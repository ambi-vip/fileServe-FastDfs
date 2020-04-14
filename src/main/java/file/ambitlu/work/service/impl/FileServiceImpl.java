package file.ambitlu.work.service.impl;

import file.ambitlu.work.dao.FileDao;
import file.ambitlu.work.entity.FastDFSFile;
import file.ambitlu.work.entity.IdWorker;
import file.ambitlu.work.service.FileService;
import file.ambitlu.work.util.DateFormatUtil;
import file.ambitlu.work.util.FastDFSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020-04-09 20:27:31
 * @description:
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public Map<String, String> upload(MultipartFile file) throws IOException {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        Map<String,String> fileInfp = new HashMap<>();
        new Thread(() -> {
            //封装一个FastDFSFile
            FastDFSFile fastDFSFile = null;//文件扩展名
            try {
                fastDFSFile = new FastDFSFile(
                        file.getOriginalFilename(), //文件名字
                        file.getBytes(),            //文件字节数组
                        file.getSize(),            //文件大小
                        StringUtils.getFilenameExtension(file.getOriginalFilename()));
                //文件上传
                String[] uploads = FastDFSClient.upload(fastDFSFile);
                //组装文件上传地址
                String s = FastDFSClient.getTrackerUrl() + "/" + uploads[0] + "/" + uploads[1];

                //利用雪花算法。生成id
                fastDFSFile.setUid(IdWorker.getWorkerId()+"");
                fastDFSFile.setGroupName(uploads[0]);
                fastDFSFile.setPath(uploads[1]);
                fastDFSFile.setAuthor("Admin");
                fileDao.savefile(fastDFSFile);
                log.info("信息保存成功");


                fileInfp.put("url",s);
                fileInfp.put("group",uploads[0]);
                fileInfp.put("path",uploads[1]);
                fileInfp.put("uid",fastDFSFile.getUid());

                countDownLatch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }

        },"input threadd name").start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fileInfp;
    }

    @Override
    public void deleteFile(String uid) throws Exception {
        log.info("删除"+uid+"一条数据！"+ DateFormatUtil.nowDate());
        FastDFSFile byUid = fileDao.findByUid(uid);
        fileDao.delete(uid);
        FastDFSClient.deleteFile(byUid.getGroupName(),byUid.getPath());
    }

    @Override
    public List<FastDFSFile> findAll() {
        log.info("查询所有记录 "+ DateFormatUtil.nowDate());
        return fileDao.findAll();
    }

    @Override
    public FastDFSFile findByid(String id) {
        log.info("查询---"+id+"---记录 "+ DateFormatUtil.nowDate());
        return fileDao.findByUid(id);
    }
}
