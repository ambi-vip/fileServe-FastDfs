package file.ambitlu.work.controller;

import com.alibaba.fastjson.JSON;
import file.ambitlu.work.entity.BCrypt;
import file.ambitlu.work.entity.Result;
import file.ambitlu.work.entity.StatusCode;
import file.ambitlu.work.entity.User;
import file.ambitlu.work.service.WebService;
import file.ambitlu.work.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/11 18:09
 * @description:
 */
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private WebService webService;

    @GetMapping("/login")
    public Result<String> login(String name, String password, HttpServletResponse response){
        if (webService.findById(name,password)){
            //设置令牌信息
            Map<String,Object> info = new HashMap<String,Object>();
            info.put("role","USER");
            info.put("success","SUCCESS");
            info.put("username",name);

            //生成令牌
            String jwt = JwtUtil.createJWT("1", JSON.toJSONString(info),null);

            //添加Cooike
            Cookie cookie = new Cookie("Authorization",jwt);
            response.addCookie(cookie);
            return new Result<String>(true, StatusCode.LOGINERROR.getCode(),"成功",jwt);
        }
        return new Result<String>(false, StatusCode.LOGINERROR.getCode(),"错误");
    }
}
