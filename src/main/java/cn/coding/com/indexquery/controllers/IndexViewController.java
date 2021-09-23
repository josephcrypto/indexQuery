package cn.coding.com.indexquery.controllers;

import cn.coding.com.indexquery.service.IndexSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class IndexViewController {

    @Autowired
    private IndexSearchService indexSearchService;

    @GetMapping("/")
    public String HomePage(Model model){
//        model.addAttribute("logs", indexSearchService.fetchAll());
        return "logs";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable("id") String id, Model model){
        model.addAttribute("id", id);
        return "details";
    }
}
