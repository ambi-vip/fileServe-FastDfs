package file.ambitlu.work.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/13 15:30
 * @description:
 */
public class DateFormatUtil {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式

    public static String nowDate(){
        return dateFormat.format( new Date() );
    }
}
