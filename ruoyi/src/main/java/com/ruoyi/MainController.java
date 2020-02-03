package com.ruoyi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenchuan@autohome.com.cn
 * @create 2020-02-02-下午11:51
 * @description
 */
@Controller
public class MainController {

    @RequestMapping("/index")
    public String index() {
        return "index.html";
    }

    /**
     * 登录方法
     */
    @GetMapping("/login")
    public String login() {
        return "index.html";
    }


}
