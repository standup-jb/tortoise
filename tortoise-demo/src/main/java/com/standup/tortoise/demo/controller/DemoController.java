package com.standup.tortoise.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jiang Biao 2019/6/1
 */

@Controller
public class DemoController {

    @RequestMapping("/demo")
    public ResponseEntity demo(){
        return new ResponseEntity("success", HttpStatus.OK);
    }


    @RequestMapping("/slow")
    public ResponseEntity slow() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return new ResponseEntity("slow",HttpStatus.OK);
    }

    @RequestMapping("error")
    public ResponseEntity error(){
        return new ResponseEntity("error",HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
