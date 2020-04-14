package file.ambitlu.work.controller;

import file.ambitlu.work.entity.Result;
import file.ambitlu.work.entity.StatusCode;
import file.ambitlu.work.service.WebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/10 18:53
 * @description:
 */
@RestController
@CrossOrigin
@Slf4j
public class webController {

    @Autowired
    private WebService  webService;

    @GetMapping(value = "/")
    public String index(){
        return "a";
    }

    @GetMapping(value = "/countAll")
    public Result<Map<String, Integer>> countAll(){

        return new Result<Map<String ,Integer>>(true, StatusCode.OK.getCode(),"count",webService.countAll());
    }

    @GetMapping(value = "/init")
    public Result init(){
        webService.init();
        return new Result(true, StatusCode.OK.getCode(),"初始化成功");
    }
}
