package com.sizaif.emsdemo.service.Contest.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.dto.ContestVO;
import com.sizaif.emsdemo.mapper.Contest.ContestMapper;
import com.sizaif.emsdemo.pojo.Contest.Contest;
import com.sizaif.emsdemo.pojo.Contest.ContestMemberkey;
import com.sizaif.emsdemo.pojo.Contest.ContestTeamKey;
import com.sizaif.emsdemo.service.Contest.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/16 9:50
 * @description：赛事Service层
 * @modified By：sizaif
 * @version: v1.0$
 */

@Service
public class ContestServiceImpl implements ContestService {


    @Autowired
    private ContestMapper contestMapper;


    @Override
    public List<Contest> findAllContestByPageF(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<Contest> contestList = contestMapper.getAllContest();
        return  contestList;
    }

    @Override
    public PageInfo<ContestVO> findAllUserByPageS(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ContestVO> contestList = contestMapper.getAllContestVO();
        return new PageInfo<ContestVO>(contestList);
    }

    @Override
    public SystemResult addContest(Contest Contest) {

        try {
            int su = contestMapper.insertSelective(Contest);
            if( su > 0 ){

                return  new SystemResult(200,"添加一个比赛成功");
            }
            else
                return  new SystemResult(100,"添加一个比赛失败");
        }catch(Exception e){
            e.printStackTrace();
        }
        return  new SystemResult(100,"添加一个比赛失败");
    }

    /**
     * 查询所有比赛
     *
     * @return
     */
    @Override
    public List<Contest> contestList() {
        return contestMapper.getAllContest();
    }

    @Override
    public SystemResult updateContes(Contest Contest) {
        try {
            int su = contestMapper.updateByPrimaryKeySelective(Contest);
            if( su > 0 ){

                return  new SystemResult(200,"更新一个比赛成功");
            }
            else
                return  new SystemResult(100,"更新一个比赛失败");
        }catch(Exception e){
            e.printStackTrace();
        }
        return  new SystemResult(100,"添加一个比赛失败");
    }

    @Override
    public Contest getContest(int id) {
        return contestMapper.selectByPrimaryKey(id);
    }

    @Override
    public SystemResult delContest(int id) {
        try {
            int su = contestMapper.deleteByPrimaryKey(id);
            if( su > 0 ){

                return  new SystemResult(200,"删除一个比赛成功");
            }
            else{
                return  new SystemResult(100,"删除一个比赛失败");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return  new SystemResult(100,"删除一个比赛失败");
    }

    /**
     * 根据用户获取比赛列表
     *
     * @param memberId
     * @return
     */
    @Override
    public List<ContestVO> getContestByMember(Integer memberId) {

        return contestMapper.findContestByMemberId(memberId);

    }

    /**
     * 根据组队获取比赛列表
     *
     * @param teamId
     * @return
     */
    @Override
    public List<ContestVO> getContestByTeam(Integer teamId) {
        return contestMapper.findContestByTeamId(teamId);
    }

    /**
     * 报名,
     * 添加一个比赛用户关系
     *
     * @param contestMemberkey
     * @return
     */
    @Override
    public SystemResult registeredContestMemberkey(ContestMemberkey contestMemberkey) {
        return null;
    }

    @Override
    public SystemResult deleteContestMemberKey(ContestMemberkey contestMemberkey) {
        return null;
    }

    @Override
    public SystemResult updateContestMemberKey() {
        return null;
    }

    /**
     * 报名,
     * 添加一个比赛用户关系
     *
     * @param contestTeamKey
     * @return
     */
    @Override
    public SystemResult registeredContestTeamkey(ContestTeamKey contestTeamKey) {
        return null;
    }

    @Override
    public SystemResult deleteContestTeamKey(ContestTeamKey contestTeamKey) {
        return null;
    }

    @Override
    public SystemResult updateContestTeamKey(ContestTeamKey contestTeamKey) {
        return null;
    }
}
