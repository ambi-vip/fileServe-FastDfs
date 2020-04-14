package file.ambitlu.work.controller;

import file.ambitlu.work.entity.FastDFSFile;
import file.ambitlu.work.entity.Result;
import file.ambitlu.work.service.FileService;
import file.ambitlu.work.entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/9 18:30
 * @description:
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/file")
public class FileController {

    @Autowired
    public FileService fileService;


    @PostMapping("/upload")
    public Result<Map<String, String>> uoload(@RequestParam("file") MultipartFile file)throws Exception{
        //文件上传
        Map<String, String> upload = fileService.upload(file);
        return new Result<Map<String, String>>(true, StatusCode.OK.getCode(),"文件上传成功",upload);
    }

    @DeleteMapping("/delete/{uid}")
    public Result delete(@PathVariable("uid") String uid) throws Exception {
        fileService.deleteFile(uid);
        return new Result(true,StatusCode.OK.getCode(),"文件已删除");
    }

    @GetMapping("/findAll")
    public Result<List<FastDFSFile>> findAll() throws Exception {
        List<FastDFSFile> all = fileService.findAll();

        return new Result<List<FastDFSFile>>(true,StatusCode.OK.getCode(),"查询数量："+all.size(),all);
    }

    @GetMapping("/get/{id}")
    public Result<FastDFSFile> findByid(@PathVariable("id") String id) throws Exception {
        FastDFSFile all = fileService.findByid(id);
        return new Result<FastDFSFile>(true,StatusCode.OK.getCode(),"查询成功",all);
    }
}
