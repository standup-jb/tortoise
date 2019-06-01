package com.standup.tortoise.servlet;

import com.alibaba.fastjson.JSON;
import com.standup.tortoise.cache.RecordCache;
import com.standup.tortoise.cache.SlowQueryCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created by Jiang Biao 2019/5/29
 */
public class TortoiseServlet extends HttpServlet {



    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getRequestURL().toString().contains("record")){
            PrintWriter printWriter = resp.getWriter();
            printWriter.print(JSON.toJSONString(RecordCache.getRecords()));
            printWriter.flush();
            printWriter.close();

        }else if(req.getRequestURL().toString().contains("slow")){
            PrintWriter printWriter = resp.getWriter();
            printWriter.print(JSON.toJSONString(SlowQueryCache.getSlowQueryRecords()));
            printWriter.flush();
            printWriter.close();
        }else {
            String reqUrl = req.getRequestURL().toString();
            if(reqUrl.contains(".css")){
                resp.setContentType("text/css");
            }


            String path = reqUrl.substring(reqUrl.lastIndexOf("/"),reqUrl.length());
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            byte[] buff = new byte[1024];
            int length = 0;
            StringBuilder result = new StringBuilder();
            while((length = is.read(buff))!=-1) {
                String string =new String(buff, 0, length);
                result.append(string);
            }
            resp.getWriter().write(result.toString());


            System.out.println(resp.getContentType());
        }


    }








}
