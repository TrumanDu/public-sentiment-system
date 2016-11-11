package com.aibibang.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by yanand on 2016/11/8.
 */
@XmlRootElement(name = "grab-rules")
@XmlAccessorType(XmlAccessType.FIELD)
public class GrabRuleConfig {

    @XmlElement(name = "grab")
    private List<GrabRule> grabRules;

    public List<GrabRule> getGrabRules() {
        return grabRules;
    }

    public void setGrabRules(List<GrabRule> grabRules) {
        this.grabRules = grabRules;
    }
}
