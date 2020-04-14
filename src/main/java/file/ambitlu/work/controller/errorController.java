package file.ambitlu.work.controller;

import file.ambitlu.work.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/10 15:29
 * @description:
 */
@RestController
@CrossOrigin
@Slf4j
public class errorController {

    @GetMapping("/error/{id}")
    public Result error(@PathVariable("id") Integer id){
        return new Result(false, id,"身份验证失败");
    }

}
