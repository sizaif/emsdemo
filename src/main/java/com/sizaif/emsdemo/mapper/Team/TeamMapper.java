package com.sizaif.emsdemo.mapper.Team;


import com.sizaif.emsdemo.dto.TeamVO;
import com.sizaif.emsdemo.pojo.Contest.Team;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeamMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Team record);


    /**
     *  查询所有队伍列表
     * @return
     */
    List<Team> getAllTeam();


    /**
     *  通过teamid 查找所有队员信息
     * @param teamid
     * @return
     */
    List<TeamVO> getTeamMemberInfo(Integer teamid);


    List<TeamVO> getContestTeamVOListByCid(Integer cid);
}
