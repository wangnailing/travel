package com.edu.springboottest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class controller {
    public String doSome(){
        //处理some.do请求了。 相当于service调用处理完成了。
        return "mbbbbbbv";
    }


}

