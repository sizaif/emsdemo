package com.sizaif.emsdemo.service.Contest;


import com.github.pagehelper.PageInfo;
import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.dto.ContestVO;
import com.sizaif.emsdemo.dto.MemberVO;
import com.sizaif.emsdemo.pojo.Contest.Contest;
import com.sizaif.emsdemo.pojo.Contest.ContestMemberkey;
import com.sizaif.emsdemo.pojo.Contest.ContestTeamKey;

import java.util.List;

public interface ContestService {



    List<Contest> findAllContestByPageF(int pageNum, int pageSize);

    /**
     *   默认分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ContestVO> findAllUserByPageS(int pageNum, int pageSize,String searchtype,String searchvalue,boolean isEnabled);



    /**
     * ↓↓↓↓↓↓↓↓
     * Start 维护赛事表
     * SQL: Contest
     *
     */



    SystemResult addContest(Contest Contest);

    /**
     * 查询所有比赛
     * @return
     */
    List<Contest> contestList();

    SystemResult updateContes(Contest Contest);

    Contest getContest(int id);

    SystemResult delContest(int id);

    /**
     * 根据用户获取比赛列表
     * @param memberId
     * @return
     */
    List<ContestVO> getContestByMember(Integer memberId);


    /**
     * 根据组队获取比赛列表
     * @param teamId
     * @return
     */
    List<ContestVO> getContestByTeam(Integer teamId);

    /**
     * ↑↑↑↑↑↑↑↑
     * End 维护赛事表
     * SQL: Contest
     *
     */

    /**
     *  ↓↓↓↓↓↓↓↓
     *  Start  维护 赛事成员表
     *  SQL: Contest_Member
     *  Alias: 单人赛报名表
     */

    /**
     *  报名,
     *  添加一个比赛用户关系
     * @param contestMemberkey
     * @return
     */
    SystemResult registeredContestMemberkey(ContestMemberkey contestMemberkey);


    SystemResult deleteContestMemberKey(ContestMemberkey contestMemberkey);

    SystemResult updateContestMemberKey();



    /**
     *  ↑↑↑↑↑↑↑↑
     *  END  维护 赛事成员表
     *  SQL: Contest_Member
     *  Alias: 单人赛报名表
     */



    /**
     *  ↓↓↓↓↓↓↓↓
     *  Start  维护 赛事组队表
     *  SQL: Contest_Team
     *  Alias: 组队赛报名表
     */

    /**
     *  报名,
     *  添加一个比赛用户关系
     * @param contestTeamKey
     * @return
     */
    SystemResult registeredContestTeamkey(ContestTeamKey contestTeamKey);


    SystemResult deleteContestTeamKey(ContestTeamKey contestTeamKey);

    SystemResult updateContestTeamKey(ContestTeamKey contestTeamKey);



    /**
     *  ↑↑↑↑↑↑↑↑
     *  END   维护 赛事组队表
     *  SQL: Contest_Team
     *  Alias: 组队赛报名表
     */

    List<ContestVO> getMebersByCid(Integer id);


    List<ContestVO> getTeamsByCid(Integer id);



}

