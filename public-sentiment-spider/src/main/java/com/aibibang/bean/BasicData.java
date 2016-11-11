package com.aibibang.bean;

/**
 * Created by yanand on 2016/11/5.
 */
public class BasicData {
    private Long id;
    private String topicTitle;
    private String url;
    private String source;
    private String publishTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "BasicData{" +
                "topicTitle='" + topicTitle + '\'' +
                ", url='" + url + '\'' +
                ", source='" + source + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}
