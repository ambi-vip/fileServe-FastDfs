package file.ambitlu.work.dao;


import file.ambitlu.work.entity.FastDFSFile;
import file.ambitlu.work.util.DateFormatUtil;
import file.ambitlu.work.util.FastDFSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/2/29 23:48
 */
@Component
@Slf4j
public class FileDao {

    Connection conn = null;
    private List<FastDFSFile> all;

    //查找一个文件的信息
    public List<Integer> countAll(){
        try {
            ResultSet rs = CommonDao.getSt().executeQuery("select count(*) from FILES group by IS_DEL order by IS_DEL desc ");
            List<Integer> list = new ArrayList<>();
            while (rs.next()){
                list.add(rs.getInt(1));
            }
            System.out.println("查询CountAll！");
            return list;
        } catch ( SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //查找一个文件的信息
    public FastDFSFile findByUid(String Uid){
        try {
            ResultSet rs = CommonDao.getSt().executeQuery("select * from FILES where UID = '" + Uid+"'");
            List<FastDFSFile> list = new ArrayList<>();
            while (rs.next()){
                FastDFSFile file = new FastDFSFile();
                file.setUid(rs.getString(1).trim());
                file.setFileSize(rs.getLong(2));
                file.setExt(rs.getString(3).trim());
                file.setName(rs.getString(4).trim());
                file.setPath(rs.getString(5).trim());
                file.setCreateAt(rs.getDate(6));
                file.setRbs(rs.getString(7).trim());
                file.setIsDel(rs.getInt(8));
                file.setGroupName(rs.getString("GROUPNAME"));
                file.setAuthor(rs.getString("AUTHOR"));
                list.add(file);
            }
            System.out.println("查询一条数据！");
            return list.get(0);
        } catch ( SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean delete(String Uid){
        try {
            boolean execute = CommonDao.getSt().execute("update FILES set IS_DEL = 1  where UID = '" + Uid + "'");
            return execute;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    /**
     * 保存文件信息
     * @param file
     * @return  返回执行成功的条数
     */
    public int savefile(FastDFSFile file){
        try {
            int execute = CommonDao.getSt().executeUpdate(" INSERT INTO FILES (UID, FILESIZE, FILETYPE, OLDNAME, PATH,RBS,GROUPNAME,AUTHOR ) VALUES ('" +
                    file.getUid() + "'," +
                    file.getFileSize()+ ",'" +
                    file.getExt() + "','" +
                    file.getName() + "','" +
                    file.getPath() + "','" +
                    file.getRbs() + "','" +
                    file.getGroupName() + "','" +
                    file.getAuthor() + "')");
            System.out.println("新增数据成功！");
            return execute;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }


    public List<FastDFSFile> findAll(){
        try {
            //只显示最近10条数据
            /**
             * SELECT * FROM YOUR_TABLE
             * OFFSET ? ROWS
             * FETCH NEXT ? ROWS ONLY;
             * 稍微解释一下：
             * OFFSET ? ROWS - 是指跳过 ? 条记录
             * FETCH NEXT ? ROWS ONLY - 是指抓取下一个 ? 条记录
             */
            ResultSet rs = CommonDao.getSt().executeQuery(" select * from FILES order by CREATE_AT desc FETCH NEXT 10 ROWS ONLY   ");
            List<FastDFSFile> list = new ArrayList<>();
            while(rs.next())
            {
                FastDFSFile file = new FastDFSFile();
                file.setUid(rs.getString(1).trim());
                file.setFileSize(rs.getLong(2));
                file.setExt(rs.getString(3).trim());
                file.setName(rs.getString(4).trim());
                file.setPath(rs.getString(5).trim());
                file.setCreateAt(rs.getDate(6));
                file.setRbs(rs.getString(7).trim());
                file.setIsDel(rs.getInt(8));
                file.setGroupName(rs.getString("GROUPNAME"));
                file.setAuthor(rs.getString("AUTHOR"));
                list.add(file);
            }
            rs.close();
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }finally{
            System.out.println("查询全部数据！");
//            try {
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            } catch (SQLException ex) {
//                System.out.println("关闭数据库！");
//                //     Logger.getLogger(TestDerbyServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }

    }

    public void SSS() {
        try {
            Statement st = CommonDao.getSt();
            ResultSet rs=st.executeQuery(" select  count(*)  from SYS.SYSTABLES where tablename='FILES' ");
            //注意derby数据库中的表明不存在小写，所以如果此处查询结果为小写那就查不到
            int k=0;
            while(rs.next())
            {
                if("0".equals(rs.getObject(1).toString()))
                    k=-1;
            }
            if(k==-1)
            {
                log.info("新建数据库 FILES ---date :"+ DateFormatUtil.nowDate());
                st.execute("create table FILES(UID VARCHAR(50) not null constraint FILES_PK primary key,FILESIZE INTEGER,FILETYPE VARCHAR(10),OLDNAME VARCHAR(20) ,PATH VARCHAR(255),CREATE_AT TIMESTAMP default CURRENT_TIMESTAMP not null,RBS VARCHAR(255),IS_DEL INTEGER ,GROUPNAME VARCHAR(20),AUTHOR VARCHAR(50))" );

                k=0;
            }else {
                DelAll();
                st.execute("drop table FILES");
                log.info("清空数据库 FILES ---date :"+ DateFormatUtil.nowDate());
                st.execute("create table FILES(UID VARCHAR(50) not null constraint FILES_PK primary key,FILESIZE INTEGER,FILETYPE VARCHAR(10),OLDNAME VARCHAR(20) ,PATH VARCHAR(255),CREATE_AT TIMESTAMP default CURRENT_TIMESTAMP not null,RBS VARCHAR(255),IS_DEL INTEGER ,GROUPNAME VARCHAR(20),AUTHOR VARCHAR(50))" );
            }
//             st.execute("drop table FILES");
            rs.close();
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
    }

    public void DelAll(){
        Statement st = CommonDao.getSt();
        List<FastDFSFile> all = findAll();
        log.warn("删除所有存在的文件");
        new Thread(() -> {
            all.stream().filter(t->t.getIsDel()==0).forEach(a-> {
                try {
                    FastDFSClient.deleteFile(a.getGroupName(),a.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        },"del_all_file").start();


    }



}
