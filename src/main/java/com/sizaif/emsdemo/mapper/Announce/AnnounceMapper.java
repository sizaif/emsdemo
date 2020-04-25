package com.sizaif.emsdemo.mapper.Announce;

import com.sizaif.emsdemo.pojo.Announce.Announce;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface AnnounceMapper {

    int deleteByPrimaryKey(Announce key);

    int insertSelective(Announce record);

    int updateSelective(Announce record);

    /**
     *  查询所有公告
     * @return
     */
    List<Announce> getAllAnnounce(Announce record);
}
