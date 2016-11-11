package com.aibibang.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by yanand on 2016/11/5.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GrabRule {
    //抓取的主站点
    private String grabSite;
    private Rule listRule;
    //文章列表地址规则
    private Rule urlRule;
    private Rule titleRule;
    private Rule sourceRule;
    private Rule publishRule;
    @XmlAttribute
    private Boolean jsEnable;
    @XmlAttribute
    private Boolean grabSubPage;
    @XmlAttribute
    private Integer maxPageNumber;

    public String getGrabSite() {
        return grabSite;
    }

    public void setGrabSite(String grabSite) {
        this.grabSite = grabSite;
    }

    public Rule getListRule() {
        return listRule;
    }

    public void setListRule(Rule listRule) {
        this.listRule = listRule;
    }

    public Rule getUrlRule() {
        return urlRule;
    }

    public void setUrlRule(Rule urlRule) {
        this.urlRule = urlRule;
    }

    public Rule getTitleRule() {
        return titleRule;
    }

    public void setTitleRule(Rule titleRule) {
        this.titleRule = titleRule;
    }

    public Rule getSourceRule() {
        return sourceRule;
    }

    public void setSourceRule(Rule sourceRule) {
        this.sourceRule = sourceRule;
    }

    public Rule getPublishRule() {
        return publishRule;
    }

    public void setPublishRule(Rule publishRule) {
        this.publishRule = publishRule;
    }

    public Boolean getJsEnable() {
        return jsEnable;
    }

    public void setJsEnable(Boolean jsEnable) {
        this.jsEnable = jsEnable;
    }

    public Boolean getGrabSubPage() {
        return grabSubPage;
    }

    public void setGrabSubPage(Boolean grabSubPage) {
        this.grabSubPage = grabSubPage;
    }

    public Integer getMaxPageNumber() {
        return maxPageNumber;
    }

    public void setMaxPageNumber(Integer maxPageNumber) {
        this.maxPageNumber = maxPageNumber;
    }
}
