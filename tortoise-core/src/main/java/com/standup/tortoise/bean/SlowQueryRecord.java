package com.standup.tortoise.bean;



import java.util.Date;
import java.util.UUID;


/**
 * Created by Jiang Biao 2019/5/28
 */
public class SlowQueryRecord {

    private String uuid;
    private String method;    //请求方法
    private String url;       //请求地址
    private Long   spend;     //查询消耗时间(ms)
    private Date   date;      //日期

    public SlowQueryRecord(String method, String url, Long spend) {
        this.uuid = UUID.randomUUID().toString();
        this.method = method;
        this.url = url;
        this.spend = spend;
        this.date = new Date();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSpend() {
        return spend;
    }

    public void setSpend(Long spend) {
        this.spend = spend;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
