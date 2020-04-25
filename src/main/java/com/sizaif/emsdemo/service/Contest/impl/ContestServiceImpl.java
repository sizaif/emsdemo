package com.sizaif.emsdemo.service.Contest.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.dto.ContestVO;
import com.sizaif.emsdemo.dto.ContestVO2;
import com.sizaif.emsdemo.dto.MemberVO;
import com.sizaif.emsdemo.mapper.Contest.ContestMapper;
import com.sizaif.emsdemo.mapper.Contest.ContestMemberMapper;
import com.sizaif.emsdemo.mapper.Contest.ContestTeamMapper;
import com.sizaif.emsdemo.pojo.Contest.Contest;
import com.sizaif.emsdemo.pojo.Contest.ContestMemberkey;
import com.sizaif.emsdemo.pojo.Contest.ContestTeamKey;
import com.sizaif.emsdemo.service.Contest.ContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    private static final Logger logger = LoggerFactory.getLogger(ContestServiceImpl.class);

    @Autowired
    private ContestMapper contestMapper;
    @Autowired
    private ContestMemberMapper contestMemberMapper;
    @Autowired
    private ContestTeamMapper contestTeamMapper;



    @Override
    public List<Contest> findAllContestByPageF(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<Contest> contestList = contestMapper.getAllContest();
        return  contestList;
    }

    @Override
    public PageInfo<ContestVO> findAllUserByPageS(int pageNum, int pageSize,String searchtype,String searchvalue,boolean isEnabled) {
        PageHelper.startPage(pageNum,pageSize);
        List<ContestVO> contestList = null;
        HashMap<String, Object> hashMap = new HashMap<>();
        if(searchtype.equals("all") && isEnabled == true){
            // 管理员模式
            hashMap.put("level",null);
            hashMap.put("type",null);
            // 查询所有的赛事
            hashMap.put("isEnabled",null);
            contestList = contestMapper.getAllContestVO(hashMap);
        } else if (searchtype.equals("all") && isEnabled == false) {
            // 普通列表模式
            hashMap.put("level",null);
            hashMap.put("type",null);
            // 查询所有的已启用的赛事
            hashMap.put("isEnabled",true);
            contestList = contestMapper.getAllContestVO(hashMap);
        }else if(searchtype.equals("level") && isEnabled == true){
            hashMap.put("level", searchvalue);
            hashMap.put("type", null);
            // 管理员分类查询
            hashMap.put("isEnabled", null);
            contestList = contestMapper.getAllContestVO(hashMap);
        } else if (searchtype.equals("level") && isEnabled == false) {
            hashMap.put("level", searchvalue);
            hashMap.put("type", null);
            // 只查询已启用的赛事
            hashMap.put("isEnabled", true);
            contestList = contestMapper.getAllContestVO(hashMap);
        } else if(searchtype.equals("type") && isEnabled == true){
            hashMap.put("level", null);
            hashMap.put("type", searchvalue);
            // 管理员分类查询
            hashMap.put("isEnabled", null);
            contestList = contestMapper.getAllContestVO(hashMap);
        } else if (searchtype.equals("type") && isEnabled == false) {
            hashMap.put("level", null);
            hashMap.put("type", searchvalue);
            // 只查询已启用的赛事
            hashMap.put("isEnabled", true);
            contestList = contestMapper.getAllContestVO(hashMap);
        }
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

            // 先删除关系表
            List<ContestMemberkey> byContest = contestMemberMapper.findByContest(id);
            for (ContestMemberkey contestMemberkey : byContest) {
                contestMemberMapper.deleteByPrimaryKey(contestMemberkey);
            }
            List<ContestTeamKey> byContest2 = contestTeamMapper.findByContest(id);
            for (ContestTeamKey contestTeamKey : byContest2) {
                contestTeamMapper.deleteByPrimaryKey(contestTeamKey);
            }
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

        logger.debug("开始对contest_member 查重");
        // 先查重
        ContestMemberkey existCM = contestMemberMapper.getCMByPrimaryKey(contestMemberkey);
        if(null != existCM){
            return new SystemResult(100,"您已报名!,不能重复报名!");
        }else {
            int su = contestMemberMapper.insertSelective(contestMemberkey);
            if(su > 0 ){
                return  new SystemResult(200,"报名成功!");
            }else
                return  new SystemResult(101,"报名失败,请联系管理员!, 错误代码: CMSIGN0001");
        }

    }

    @Override
    public SystemResult deleteContestMemberKey(ContestMemberkey contestMemberkey) {
        return null;
    }

    @Override
    public SystemResult updateContestMemberKey(ContestMemberkey contestMemberkey) {
        int su = contestMemberMapper.updateSelective(contestMemberkey);
        if( su > 0 ){
            return  new SystemResult(200,"跟新状态成功");
        }
        else
            return  new SystemResult(100,"跟新状态失败");
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
        logger.debug("开始对contest_team 查重");
        // 先查重
        ContestTeamKey existCT = contestTeamMapper.getCTByPrimaryKey(contestTeamKey);
        if(null != existCT){
            return new SystemResult(100,"您已报名!,不能重复报名!");
        }else {
            int su = contestTeamMapper.insertSelective(contestTeamKey);
            if(su > 0 ){
                return  new SystemResult(200,"报名成功!");
            }else
                return  new SystemResult(101,"报名失败,请联系管理员!, 错误代码: CTSIGN0001");
        }
    }

    @Override
    public SystemResult deleteContestTeamKey(ContestTeamKey contestTeamKey) {
        return null;
    }

    @Override
    public SystemResult updateContestTeamKey(ContestTeamKey contestTeamKey) {
        int su = contestTeamMapper.updateSelective(contestTeamKey);
        if( su > 0 ){
            return  new SystemResult(200,"跟新状态成功");
        }
        else
            return  new SystemResult(100,"跟新状态失败");
    }

    /**
     * ↑↑↑↑↑↑↑↑
     * END   维护 赛事组队表
     * SQL: Contest_Team
     * Alias: 组队赛报名表
     *
     * @param id
     */


    @Override
    public ContestVO getMebersByCid(Integer id) {

        ContestVO membersByContestId = contestMapper.findMembersByContestId(id);
        return  membersByContestId;
    }

    @Override
    public ContestVO getTeamsByCid(Integer id) {
        return  contestMapper.findTeamsByContestId(id);
    }

}
