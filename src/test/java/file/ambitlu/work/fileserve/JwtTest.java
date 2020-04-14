package file.ambitlu.work.fileserve;

import com.alibaba.fastjson.JSON;
import file.ambitlu.work.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/11 21:15
 * @description:
 */
@SpringBootTest
public class JwtTest {

    /****
     * 创建Jwt令牌
     */
    @Test
    public void testCreateJwt(){
        JwtBuilder builder= Jwts.builder()
                .setId("888")             //设置唯一编号
                .setSubject("小白")       //设置主题  可以是JSON数据
                .setIssuedAt(new Date())  //设置签发日期
                .signWith(SignatureAlgorithm.HS256,"itcast");//设置签名 使用HS256算法，并设置SecretKey(字符串)
        //构建 并返回一个字符串
        System.out.println( builder.compact() );
    }

    /***
     * 解析Jwt令牌数据
     */
    @Test
    public void testParseJwt(){
        String compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NjIwNjIyODd9.RBLpZ79USMplQyfJCZFD2muHV_KLks7M1ZsjTu6Aez4";
        Claims claims = Jwts.parser().
                setSigningKey("itcast").
                parseClaimsJws(compactJwt).
                getBody();
        System.out.println(claims);
    }

    @Test
    public  void  s(){
        //设置令牌信息
        Map<String,Object> info = new HashMap<String,Object>();
        info.put("role","USER");
        info.put("success","SUCCESS");
        info.put("username","Ambi");
        System.out.println(JwtUtil.createJWT("1", JSON.toJSONString(info), null));
    }
}
