package com.standup.tortoise.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.standup.tortoise.bean.SlowQueryRecord;
import com.standup.tortoise.utils.CreateKeyUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jiang Biao 2019/5/28
 */
public class SlowQueryCache {


    private static Cache<String, SlowQueryRecord> slowQueryRecord = CacheBuilder.newBuilder().initialCapacity(50).maximumSize(100000).expireAfterWrite(7,TimeUnit.DAYS).build();


    public static void addSlowQueryRecord(String method,String url,Long spend){
        slowQueryRecord.put(CreateKeyUtils.createKey(method,url),new SlowQueryRecord(method,url,spend));
    }


    public static List<SlowQueryRecord> getSlowQueryRecords(){
        Map<String,SlowQueryRecord> map = slowQueryRecord.asMap();
        return new ArrayList<>(map.values());
    }


}
