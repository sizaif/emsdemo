package com.sizaif.emsdemo.mapper;

import com.sizaif.emsdemo.pojo.ContestMemberkey;
import com.sizaif.emsdemo.pojo.ContestTeamKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ContestTeamMapper {


    int deleteByPrimaryKey(ContestTeamKey key);

    int insert(ContestTeamKey record);

    int insertSelective(ContestTeamKey record);

    /**
     * 通过队伍Id查找
     * @param TeamId
     * @return
     */
    List<ContestTeamKey> findByTeam(int TeamId);

    /**
     * 通过比赛ID查找所有参赛队伍
     * @param contestId
     * @return
     */
    List<ContestTeamKey> findByContest(int contestId);


}
