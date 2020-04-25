package com.sizaif.emsdemo.service.Announce;

import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.pojo.Announce.Announce;

import java.util.List;



public interface AnnounceService {

    SystemResult deleteByPrimaryKey(com.sizaif.emsdemo.pojo.Announce.Announce key);

    SystemResult insertSelective(com.sizaif.emsdemo.pojo.Announce.Announce record);

    SystemResult updateSelective(com.sizaif.emsdemo.pojo.Announce.Announce record);

    /**
     *  查询所有公告
     * @return
     */
    List<Announce> getAllAnnounce(String isEnabled);
}
