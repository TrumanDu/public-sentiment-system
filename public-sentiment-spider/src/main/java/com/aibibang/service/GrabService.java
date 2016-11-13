package com.aibibang.service;

import com.aibibang.bean.BasicData;
import com.aibibang.bean.GrabRule;
import com.aibibang.bean.Rule;
import com.aibibang.dao.BasicDataDao;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yanand on 2016/11/7.
 */
@Service
public class GrabService {

    private static final Logger log = LoggerFactory.getLogger(GrabService.class);
    @Autowired
    private BasicDataDao basicDataDao;

    public Document getDocument(String site, boolean jsEnable) {
        try {
            if (!jsEnable) {
                return Jsoup.connect(site).timeout(10000).get();
            }
            final WebClient webClient = new WebClient();
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            final HtmlPage page = webClient.getPage(site);
            webClient.waitForBackgroundJavaScript(10000);
            return Jsoup.parse(page.asXml());
        } catch (IOException e) {
            log.error("Connect the site has error, the url is: " + site, e);
        }
        return null;
    }

    public List<String> getUrls(Document doc, String urlRule) {
        List<String> urls = new ArrayList<>();
        if (doc == null) {
            return urls;
        }
        Elements es = doc.select(urlRule);
        for (Element e : es) {
            String url = e.attr("href");
            if (url.startsWith("http") && !urls.contains(url)) {
                urls.add(url);
            }
        }
        return urls;
    }

    public BasicData getBasicData(String url, GrabRule rule) {
        final Document doc = getDocument(url, rule.getJsEnable());
        try {
            String topicTitle = getElementValue(rule.getTitleRule(), doc.select(rule.getTitleRule().getSelector()).first());
            if (StringUtils.isEmpty(topicTitle)) {
                return null;
            }
            String source = getElementValue(rule.getSourceRule(), doc.select(rule.getSourceRule().getSelector()).first());
            if (StringUtils.isEmpty(source)) {
                return null;
            }
            String publishTime = getElementValue(rule.getPublishRule(), doc.select(rule.getPublishRule().getSelector()).first());
            if (StringUtils.isEmpty(publishTime)) {
                return null;
            }
            BasicData data = new BasicData();
            data.setUrl(url);
            data.setTopicTitle(topicTitle);
            data.setSource(source);
            data.setPublishTime(publishTime);
            return data;
        } catch (Exception e) {
            log.error("Get basic data from Url has error, the url is: " + url, e);
        }
        return null;
    }

    public void saveDataToHbase(BasicData data) {
        if (data != null) {
            basicDataDao.saveToHbase(data);
        } else {
            log.info("The data is empty and will not be saved");
        }
    }

    public void parseAndSave(Document doc, GrabRule rule) {
        if (doc == null) {
            return;
        }
        Elements es = doc.select(rule.getListRule().getSelector());
        log.info("List page have been parsed, the size is: " + es.size());
        for (Element e : es) {
            BasicData data = getBasicDataFromElement(rule, e);
            saveDataToHbase(data);
        }
    }

    private BasicData getBasicDataFromElement(GrabRule rule, Element e) {
        try {
            String url = e.select(rule.getUrlRule().getSelector()).first().attr("href");
            if (StringUtils.isEmpty(url)) {
                return null;
            }
            String topicTitle = getElementValue(rule.getTitleRule(), e.select(rule.getTitleRule().getSelector()).first());
            if (StringUtils.isEmpty(topicTitle)) {
                return null;
            }
            String source = getElementValue(rule.getSourceRule(), e.select(rule.getSourceRule().getSelector()).first());
            if (StringUtils.isEmpty(source)) {
                return null;
            }
            String publishTime = getElementValue(rule.getPublishRule(), e.select(rule.getPublishRule().getSelector()).first());
            if (StringUtils.isEmpty(publishTime)) {
                return null;
            }
            BasicData data = new BasicData();
            data.setUrl(url);
            data.setTopicTitle(topicTitle);
            data.setSource(source);
            data.setPublishTime(publishTime);
            return data;
        } catch (Exception ex) {
            log.error("Get basic data from Item has error, the rule may not be correct.", ex);
        }
        return null;
    }

    private String getElementValue(Rule subRule, Element ele) {
        String value;
        if ("text".equals(subRule.getMode()) || StringUtils.isEmpty(subRule.getMode())) {
            value = ele.text();
        } else {
            value = ele.attr(subRule.getMode());
        }
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        value = value.trim();
        if (StringUtils.isNotEmpty(subRule.getSubstring())) {
            Matcher m = Pattern.compile(subRule.getSubstring()).matcher(value);
            if (m.find()) {
                value = m.group(1);
            }
        }
        return value;
    }
}
