package com.sizaif.emsdemo.service.Auth;



import com.sizaif.emsdemo.Result.SystemResult;
import com.sizaif.emsdemo.dto.PermissionVO;
import com.sizaif.emsdemo.dto.RoleVO;
import com.sizaif.emsdemo.pojo.User.Permission;
import com.sizaif.emsdemo.pojo.User.Role;

import java.util.List;


public interface AuthService {

	SystemResult addPermission(Permission permission);

	List<Permission> permList();

	SystemResult updatePerm(Permission permission);

	Permission getPermission(int id);

	SystemResult delPermission(int id);

	/**
	 * 查询所有角色
	 * @return
	 */
	List<Role> roleList();

	/**
	 * 关联查询权限树列表
	 * @return
	 */
	List<PermissionVO> findPerms();

	/**
	 * 添加角色
	 * @param role
	 * @param permIds
	 * @return
	 */
	SystemResult addRole(Role role, String permIds);

	RoleVO findRoleAndPerms(Integer id);

	/**
	 * 更新角色并授权
	 * @param role
	 * @param permIds
	 * @return
	 */
	SystemResult updateRole(Role role, String permIds);

	/**
	 * 删除角色以及它对应的权限
	 * @param id
	 * @return
	 */
	SystemResult delRole(int id);

	/**
	 * 查找所有角色
	 * @return
	 */
	List<Role> getRoles();

	/**
	 * 根据用户获取角色列表
	 * @param userId
	 * @return
	 */
	List<Role> getRoleByUser(Integer userId);

	/**
	 * 根据角色id获取权限数据
	 * @param id
	 * @return
	 */
	List<Permission> findPermsByRoleId(Integer id);

//	/**
//	 * 根据用户id获取权限数据
//	 * @param id
//	 * @return
//	 */
//	List<PermissionVO> getUserPerms(Integer id);


}
