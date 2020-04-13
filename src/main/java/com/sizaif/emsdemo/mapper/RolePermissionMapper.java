package com.sizaif.emsdemo.mapper;


import com.sizaif.emsdemo.pojo.RolePermissionKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RolePermissionMapper {
    int deleteByPrimaryKey(RolePermissionKey key);

    int insert(RolePermissionKey record);

    int insertSelective(RolePermissionKey record);

	List<RolePermissionKey> findByRole(int roleId);
}