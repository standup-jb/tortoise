package com.standup.tortoise.bean;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Jiang Biao 2019/5/28
 */
public class QueryRecord {

    private String uuid;
    private String method;               //请求方法
    private String url;                  //请求地址
    private AtomicLong averageSpend;     //平均耗时(ms)
    private AtomicLong maxSpend;         //最大耗时(ms)
    private AtomicLong minSpend;         //最小耗时(ms)
    private AtomicLong queryCount;       //请求量
    private AtomicLong error5XXCount;    //500错误量
    private AtomicLong error4XXCount;    //400错误量
    private AtomicLong slowQueryCount;   //慢查询数量

    private Double errorRate;            //错误占比
    private Double slowRate;             //慢查询占比



    public QueryRecord(String method, String url, AtomicLong averageSpend) {
        this.uuid = UUID.randomUUID().toString();
        this.method = method;
        this.url = url;
        this.averageSpend = averageSpend;
        this.maxSpend = new AtomicLong(Long.MIN_VALUE);
        this.minSpend = new AtomicLong(Long.MAX_VALUE);
        this.queryCount = new AtomicLong(1);
        this.error4XXCount = new AtomicLong(0);
        this.error5XXCount = new AtomicLong(0);
        this.slowQueryCount = new AtomicLong(0);
        this.errorRate = 0.0;
        this.slowRate = 0.0;
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

    public AtomicLong getAverageSpend() {
        return averageSpend;
    }

    public void setAverageSpend(AtomicLong averageSpend) {
        this.averageSpend = averageSpend;
    }

    public AtomicLong getMaxSpend() {
        return maxSpend;
    }

    public void setMaxSpend(AtomicLong maxSpend) {
        this.maxSpend = maxSpend;
    }

    public AtomicLong getMinSpend() {
        return minSpend;
    }

    public void setMinSpend(AtomicLong minSpend) {
        this.minSpend = minSpend;
    }

    public AtomicLong getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(AtomicLong queryCount) {
        this.queryCount = queryCount;
    }

    public AtomicLong getError5XXCount() {
        return error5XXCount;
    }

    public void setError5XXCount(AtomicLong error5XXCount) {
        this.error5XXCount = error5XXCount;
    }

    public AtomicLong getError4XXCount() {
        return error4XXCount;
    }

    public void setError4XXCount(AtomicLong error4XXCount) {
        this.error4XXCount = error4XXCount;
    }

    public Double getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(Double errorRate) {
        this.errorRate = errorRate;
    }


    public AtomicLong getSlowQueryCount() {
        return slowQueryCount;
    }

    public void setSlowQueryCount(AtomicLong slowQueryCount) {
        this.slowQueryCount = slowQueryCount;
    }

    public Double getSlowRate() {
        return slowRate;
    }

    public void setSlowRate(Double slowRate) {
        this.slowRate = slowRate;
    }
}

