package file.ambitlu.work.util;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/4 0:34
 */
@Slf4j
public class TokenRsa {

    //令牌头名字
    private static final String AUTHORIZE_TOKEN = "Authorization";


    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqW2VX0YoBwhWvXKGCEui/r1FwsrTFRGdL/IKW+igpznK37AAB4PLQsXgHLP+tntFcLSUVxnB8q1mLEQRhdo+S01v7NxnjLrvmCHP6C+xTlIJw22zWmdAhPrmcKouVDM/TFvsPxuevv1MJzjUn36RzZmF8Jg6+0N0kA3M9k8LnWwIDAQAB";


    /**
     * 读取内容header
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4096];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 判断SID是否符合要求。
     * @param
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public static boolean Authentication(HttpServletRequest request, HttpServletResponse response) {


        if (authJwt(request,response))  {log.info("验证成功");return true;}

        String SID = request.getHeader("X-SID");
        String Signature = request.getHeader("X-Signature");
        //用公钥解锁。解锁后内容与SID比较
        if (Signature == null)  return false;
        String result = RsaUtil.deWithRSAPublicKey(Signature, publicKey);
        if (result.equals(SID)){
            log.info("验证成功");
            return true;
        }
        log.info("验证失败");
        return false;
    }

    public static String getPublicKey(){
        return publicKey;
    }

    public static boolean authJwt(HttpServletRequest request, HttpServletResponse response){
        //获取头文件中的令牌信息
        String tokent = request.getHeader("Authorization");

        //如果头文件中没有，则从请求参数中获取
        if (StringUtils.isEmpty(tokent)) {
            tokent = CookieUtil.getValue(request,"Authorization");

        }
        log.info("获取到参数tokent:"+tokent);

        //如果为空，则输出错误代码
        if (StringUtils.isEmpty(tokent)) {
//            //设置方法不允许被访问，405错误代码
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        //解析令牌数据
        try {
            Claims claims = JwtUtil.parseJWT(tokent);
            log.info("Jwt 返回成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            //解析失败，响应401错误
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }


}
