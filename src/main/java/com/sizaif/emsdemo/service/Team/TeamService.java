package com.sizaif.emsdemo.service.Team;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.dto.TeamVO;
import com.sizaif.emsdemo.pojo.Announce.Announce;
import com.sizaif.emsdemo.pojo.Contest.Team;

import java.util.List;

public interface TeamService {


    SystemResult deleteByPrimaryKey(Team key);

    SystemResult insertSelective(Team record);

    SystemResult updateSelective(Team record);

    /**
     *  查询某个比赛的  所有参赛队伍
     * @return
     */
    List<TeamVO> getTeamMemberVOInfo(TeamVO teamVO);

    /**
     *  查询所有team 列表
     * @return
     */
    List<Team> getAllTeam();


    /**
     *  根据某一赛事的ID  获取 参赛Team 列表
     * @param cid
     * @return
     */
    List<TeamVO> getContestTeamVOByCid(Integer cid);


    /**
     *  根据ID  获取 team  信息
     * @param id
     * @return
     */
    Team getTeamByID(Integer id);
}
