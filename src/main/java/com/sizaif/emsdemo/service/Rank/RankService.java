package com.sizaif.emsdemo.service.Rank;

import com.sizaif.emsdemo.dto.RankVO;

import java.util.List;

public interface RankService {


    /**
     *  根据赛事ID  获得 当前赛事的 个人赛排名
     * @param cid
     * @return
     */
    List<RankVO> getAloneRankByCid(Integer cid);


    /**
     * 根据赛事ID  获得 当前赛事的 组队赛排名
     * @param cid
     * @return
     */
    List<RankVO> getTeamRankByCid(Integer cid);
}
