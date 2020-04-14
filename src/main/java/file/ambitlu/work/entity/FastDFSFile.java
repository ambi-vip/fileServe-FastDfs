package file.ambitlu.work.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * 文件信息类
 * @author Ambi
 * @version 1.0
 * @date 2020/3/13 14:53
 */
@Data
public class FastDFSFile implements Serializable {
    private String uid;
    //文件名字
    private String name;
    //文件内容
    private byte[] content;
    //文件扩展名
    private String ext;
    //文件MD5摘要值
    private String md5;
    //文件创建作者
    private String author;
    //文件密钥
    private String rbs;
    //文件大小
    private Long fileSize;
    //创建时间
    private Date createAt;
    //服务区存储路径
    private String path;
    //文件状态
    private Integer isDel;
    //f服务器group
    private String groupName;

    public FastDFSFile(String name, byte[] content, String ext, String rbs, String author) {
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.rbs = rbs;
        this.author = author;
    }

    public FastDFSFile(String name, byte[] content,Long fileSize, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.fileSize = fileSize;
    }

    public FastDFSFile() {
    }
}
