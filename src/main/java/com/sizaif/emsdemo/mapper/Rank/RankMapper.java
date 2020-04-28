package com.sizaif.emsdemo.mapper.Rank;


import com.sizaif.emsdemo.dto.RankVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RankMapper {


    /**
     * 通过 赛事ID 获得一场单人赛事的 比赛成绩
     * @param cid
     * @return
     */
    List<RankVO> getAloneRankListByCId(Integer cid);

    /**
     * 通过 赛事ID 获得一场组队赛事的 比赛成绩
     * @param cid
     * @return
     */
    List<RankVO> getTeamRankListByCId(Integer cid);

}
