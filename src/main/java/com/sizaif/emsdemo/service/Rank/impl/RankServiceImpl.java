package com.sizaif.emsdemo.service.Rank.impl;

import com.sizaif.emsdemo.dto.RankVO;
import com.sizaif.emsdemo.mapper.Rank.RankMapper;
import com.sizaif.emsdemo.service.Contest.impl.ContestServiceImpl;
import com.sizaif.emsdemo.service.Rank.RankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/29 1:28
 * @description：提交表,排名表的服务层
 * @modified By：sizaif
 * @version: v1.0$
 */

@Service
public class RankServiceImpl implements RankService {

    private static final Logger logger = LoggerFactory.getLogger(RankServiceImpl.class);
    @Autowired
    private RankMapper rankMapper;
    /**
     * 根据赛事ID  获得 当前赛事的 个人赛排名
     *
     * @param cid
     * @return
     */
    @Override
    public List<RankVO> getAloneRankByCid(Integer cid) {

        return rankMapper.getAloneRankListByCId(cid);
    }

    /**
     * 根据赛事ID  获得 当前赛事的 组队赛排名
     *
     * @param cid
     * @return
     */
    @Override
    public List<RankVO> getTeamRankByCid(Integer cid) {
        return rankMapper.getTeamRankListByCId(cid);
    }
}
