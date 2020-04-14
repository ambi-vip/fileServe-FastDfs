package file.ambitlu.work.service.impl;

import file.ambitlu.work.dao.FileDao;
import file.ambitlu.work.dao.WebDao;
import file.ambitlu.work.entity.BCrypt;
import file.ambitlu.work.entity.IdWorker;
import file.ambitlu.work.entity.User;
import file.ambitlu.work.service.WebService;
import file.ambitlu.work.util.DateFormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/11 18:11
 * @description:
 */
@Service
@Slf4j
public class WebServicImpl  implements WebService {

    @Autowired
    public WebDao webDao;

    @Autowired
    public FileDao fileDao;


    @Override
    public Boolean findById(String name, String password) {
        log.info("查询name = "+name+"---date :"+ DateFormatUtil.nowDate());
        User user = webDao.findByName(name);
        return BCrypt.checkpw(password,user.getPassword());
    }

    @Override
    public Map<String, Integer> countAll() {
        List<Integer> integers = fileDao.countAll();
        Map<String, Integer> maps = new HashMap<>();
        maps.put("allFilesNumber",integers.get(0));
        if (integers.size()>1){
            maps.put("allRecordNumber",integers.get(0)+integers.get(1));
        }else {
            maps.put("allRecordNumber",integers.get(0));
        }
        log.info("查询ConuntAll ---date :"+ DateFormatUtil.nowDate());
        return maps;
    }

    @Override
    public void init() {
        log.info("init ---date :"+ DateFormatUtil.nowDate());
        webDao.SSS();
        fileDao.SSS();

        User user = new User();
        user.setName("admin");
        String hashpw = BCrypt.hashpw("123123", BCrypt.gensalt());
        user.setPassword(hashpw);
        user.setRole("admin");
        user.setUid(IdWorker.getWorkerId());
        user.setRemark("大佬");
        log.info("新增管理员Admin  ---date :"+ DateFormatUtil.nowDate());
        webDao.savefile(user);

    }
}
