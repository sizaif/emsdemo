package com.sizaif.emsdemo.mapper;

import com.sizaif.emsdemo.pojo.Contest;
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

}
