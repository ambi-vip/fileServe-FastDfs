package file.ambitlu.work.service;

import file.ambitlu.work.entity.FastDFSFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020-04-09 20:27:22
 * @description:
 */
public interface FileService {

    public Map<String, String> upload(MultipartFile file) throws IOException;

    public void deleteFile(String uid) throws Exception;

    public List<FastDFSFile> findAll();

    public FastDFSFile findByid(String id);
}
