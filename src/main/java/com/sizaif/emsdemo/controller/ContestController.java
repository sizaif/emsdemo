package com.sizaif.emsdemo.controller;

import com.github.pagehelper.PageInfo;
import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.appoint.ContestServiceAppoint;
import com.sizaif.emsdemo.dto.ContestVO;
import com.sizaif.emsdemo.pojo.Contest.Contest;
import com.sizaif.emsdemo.pojo.Contest.ContestMemberkey;
import com.sizaif.emsdemo.pojo.Contest.ContestTeamKey;
import com.sizaif.emsdemo.service.Contest.ContestService;
import com.sizaif.emsdemo.utils.DateUtils;
import com.sizaif.emsdemo.utils.JsonUtils;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
                                                  @RequestParam(defaultValue = "5") int pageSize,
                                                  @RequestParam(defaultValue = "all") String level,
                                                  @RequestParam(defaultValue = "all") String type,
                                                  @RequestParam(defaultValue = "false") Boolean isEnabled){
        logger.debug("获得比赛列表！,分页");
        ModelAndView mav = new ModelAndView("production/Contest/contestList");
        try {
            PageInfo pageInfo = new PageInfo();
            if (level.equals("all") && type.equals("all")){
                // 默认
                 pageInfo = contestService.findAllUserByPageS(pageNum,pageSize,"all",null,isEnabled);
            }else if( !level.equals("all") && type.equals("all")){
                // 通过赛事级别划分
                pageInfo = contestService.findAllUserByPageS(pageNum,pageSize,"level",level,isEnabled);
            }else if( level.equals("all") && !type.equals("all")){
                pageInfo = contestService.findAllUserByPageS(pageNum,pageSize,"type",type,isEnabled);
            }
            //  start 处理 tags
            ContestServiceAppoint.TagTotags(pageInfo);
            //  end 处理 tags

//            logger.debug("----> sql 获得比赛列表 ->> list :" + list);
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
    @RequestMapping("/admin/contest/toAddContestPage")
    public String toAddPage(Model model){
        return "production/Admin/addContest";
    }

    @RequestMapping("/admin/contest/addContest")
    //@ResponseBody
    public String addContest(Contest contest) {

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


    /**
     * 更新赛事
     * @param contest
     * @return
     */
    @RequestMapping("/admin/contest/updateContest")
    @ResponseBody
    public String updateContest(Contest contest){


        /**
         *开始更新contest-->+ Contest{Cid=7, CreatorId=0, isEnabled=0,
         * Title='testsizaif', Memo='testsizaif', Length='1天0时0分',
         * Level='college', CreateDate='null', ModifyDate='2020-04-23 17:20:57',
         * StartTime='2020-04-15 00:00:00', EndTime='2020-04-16 00:00:00',
         * Type='组队赛', Tag='null'}
         */

        contest.setModifyDate(DateUtils.DatetoString(new Date()));
        // 设置时长
        contest.setLength(DateUtils.DataLength(contest.getStartTime(),contest.getEndTime()));


        logger.debug("开始更新contest-->+ "+contest.toString());

        try {
            SystemResult systemResult = contestService.updateContes(contest);
            if( systemResult.getStatus() == 200){
                // 更新成功
                return "200";
            }else{
                return systemResult.getMsg();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  删除赛事
     * @param id
     * @return
     */
    @RequestMapping("/admin/contest/delContest")
    @ResponseBody
    public String delContest(@RequestParam("id")Integer id){

        logger.debug("开始删除赛事");

        try {
            SystemResult systemResult = contestService.delContest(id);
            if(systemResult.getStatus() == 200){
                return "200";
            }else {
                return "100";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 赛事报名,  包括单人赛报名, 和组队赛报名两种方式
     * @param type
     * @param userid
     * @param contestid
     * @return
     */
    @RequestMapping("/contest/signup")
    @ResponseBody
    public String signup(@RequestParam("cid")Integer contestid,
                         @RequestParam("uid") Integer userid,
                         @RequestParam("type") String type){

        logger.debug("开始进行报名---> 报名方式: "+ type);

        if(type.equals("单人赛")){
            ContestMemberkey contestMemberkey = new ContestMemberkey(userid,contestid,true);
            SystemResult membersignresult = contestService.registeredContestMemberkey(contestMemberkey);
            if (membersignresult.getStatus()==100){
                // 重复报名
                return "100";
            }else if(membersignresult.getStatus()== 200){
                // 报名成功
                return "200";
            }
            else if(membersignresult.getStatus()== 101){
                return membersignresult.getMsg();
            }
        }else if( type.equals("组队赛")){
            ContestTeamKey contestTeamKey = new ContestTeamKey(userid,contestid,true);
            SystemResult teamsignresult = contestService.registeredContestTeamkey(contestTeamKey);
            if (teamsignresult.getStatus()==100){
                // 重复报名
                return "100";
            }else if(teamsignresult.getStatus()== 200){
                // 报名成功
                return "200";
            }
            else if(teamsignresult.getStatus()== 101){
                return teamsignresult.getMsg();
            }
        }

        return "102";
    }

    @RequestMapping("/contest/getContestsById")
    @ResponseBody
    public String getContestByUid(@RequestParam("type") String type,
                                  @RequestParam("id") Integer id){

        logger.debug("通过 id 查询已报名的赛事列表");

        if(type.equals("alone")){

            List<ContestVO> contestByMember = contestService.getContestByMember(id);
            return JsonUtils.objectToJson(contestByMember);
        }else if(type.equals("team")){
            List<ContestVO> contestByTeam = contestService.getContestByTeam(id);
            return JsonUtils.objectToJson(contestByTeam);
        }
        return null;
    }

    /**
     *  通过ID 获取团队/单人 的成员列表
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/contest/getInfosById")
    @ResponseBody
    public String getMebersByCid(@RequestParam("type") String type,
                                 @RequestParam("id") Integer id){
        logger.debug("开始进行查询用户/团队信息---> "+ type);
        if(type.equals("alone")){
            return JsonUtils.objectToJson(contestService.getMebersByCid(id));
        }else  if(type.equals("team")){
            return JsonUtils.objectToJson(contestService.getTeamsByCid(id));
        }
        return  null;
    }


}
