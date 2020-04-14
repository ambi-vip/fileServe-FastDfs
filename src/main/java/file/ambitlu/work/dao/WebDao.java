package file.ambitlu.work.dao;

import file.ambitlu.work.entity.FastDFSFile;
import file.ambitlu.work.entity.User;
import file.ambitlu.work.util.DateFormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/11 18:17
 * @description:
 */
@Component
@Slf4j
public class WebDao {

    public User findByName(String Name){
        try {
            ResultSet rs = CommonDao.getSt().executeQuery("select * from ADMIN where USERNAME = '" + Name+"'");
            List<User> list = new ArrayList<>();
            while (rs.next()){
                User user= new User();
                user.setUid(rs.getLong(1));
                user.setName(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setRemark(rs.getString(4));
                user.setCreadaAt(rs.getDate(5));
                user.setRole(rs.getString(6));
                list.add(user);
            }
            System.out.println("查询一条数据！");
            return list.get(0);
        } catch ( SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int savefile(User user){
        try {
            int execute = CommonDao.getSt().executeUpdate(" INSERT INTO ADMIN (UID, USERNAME, PASSWORD, REMARK, ROLE ) VALUES ('" +
                    user.getUid() + "','" +
                    user.getName()+ "','" +
                    user.getPassword() + "','" +
                    user.getRemark() + "','" +
                    user.getRole() + "')");
            System.out.println("新增数据成功！");
            return execute;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    //初始化表
    public void SSS() {
        try {
            Statement st = CommonDao.getSt();
            ResultSet rs=st.executeQuery(" select  count(*)  from SYS.SYSTABLES where tablename='ADMIN' ");
            //注意derby数据库中的表明不存在小写，所以如果此处查询结果为小写那就查不到
            int k=0;
            while(rs.next())
            {
                if("0".equals(rs.getObject(1).toString()))
                    k=-1;
            }
            if(k==-1)
            {
                log.info("新建数据库 ADMIN ---date :"+ DateFormatUtil.nowDate());
                st.execute("create table ADMIN(UID VARCHAR(50) not null constraint ADMIN_PK primary key,USERNAME VARCHAR(50),PASSWORD VARCHAR(500),REMARK VARCHAR(100) ,CREATE_AT TIMESTAMP default CURRENT_TIMESTAMP not null ,ROLE VARCHAR(10))" );
                k=0;
            }else {
                st.execute("drop table ADMIN");
                log.info("清空数据库 ADMIN ---date :"+ DateFormatUtil.nowDate());
                st.execute("create table ADMIN(UID VARCHAR(50) not null constraint ADMIN_PK primary key,USERNAME VARCHAR(50),PASSWORD VARCHAR(500),REMARK VARCHAR(100) ,CREATE_AT TIMESTAMP default CURRENT_TIMESTAMP not null ,ROLE VARCHAR(10))" );
                k=0;
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
