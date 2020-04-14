package file.ambitlu.work.fileserve;

import file.ambitlu.work.dao.FileDao;
import file.ambitlu.work.dao.WebDao;
import file.ambitlu.work.entity.BCrypt;
import file.ambitlu.work.entity.FastDFSFile;
import file.ambitlu.work.entity.IdWorker;
import file.ambitlu.work.entity.User;
import file.ambitlu.work.util.FastDFSClient;
import file.ambitlu.work.util.RsaUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FileserveApplicationTests {

    //私钥
    @Value("${privateKey}")
    private String privateKey ;

    //公钥
    @Value("${publicKey}")
    private String publicKey ;

    @Test
    void contextLoads() {
        FastDFSFile dfsFile = new FastDFSFile();
        FastDFSClient.upload(dfsFile);
    }

    @Test
    void coutAll() {
        FileDao fileDao = new FileDao();
        System.out.println(fileDao.countAll());
    }

    @Test
    void dao() {
//        FileDao fileDao = new FileDao();
//        fileDao.SSS();
        WebDao webDao = new WebDao();
        webDao.SSS();
    }

    @Test
    void findAll() {
        FileDao fileDao = new FileDao();
        List<FastDFSFile> all = fileDao.findAll();
        System.out.println(all);
    }

    @Test
    void rsa() {
        System.out.println(RsaUtil.enWithRSAPrivateKey("123456", privateKey));
    }


    @Test
    void delete() throws Exception {
        FastDFSClient.deleteFile("group1","M00/00/00/rBFizl6UNHmAXpTSAAToOvlsypA659.jpg");
    }

    @Test
    void AddAdmin() {
        User user = new User();
        user.setName("admin");
        String hashpw = BCrypt.hashpw("123123", BCrypt.gensalt());
        System.out.println(hashpw);
        user.setPassword(hashpw);
        user.setRole("admin");
        user.setUid(IdWorker.getWorkerId());
        user.setRemark("大佬");
        WebDao dao = new WebDao();
        dao.savefile(user);
        User admin = dao.findByName("admin");
        System.out.println(admin.getPassword());
    }

}
