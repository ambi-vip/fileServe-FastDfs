package file.ambitlu.work.service;

import file.ambitlu.work.entity.User;

import java.util.Map;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/4/11 18:11
 * @description:
 */
public interface WebService {

    Boolean findById(String name,String password);

    Map<String,Integer> countAll();

    void init();
}
