package com.sizaif.emsdemo.service.Announce.impl;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.mapper.Announce.AnnounceMapper;
import com.sizaif.emsdemo.pojo.Announce.Announce;
import com.sizaif.emsdemo.service.Announce.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/25 21:25
 * @description： 公告栏服务层
 * @modified By：sizaif
 * @version: v1.0$
 */

@Service
public class AnnounceServiceImpl implements AnnounceService {

    @Autowired
    private AnnounceMapper announceMapper;

    @Override
    public SystemResult deleteByPrimaryKey(Announce key) {

        int su = announceMapper.deleteByPrimaryKey(key);
        if( su > 0){
            return  new SystemResult(200,"删除公告成功");
        }else{
            return  new SystemResult(100,"删除公告是失败");
        }
    }

    @Override
    public SystemResult insertSelective(Announce record) {
        int su = announceMapper.insertSelective(record);
        if( su > 0){
            return  new SystemResult(200,"删除公告成功");
        }else{
            return  new SystemResult(100,"删除公告是失败");
        }
    }

    @Override
    public SystemResult updateSelective(Announce record) {
        int su = announceMapper.updateSelective(record);
        if( su > 0){
            return  new SystemResult(200,"删除公告成功");
        }else{
            return  new SystemResult(100,"删除公告是失败");
        }
    }

    /**
     * 查询所有公告
     *
     * @return
     */
    @Override
    public List<Announce> getAllAnnounce(String isEnabled) {
        if( isEnabled.equals("true") ){
            return announceMapper.getAllAnnounce(new Announce(1));
        }else if(isEnabled.equals("false")){
            return announceMapper.getAllAnnounce(new Announce(0));
        }else
            return announceMapper.getAllAnnounce(new Announce());
    }
}
