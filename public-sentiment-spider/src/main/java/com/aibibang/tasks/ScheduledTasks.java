package com.aibibang.tasks;

import com.aibibang.bean.GrabRule;
import com.aibibang.bean.GrabRuleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yanand on 2016/11/1.
 */
@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

    @Autowired
    private Unmarshaller unmarshaller;
    @Autowired
    private GrabListTask grabListTask;


    @Scheduled(fixedRate = 1 * 60 * 60 * 1000)  //配置抓取时间间隔
    public void scheduleTasks() {
        log.info(String.format("The Scheduled Task is beginning, the time is now %s", dateFormat.format(new Date())));
        List<GrabRule> rules = getGrabRules();
        log.info("The rules size is: " + rules.size());
        rules.forEach(this::doGrabPages);
    }

    private List<GrabRule> getGrabRules() {
        ClassPathResource resource = new ClassPathResource("grab-rule-config.xml");
        try (InputStream is = resource.getInputStream()){
            GrabRuleConfig config = (GrabRuleConfig) unmarshaller.unmarshal(new StreamSource(is));
            return config.getGrabRules();
        } catch (IOException e) {
            log.error("Read Grab rule config file has error.", e);
        }
        return Collections.emptyList();
    }

    public void doGrabPages(GrabRule rule) {
        if (rule.getMaxPageNumber() != null) { //判断是否有分页
            for (int i = 1; i <= rule.getMaxPageNumber(); i++) {
                log.info("Grab page: " + i);
                grabListTask.doGrab(rule.getGrabSite().replace("{page}", String.valueOf(i)), rule); //多线程抓取
            }
        } else {
            grabListTask.doGrab(rule.getGrabSite(), rule); //多线程抓取
        }
    }
}
