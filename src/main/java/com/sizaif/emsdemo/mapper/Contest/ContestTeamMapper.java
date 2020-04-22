package com.sizaif.emsdemo.mapper.Contest;

import com.sizaif.emsdemo.pojo.Contest.ContestTeamKey;
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


    /**
     *  通过 cid 和 tid 查找
     * @param contestTeamKey
     * @return
     */
    ContestTeamKey getCTByPrimaryKey(ContestTeamKey contestTeamKey);

    /**
     *  根据isEnabled 查找
     * @param contestTeamKey
     * @return
     */
    List<ContestTeamKey> getCTByisEnabled(ContestTeamKey contestTeamKey);

}
