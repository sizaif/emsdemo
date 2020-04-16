package com.sizaif.emsdemo.controller;

import com.github.pagehelper.PageInfo;
import com.sizaif.emsdemo.pojo.Contest;
import com.sizaif.emsdemo.service.ContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.util.List;

@Controller
@RequestMapping("/contest")
public class ContestController {

    private static final Logger logger = LoggerFactory.getLogger(ContestController.class);

    @Autowired
    private ContestService contestService;

    /**
     * 到比赛列表
     * @return
     */
    @RequestMapping("/toContestList")
    public ModelAndView queryAllContestlist(){
        logger.debug("获得比赛列表！");

        //返回文件路径
        ModelAndView mav = new ModelAndView("production/Contest/contestList");
        try {
            List<Contest> contestList = contestService.contestList();
            logger.debug("----> sql 获得比赛列表 ->> toContestList :" + contestList);

            mav.addObject("contestList",contestList);
            mav.addObject("msg","获得比赛列成功");

        }catch(Exception e){
            e.printStackTrace();
            logger.debug("----> sql 获得比赛列表 ->> toContest 失败异常");
        }
        return  mav;
    }

    @RequestMapping("/testPageHelper")
    public ModelAndView queryAllContestlistByPage(@RequestParam(defaultValue = "1") int pageNum,
                                                  @RequestParam(defaultValue = "2") int pageSize){
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

}
