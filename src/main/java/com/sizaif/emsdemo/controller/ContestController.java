package com.sizaif.emsdemo.controller;

import com.github.pagehelper.PageInfo;
import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.pojo.Contest.Contest;
import com.sizaif.emsdemo.service.Contest.ContestService;
import com.sizaif.emsdemo.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller

public class ContestController {

    private static final Logger logger = LoggerFactory.getLogger(ContestController.class);

    @Autowired
    private ContestService contestService;

    /**
     * 到比赛列表
     * @return
     */
    @RequestMapping("/contest/toContestList")
    public ModelAndView queryAllContestlistByPage(@RequestParam(defaultValue = "1") int pageNum,
                                                  @RequestParam(defaultValue = "5") int pageSize){
        logger.debug("获得比赛列表！,分页");
        ModelAndView mav = new ModelAndView("production/Contest/contestList");
        try {
            List<Contest> list = contestService.findAllContestByPageF(pageNum,pageSize);
            PageInfo pageInfo = contestService.findAllUserByPageS(pageNum,pageSize);
            logger.debug("----> sql 获得比赛列表 ->> list :" + list);
            mav.addObject("pageList",list);
            mav.addObject("pageInfo",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("----> sql 获得比赛列表 ->> ContestPage 失败异常");
        }
        return  mav;
    }

    /**
     *  去添加页面
     * @param model
     * @return
     */
    @RequestMapping("/admin/toAddContestPage")
    public String toAddPage(Model model){
        return "production/Admin/addContest";
    }

    @RequestMapping("/admin/addContest")
    //@ResponseBody
    public String addRole(Contest contest) {

        logger.debug("添加赛事！Contest：" + contest);
        SystemResult systemResult = new SystemResult(100,"操作错误，请您联系技术管理员");
//        ModelAndView mav = new ModelAndView("redirect: /contest/toContestList");
        try {

            // 设置添加时间
            contest.setCreateDate(DateUtils.DatetoString(new Date()));
            contest.setModifyDate(DateUtils.DatetoString(new Date()));
            // 设置时长
            contest.setLength(DateUtils.DataLength(contest.getStartTime(),contest.getEndTime()));
            // 设置默认启用
            contest.setIsEnabled(1);
           systemResult = contestService.addContest(contest);

//            mav.addObject("msg",systemResult1.getMsg());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加赛事！异常！", e);
        }
        return  "redirect:/contest/toContestList";
    }

}
