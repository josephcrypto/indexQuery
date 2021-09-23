package cn.coding.com.indexquery.controllers;

import cn.coding.com.indexquery.encryption.annotation.decrypt.AESDecryptBody;
import cn.coding.com.indexquery.service.IndexSearchService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class IndexController {

    private IndexSearchService indexSearchService;

    @Autowired
    public IndexController(IndexSearchService indexSearchService) {
        this.indexSearchService = indexSearchService;
    }

    @GetMapping("/suggestions")
    @ResponseBody
    public List<JSONObject> fetchSuggestions(@RequestParam(value = "q", required = false) String query){
        log.info("fetch suggests {} ", query);
        List<JSONObject> suggests = indexSearchService.fetchSuggestions(query);
        log.info("suggests {}", suggests);
        return suggests;
    }

    @GetMapping("/logs")
    public JSONObject getAllIndex(@RequestParam(value = "q", required = false, defaultValue = "") String query,
                                  @RequestParam(value = "page",required = false, defaultValue = "0") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = "30") int size){
        log.info("fetch allIndex page {} , size {}, q {} ", page, size, query);
        return indexSearchService.fetchAll(query, page, size);
    }

    @GetMapping("/log-detail")
    public JSONObject getIndexById(@RequestParam("id") String id){
        log.info("fetch index by id {} ", id);
        return indexSearchService.getById(id);
    }

    @PostMapping("/get_descrypt_body")
    @AESDecryptBody
    public JSONObject getDescryptBody(@RequestBody String encryptedString){
        return JSONObject.parseObject(encryptedString);
    }
}
