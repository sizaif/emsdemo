package com.sizaif.emsdemo.controller;

import com.sizaif.emsdemo.pojo.Announce.Announce;
import com.sizaif.emsdemo.service.Announce.AnnounceService;
import com.sizaif.emsdemo.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/25 21:29
 * @description：公告栏 控制层
 * @modified By：sizaif
 * @version: v1.0$
 */

@Controller
@RequestMapping("/admin/announce")
public class AnnounceController {

    private static final Logger logger = LoggerFactory.getLogger(AnnounceController.class);

    @Autowired
    private AnnounceService announceService;




    @RequestMapping("/toManagePage")
    public String toManagePage(Model model){

        List<Announce> allannounce = announceService.getAllAnnounce("all");
        model.addAttribute("AnnounceList",allannounce);
        return "production/Admin/announceList";
    }
    /**
     *  获得所有的公告
     * @return
     */
    @RequestMapping("/getAllAnnounce")
    @ResponseBody
    public String getAllAnnounce(@RequestParam(defaultValue = "true") String isEnabled){

        logger.debug(" 开始获取所有公告");

        try {
            List<Announce> allAnnounce = announceService.getAllAnnounce(isEnabled);
            return JsonUtils.objectToJson(allAnnounce);
        }catch(Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
