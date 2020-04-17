package com.sizaif.emsdemo.mapper.Contest;

import com.sizaif.emsdemo.dto.ContestVO;
import com.sizaif.emsdemo.pojo.Contest.Contest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ContestMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Contest record);

    int insertSelective(Contest record);

    Contest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Contest record);

    int updateByPrimaryKey(Contest record);

    /**
     *  查询所有比赛列表
     * @return
     */
    List<Contest> getAllContest();


    /**
     * 通过比赛类别查询
     * @param type
     * @return
     */
    List<Contest> findByType(String type);


    /**
     * 通过用户Id 查询自己参加的比赛
     * @param memberId
     * @return
     */
    List<ContestVO> findContestByMemberId(int memberId);

    /**
     * 通过组队ID 查询队伍参加的比赛
     * @param teamId
     * @return
     */
    List<ContestVO> findContestByTeamId(int teamId);

}
