package cn.tedu.store.service;


import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserConfilctException;
import cn.tedu.store.service.ex.UserNotFoundException;
/**
 * 用户数据的业务层接口
 */
public interface IUserService {
	/**
	 * 用户注册业务
	 * @param user 用户数据
	 * @throws UserConfilctException
	 * @throws InsertException
	 */
	void reg(User user) throws UserConfilctException,InsertException;
	
	/**
	 * 用户登陆业务
	 * @param username 用户名
	 * @param password	密码
	 * @return User 用户对象，有;用户id，用户名
	 * @throws PasswordExpiredException 用户名不存在
	 * @throws UserNotFoundException	验证密码失败
	 */
	User login(String username,String password) throws PasswordNotMatchException,UserNotFoundException;
	
	
	/**
	 * 修改密码
	 * @param uid 用户id
	 * @param oldPassword 原密码
	 * @param newPassword	新密码
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws PasswordNotMatchException 原密码错误
	 * @throws UpdateException 更新数据异常
	 */
	void changePassword(Integer uid,String oldPassword,String newPassword) throws UserNotFoundException,
		PasswordNotMatchException,UpdateException;
	
	/**
	 * 修改用户信息
	 * @param user 用户信息
	 * @throws UserNotFoundException 用户不存在
	 * @throws UpdateException	更新数据异常
	 */
	void changeUserInfo(User user) throws UserNotFoundException,UpdateException;
	
	/**
	 * 查询用户信息
	 * @param uid 用户id
	 * @return 用户对象
	 * @throws UserNotFoundException 用户数据不存在
	 */
	User listUserInfo(Integer uid) throws UserNotFoundException;

	/**
	 * 修改用户头像
	 * @param uid 用户id
	 * @param avater 用户头像路径
	 * @throws UserNotFoundException 用户名未找到异常
	 * @throws UpdateException 更新异常
	 */
	void changeAvater(Integer uid,String avater) throws UserNotFoundException,UpdateException;
}
