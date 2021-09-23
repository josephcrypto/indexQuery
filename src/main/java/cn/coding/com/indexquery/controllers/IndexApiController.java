package cn.coding.com.indexquery.controllers;

import cn.coding.com.indexquery.encryption.annotation.encrypt.AESEncryptBody;
import cn.coding.com.indexquery.service.IndexSearchService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class IndexApiController {

    @Autowired
    IndexSearchService indexSearchService;

    @GetMapping("/logs")
    @AESEncryptBody //userid ,  tableno,  xuehao, juhao, username
    public JSONObject getAllIndex(@RequestParam(value = "userid", required = false, defaultValue = "") String userId,
                                  @RequestParam(value = "tableno", required = false, defaultValue = "") String tableNo,
                                  @RequestParam(value = "xuehao", required = false, defaultValue = "") String xuehao,
                                  @RequestParam(value = "juhao", required = false, defaultValue = "") String juhao,
                                  @RequestParam(value = "username", required = false, defaultValue = "") String userName,
                                  @RequestParam(value = "bid", required = false, defaultValue = "") String bid,
                                  @RequestParam(value = "page",required = false, defaultValue = "0") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = "30") int size){
        log.info("fetch api index page {}, size {}, q {}", page, size, userId);
        return indexSearchService.searchQuery(userId, tableNo, xuehao, juhao, userName, bid, page, size);
    }

    @GetMapping("/log-detail")
    @AESEncryptBody
    public JSONObject getIndexById(@RequestParam("id") String id){
        log.info("fetch api index by id {}", id);
        return indexSearchService.getById(id);
    }
}
