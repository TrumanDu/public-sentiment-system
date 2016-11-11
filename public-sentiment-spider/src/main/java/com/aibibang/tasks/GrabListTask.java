package com.aibibang.tasks;

import com.aibibang.bean.GrabRule;
import com.aibibang.service.GrabService;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yanand on 2016/11/10.
 */
@Component
public class GrabListTask {
    private static final Logger log = LoggerFactory.getLogger(GrabListTask.class);
    @Autowired
    private GrabService grabService;
    @Autowired
    private GrabDetailTask grabDetailTask;

    @Async
    public void doGrab(String site, GrabRule rule) {
        final Document doc = grabService.getDocument(site, rule.getJsEnable());
        if (rule.getGrabSubPage()) { //判断是否爬子页面
            List<String> urls = grabService.getUrls(doc, rule.getUrlRule().getSelector());
            log.info("Beginning grab sub page, the List size is: " + urls.size());
            for (String url : urls) {
                grabDetailTask.grabSubPageAndSave(url, rule); //多线程抓取
            }
        } else {
            grabService.parseAndSave(doc, rule);
        }
    }
}
