package file.ambitlu.work.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/11 18:13
 * @description:
 */
@Data
@NoArgsConstructor
public class User {

    public Long uid;
    public String name;
    public String password;
    public Date creadaAt;
    public String remark;
    public String role;
}
