package cn.tedu.store.mapper;


import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import cn.tedu.store.entity.User;
/**
 * 处理用户数据的持久层接口
 */
public interface UserMapper {
	/**
	 * 插入用户数据
	 * @param user 用户数绝
	 * @return 受影响的行数
	 */
	Integer addNew(User user);
	/**
	 * 根据用户名查询用户信息
	 * @param username 用户名
	 * @return 匹配用户的数据，如果没有匹配的数据，则返回null
	 */
	User findByUsername(String username);
	
	/**
	 * 根据用户id查询用户信息
	 * @param uid 用户id
	 * @return 用户名，盐，密码
	 */
	User findByUid(Integer uid);
	
	/**
	 *修改用户密码
	 * @param uid 用户id
	 * @param password 新密码
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 受影响行数
	 */
	Integer updatePassword(@Param("uid") Integer uid,
			@Param("password") String password,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 修改用户信息
	 * @param uid 必须包含用户的id
	 * @return	受影响行数
	 */
	Integer updateUserInfo(User user);
	
	/**
	 * 修改用户头像
	 * @param uid 用户id
	 * @param avater 头像的路径
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 收影响的行数
	 */
	/*@Update("") xml中sql语句可以写在这里*/
	Integer updateAvatar(@Param("uid") Integer uid,
			@Param("avater") String avater,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);
}
