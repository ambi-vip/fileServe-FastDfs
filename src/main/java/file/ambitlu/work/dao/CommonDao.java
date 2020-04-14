package file.ambitlu.work.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/11 18:17
 * @description:
 */
public class CommonDao {

    public static Statement getSt(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");//加载驱动
            Connection conn = DriverManager.getConnection("jdbc:derby:derbyDB;create=true","user","pwd");
            return conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
