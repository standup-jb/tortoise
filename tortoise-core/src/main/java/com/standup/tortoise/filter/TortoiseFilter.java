package com.standup.tortoise.filter;


import com.standup.tortoise.cache.RecordCache;
import com.standup.tortoise.cache.SlowQueryCache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Jiang Biao 2019/5/28
 */


public class TortoiseFilter implements Filter {


    private static Long SLOW_QUERY_THRESHOLD = 300L;

    private static String DING_DING_URL = "";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String slow = filterConfig.getInitParameter("slowQueryThreshold");
        String dingdingUrl = filterConfig.getInitParameter("dingdingUrl");
        if(slow !=null){
            SLOW_QUERY_THRESHOLD = Long.valueOf(slow);
        }
        if(dingdingUrl != null){
            DING_DING_URL = dingdingUrl;
        }


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);

        Long spend = System.currentTimeMillis() - start ;
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        if(request.getRequestURL().toString().contains("tortoise")){
            return;
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        RecordCache.addRecord(request.getMethod(),request.getRequestURL().toString(),spend,response.getStatus());
        if(spend > SLOW_QUERY_THRESHOLD){
            SlowQueryCache.addSlowQueryRecord(request.getMethod(),request.getRequestURL().toString(),spend);
            RecordCache.addSlowQuery(request.getMethod(),request.getRequestURL().toString(),spend);
            sendAlert(request.getRequestURL().toString(),spend);
        }
    }

    @Override
    public void destroy() {

    }


    public static void sendAlert(String url,Long spend) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "{\"msgtype\": \"text\", \n" +
                "        \"text\": {\n" +
                "             \"content\": \"请求超过阈值报警，url:"+url + " 耗时:"+spend+"ms\"\n" +
                "        }\n" +
                "      }");
        if(DING_DING_URL!=null&&!DING_DING_URL.equals("")){
            Request request = new Request.Builder().url(DING_DING_URL).post(requestBody).build();
            try {
                client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
