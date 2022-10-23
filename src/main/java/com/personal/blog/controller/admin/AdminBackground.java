package com.personal.blog.controller.admin;


import com.personal.blog.pojo.Background;
import com.personal.blog.service.BackgroundService;
import com.personal.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
* @description:
* @author: luxinlong
* @date: 2022/9/29
**/

@Controller
@RequestMapping("/admin")
public class AdminBackground {

    @Autowired
    private BackgroundService backgroundService;

    @Autowired
    private UserService userService;

    //每次显示多少条
    private static final Integer show = 10;

    //局部刷新?
    private boolean part = false;

    @GetMapping("/background")
    public String backimg(Model model){

        //要返回的页面
        String view = "admin/backimg";

        //List<Background> urls = backgroundService.queryAll();

        //默认显示10条数据
        List<Background> urls = backgroundService.queryAllLimit(1*show);

        //起始ok为1
        model.addAttribute("urls",urls).addAttribute("ok",1);

        if(part){
            view = "admin/backimg :: urlfragment";
            this.part = false;
        }
        return view;
    }

    @GetMapping("/addimg")
    public String addbackground(){

        return "admin/addimg";
    }

    @GetMapping("/checkurl")
    public @ResponseBody int checkurl(String imgurl){

        //查看是否重复
        int count = backgroundService.checkurlRepitition(imgurl);

        return count;
    }

    @PostMapping("/insertimgurl")
    public String insertimg(Background background){

        int count = backgroundService.addSelective(background);

        //局部刷新
        this.part = true;

        return count > 0 ? "redirect:/admin/background" : "error/error";
    }

    @GetMapping("/useurl")
    public @ResponseBody int useUrl(@RequestParam("url") String url){

        int count = userService.updateFirstPicture(url);

        return count;
    }

    @PostMapping("/deleteimgurl")
    public String deleteUrl(@RequestParam(value = "urlid",required = false) Long id,Model model){

        //如果id不为null,删除
        if (id != null)
            backgroundService.deleteUrl(id);

        List<Background> urls = backgroundService.queryAll();
        model.addAttribute("urls",urls);

        return "admin/backimg :: urlfragment";
    }

    @PostMapping("/loadmoreimg")
    public String loadMore(Integer number,Model model){

        //刷新数据
        List<Background> urls = backgroundService.queryAllLimit(number*show);

        model.addAttribute("urls",urls);

        int ok = 1;

        //如果加载的数量大于总数,不执行刷新
        if (number*show >= backgroundService.queryCount())
            ok = 0;

        model.addAttribute("ok",ok);

        return "admin/backimg :: urlfragment";
    }

}