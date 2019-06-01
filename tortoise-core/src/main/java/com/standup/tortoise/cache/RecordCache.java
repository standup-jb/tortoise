package com.standup.tortoise.cache;




import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.standup.tortoise.bean.QueryRecord;
import com.standup.tortoise.utils.CreateKeyUtils;
import com.standup.tortoise.utils.RateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Jiang Biao 2019/5/28
 */
public class RecordCache {

    public static Cache<String , QueryRecord> recordCache = CacheBuilder.newBuilder().initialCapacity(50).maximumSize(1000).expireAfterWrite(30,TimeUnit.DAYS).build();


    public static synchronized void addRecord(String method,String url,Long spend,Integer responseCode){
        String key = CreateKeyUtils.createKey(method,url);
        try {
            QueryRecord queryRecord = recordCache.get(key, new Callable<QueryRecord>() {
                @Override
                public QueryRecord call() throws Exception {
                    QueryRecord newRecord = new QueryRecord(method, url, new AtomicLong(spend));
                    return newRecord;
                }
            });
            if (queryRecord != null) {
                queryRecord.getQueryCount().incrementAndGet();
                queryRecord.setMaxSpend(new AtomicLong(Math.max(queryRecord.getMaxSpend().get(),spend)));
                queryRecord.setMinSpend(new AtomicLong(Math.min(queryRecord.getMinSpend().get(),spend)));
                Long averageSpend = (queryRecord.getAverageSpend().get()*queryRecord.getQueryCount().get()+spend)/(queryRecord.getQueryCount().get()+1);
                queryRecord.setAverageSpend(new AtomicLong(averageSpend));
                if(responseCode>=400&&responseCode<500){
                    queryRecord.getError4XXCount().incrementAndGet();
                    queryRecord.setErrorRate(RateUtils.computeRate(queryRecord.getError4XXCount().get()+queryRecord.getError5XXCount().get(),queryRecord.getQueryCount().get()));

                }else if(responseCode>=500&&responseCode<600){
                    queryRecord.getError5XXCount().incrementAndGet();
                    queryRecord.setErrorRate(RateUtils.computeRate(queryRecord.getError4XXCount().get()+queryRecord.getError5XXCount().get(),queryRecord.getQueryCount().get()));

                }

            }

        }catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    public static void addSlowQuery(String method,String url,Long spend){
        String key = CreateKeyUtils.createKey(method,url);
        QueryRecord queryRecord = recordCache.getIfPresent(key);
        if(queryRecord!=null){
            queryRecord.getSlowQueryCount().incrementAndGet();
            queryRecord.setSlowRate(queryRecord.getSlowQueryCount().get()*1.0/queryRecord.getQueryCount().get());
        }
    }

    public static List<QueryRecord> getRecords(){
        Map<String,QueryRecord> maps = recordCache.asMap();
        return new ArrayList<>(maps.values());
    }



}
