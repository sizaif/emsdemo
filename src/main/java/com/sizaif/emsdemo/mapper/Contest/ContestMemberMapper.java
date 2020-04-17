package com.sizaif.emsdemo.mapper.Contest;

import com.sizaif.emsdemo.pojo.Contest.ContestMemberkey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ContestMemberMapper {


    int deleteByPrimaryKey(ContestMemberkey key);

    int insert(ContestMemberkey record);

    int insertSelective(ContestMemberkey record);

    /**
     * 通过用户Id查找
     * @param memberId
     * @return
     */
    List<ContestMemberkey> findByMember(int memberId);

    /**
     * 通过比赛ID查找所有参赛人员
     * @param contestId
     * @return
     */
    List<ContestMemberkey> findByContest(int contestId);


}
