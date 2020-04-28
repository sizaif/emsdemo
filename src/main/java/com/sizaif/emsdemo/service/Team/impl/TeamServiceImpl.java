package com.sizaif.emsdemo.service.Team.impl;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.controller.TeamController;
import com.sizaif.emsdemo.dto.TeamVO;
import com.sizaif.emsdemo.mapper.Team.TeamMapper;
import com.sizaif.emsdemo.mapper.User.UserMapper;
import com.sizaif.emsdemo.pojo.Contest.Team;
import com.sizaif.emsdemo.service.Team.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/28 15:25
 * @description：team service层
 * @modified By：sizaif
 * @version: v1.0$
 */

@Service
public class TeamServiceImpl implements TeamService {

    private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);



    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public SystemResult deleteByPrimaryKey(Team key) {

        // 先删除team 表 在删除user 表
        int su = teamMapper.deleteByPrimaryKey(key.getId());
        if( su > 0){
            int s2 = userMapper.deleteUserById(key.getId());
            if(s2 > 0){
                return  new SystemResult(200,"删除team账户成功");
            }else{
                return new SystemResult(100,"删除team账户失败");
            }
        }else
            return new SystemResult(100,"删除team 账户失败");
    }

    @Override
    public SystemResult insertSelective(Team record) {

        int su = teamMapper.insertSelective(record);
        if( su > 0 ){
            return  new SystemResult(200,"添加成功");
        }else
            return new SystemResult(100,"添加失败");
    }

    @Override
    public SystemResult updateSelective(Team record) {
        int su = teamMapper.updateByPrimaryKeySelective(record);
        if( su > 0 ){
            return  new SystemResult(200,"更新成功");
        }else
            return new SystemResult(100,"更新失败");
    }

    /**
     * 查询所有公告
     *
     * @param teamVO
     * @return
     */
    @Override
    public List<TeamVO> getTeamMemberVOInfo(TeamVO teamVO) {
        return null;
    }

    /**
     * 查询所有team 列表
     *
     * @return
     */
    @Override
    public List<Team> getAllTeam() {
        return  teamMapper.getAllTeam();
    }

    @Override
    public List<TeamVO> getContestTeamVOByCid(Integer cid) {
        return teamMapper.getContestTeamVOListByCid(cid);
    }

    /**
     * 根据ID  获取 team  信息
     *
     * @param id
     * @return
     */
    @Override
    public Team getTeamByID(Integer id) {
        return teamMapper.selectByPrimaryKey(id);
    }


}
