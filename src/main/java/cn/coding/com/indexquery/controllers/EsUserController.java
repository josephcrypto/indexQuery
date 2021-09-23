package cn.coding.com.indexquery.controllers;

import cn.coding.com.indexquery.model.EsUser;
import cn.coding.com.indexquery.repository.EsUserRepository;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EsUserController {

    @Autowired
    private EsUserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/create_user")
    public String createUser(@RequestBody EsUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        EsUser userResult = repository.save(user);
        return JSONObject.toJSONString(userResult);
    }
}
