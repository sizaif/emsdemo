package com.sizaif.emsdemo.controller;

import com.github.pagehelper.PageInfo;
import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.appoint.ContestServiceAppoint;
import com.sizaif.emsdemo.dto.ContestVO;
import com.sizaif.emsdemo.dto.MemberVO;
import com.sizaif.emsdemo.dto.TeamVO;
import com.sizaif.emsdemo.pojo.Contest.Contest;
import com.sizaif.emsdemo.pojo.Contest.ContestMemberkey;
import com.sizaif.emsdemo.pojo.Contest.ContestTeamKey;
import com.sizaif.emsdemo.pojo.Contest.Team;
import com.sizaif.emsdemo.pojo.User.Member;
import com.sizaif.emsdemo.service.Contest.ContestService;
import com.sizaif.emsdemo.service.Team.TeamService;
import com.sizaif.emsdemo.service.User.MemberService;
import com.sizaif.emsdemo.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static com.sizaif.emsdemo.config.UserConfig.*;

@Controller

public class ContestController {

    private static final Logger logger = LoggerFactory.getLogger(ContestController.class);

    @Autowired
    private ContestService contestService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private TeamService teamService;
    /**
     * 到比赛列表
     * @return
     */
    @RequestMapping("/contest/toContestList")
    public ModelAndView queryAllContestlistByPage(@RequestParam(defaultValue = "1") int pageNum,
                                                  @RequestParam(defaultValue = "5") int pageSize,
                                                  @RequestParam(defaultValue = "all") String level,
                                                  @RequestParam(defaultValue = "all") String type,
                                                  @RequestParam(defaultValue = "0") Integer isEnabled){

        logger.debug("获得比赛列表！,分页"+pageNum+"   "+pageSize+"   "+level+"  "+type+" "+isEnabled);
        ModelAndView mav = new ModelAndView("production/Contest/contestList");
        try {
            PageInfo pageInfo = new PageInfo();
            if (level.equals("all") && type.equals("all")){
                // 默认  管理员可以查询所以有 enabled = 1
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

    /**
     * 添加赛事
     * @param contest
     * @return
     */
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
            // 设置默认未启用
            contest.setIsEnabled(0);
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

        if(type.equals(membertype)){
            ContestMemberkey contestMemberkey = new ContestMemberkey(contestid,userid,2);
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
        }else if( type.equals(teamtype)){
            ContestTeamKey contestTeamKey = new ContestTeamKey(contestid,userid,2);
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


    /**
     *  到审核用户报名界面
     * @param cid
     * @param type
     * @return
     */
    @RequestMapping("/admin/contest/toMemberListPage")
    public ModelAndView toContestMemberListPage(@RequestParam("cid") Integer cid,
                                          @RequestParam("type") String type){
        logger.debug("根据赛事id查询报名的用户或组队：");
        ModelAndView mav=new ModelAndView("production/Contest/contestMemberList");

        mav.addObject("cid",cid);
        if(type.equals(contestalonetype)){
            mav.addObject("type","alone");
            ContestVO contestByMember = contestService.getMebersByCid(cid);
            mav.addObject("data",contestByMember);
        } else if(type.equals(contestteamtype)){
            mav.addObject("type","team");
            ContestVO contestByTeam = contestService.getTeamsByCid(cid);
            mav.addObject("data",contestByTeam);
        }
        return mav;
    }

    /**
     * 查看我的已报名的赛事
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/contest/getContestsByUid")
    public ModelAndView getContestByUid(@RequestParam("type") String type,
                                  @RequestParam("id") Integer id){

        ModelAndView mav = new ModelAndView("production/Contest/MycontestList");
        logger.debug("通过 id 查询已报名的赛事列表");

        mav.addObject("type",type);
        // 账户类型
        if(type.equals(membertype)){
            List<ContestVO> contestByMember = contestService.getContestByMember(id);

            logger.debug(contestByMember.toString());
            if(contestByMember.size()>0)
                mav.addObject("info",contestByMember.get(0).getMemberList().get(0));
            else{
                Member member = memberService.QueryOneMemberInfoByID(id);
                mav.addObject("info",member);
            }
            mav.addObject("data",contestByMember);

        }else if(type.equals(teamtype)){
            List<ContestVO> contestByTeam = contestService.getContestByTeam(id);
            if(contestByTeam.size()>0){
                mav.addObject("info",contestByTeam.get(0).getTeamList().get(0));
            }else{
                Team team = teamService.getTeamByID(id);
                mav.addObject("info",team);
            }
            mav.addObject("data",contestByTeam);
        }
        return mav;
    }

    /**
     *  审核用户/组队 参赛状态
     * @param type
     * @param cid
     * @return
     */
    @RequestMapping("/admin/contest/checkCmtEnabled")
    @ResponseBody
    public String getCMTInfoByCid(@RequestParam("type") String type,
                                  @RequestParam("cid") Integer cid,
                                  @RequestParam("id") Integer id,
                                  @RequestParam("isEnabled") Integer isEnabled){

        logger.debug("通过 赛事id , 用户/组队id  改变 赛事报名状态");

        if(type.equals("alone")){
            // 相反状态
            ContestMemberkey cmk = new ContestMemberkey(cid,id,isEnabled);
            SystemResult systemResult = contestService.updateContestMemberKey(cmk);
            return systemResult.getStatus().toString();
        }else if(type.equals("team")){
            ContestTeamKey ctk = new ContestTeamKey(cid,id,isEnabled);
            SystemResult systemResult = contestService.updateContestTeamKey(ctk);
            return systemResult.getStatus().toString();
        }
        return null;
    }


    /**
     * 到赛事列表 查询参赛人员
     * @return
     */
    @RequestMapping("/contest/toContestInfoList")
    public ModelAndView toTeamList(){

        logger.debug("获得所有赛事的列表2");
        ModelAndView mav = new ModelAndView("production/Contest/contestList2");

        List<Contest> contests = contestService.contestList();

        mav.addObject("ContestList",contests);

        return mav;
    }


    /**
     * 到赛事列表 查询获奖名单
     * @return
     */
    @RequestMapping("/contest/toContestInfoList2")
    public ModelAndView toTeamList2(){

        logger.debug("获得所有赛事的列表3");
        ModelAndView mav = new ModelAndView("production/Contest/contestList3");

        List<Contest> contests = contestService.contestList();

        mav.addObject("ContestList",contests);

        return mav;
    }

    /**
     * 根据赛事ID 和赛事类型 获取参赛的成员列表 或者 组队列表信息
     * @param id
     * @param type
     * @return
     */
    @RequestMapping("/contest/ContestTMList")
    public ModelAndView toContestTeamInfoList(@RequestParam("id") Integer id,
                                              @RequestParam("type") String type){

        logger.debug("获得某个比赛的已报名队伍列表 id: "+ id +"  type = "+type);
        ModelAndView mav = new ModelAndView("production/Contest/cdteammemberList");

        // 赛事信息
        Contest contest = contestService.getContest(id);
        mav.addObject("contest",contest);
        if(type.equals( contestalonetype)){

            // 个人赛 参赛成员列表
            mav.addObject("type",type);
            List<MemberVO> memberVOByCid = memberService.getMemberVOByCid(id);
            mav.addObject("MemberList",memberVOByCid);

        }else if(type.equals( contestteamtype)){


            // 组队赛 参赛组队列表
            List<TeamVO> contestTeamVOByCid = teamService.getContestTeamVOByCid(id);

            mav.addObject("type",type);
            for (TeamVO teamVO : contestTeamVOByCid) {
                List<MemberVO> teamMember = teamVO.getTeamMember();
                List<MemberVO> newTeamMember = new ArrayList<>();
                for (MemberVO memberVO : teamMember) {
                    // 教练
                    if(memberVO.getId() == teamVO.getCaptainId()){
                        teamVO.setTeacherMember(memberVO);
                    }else{
                        // 其他队员
                        newTeamMember.add(memberVO);
                    }
                }
                teamVO.setTeamMember(newTeamMember);
            }
            mav.addObject("TeamList",contestTeamVOByCid);
        }
        return mav;
    }

    /**
     *  根据赛事ID  得到获奖名单
     * @param id
     * @param type
     * @return
     */
    @RequestMapping("/contest/ContestRankList")
    public ModelAndView toContestRankList(@RequestParam("id") Integer id,
                                              @RequestParam("type") String type){

        logger.debug("获得某个比赛的获奖名单 id: "+ id +"  type = "+type);
        ModelAndView mav = new ModelAndView("production/Contest/cdteammemberList2");

        // 赛事信息
        Contest contest = contestService.getContest(id);
        mav.addObject("contest",contest);
        if(type.equals( contestalonetype)){

            // 个人赛 参赛成员列表
            mav.addObject("type",type);
            List<MemberVO> memberVOByCid = memberService.getMemberVOByCid(id);
            mav.addObject("MemberList",memberVOByCid);

        }else if(type.equals( contestteamtype)){


            // 组队赛 参赛组队列表
            List<TeamVO> contestTeamVOByCid = teamService.getContestTeamVOByCid(id);

            mav.addObject("type",type);
            for (TeamVO teamVO : contestTeamVOByCid) {
                List<MemberVO> teamMember = teamVO.getTeamMember();
                List<MemberVO> newTeamMember = new ArrayList<>();
                for (MemberVO memberVO : teamMember) {
                    // 教练
                    if(memberVO.getId() == teamVO.getCaptainId()){
                        teamVO.setTeacherMember(memberVO);
                    }else{
                        // 其他队员
                        newTeamMember.add(memberVO);
                    }
                }
                teamVO.setTeamMember(newTeamMember);
            }
            mav.addObject("TeamList",contestTeamVOByCid);
        }
        return mav;
    }


}
