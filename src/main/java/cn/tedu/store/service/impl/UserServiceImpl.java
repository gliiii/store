package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserConfilctException;
import cn.tedu.store.service.ex.UserNotFoundException;
/**
 * 用户数据业务层实现类
 */
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserMapper userMapper;
	/**
	 * 注册业务
	 */
	@Override
	public void reg(User user) throws UserConfilctException, InsertException {
		//根据用户名查询user.getUsername()获取用户名匹配的数据
		String username=user.getUsername();
		User data=findByUsername(username);
		//检查数据是否为null
		if(data==null) {
			//是：为null，用户名未被占用，则应该补全参数中的属性值
			// TODO -1.密码加密，并封装
			String salt=UUID.randomUUID().toString().toUpperCase();
			String md5Password=getMd5Password(user.getPassword(), salt);
			user.setPassword(md5Password);
			// TODO -2.封装salt
			user.setSalt(salt);
			//-3.封装isDelete,固定值为0
			user.setIsDelete(0);
			//-4.封装4项日志数据
			Date now=new Date();
			user.setCreatedTime(now);
			user.setModifiedTime(now);
			user.setCreatedUser(username);
			user.setModifiedUser(username);
			//执行注册：addNew(user)
			addNew(user);
		}else {
			//否：非null，用户名被占用，则抛出UserConlictException
			throw new UserConfilctException("注册失败，您尝试注册的用户名("+username+")已被占用！");
		}

	}
	/**
	 * 登陆业务
	 */
	@Override
	public User login(String username, String password) throws PasswordNotMatchException, UserNotFoundException {
		//根据username查询用户数据
		User user=findByUsername(username);
		//判断用户数据是否为null
		if(user==null) {
			//是：为null，即用户数据不存在，则抛出UseNotFoundException
			throw new UserNotFoundException("（"+username+"）用户数据不存在");
		}
		//否：非null，即用户数据存在，判断是否已删除：isDelete.equals(1)
		Integer isDelete=user.getIsDelete();
		if(isDelete==1) {
			//--是：已删除，则抛出UserNotFoundException
			throw new UserNotFoundException("（"+username+"）用户数据不存在");
		}
		//--否：未删除，先取出salt
		String salt=user.getSalt();
		//--将参数password结合salt执行加密
		String md5Password=getMd5Password(password, salt);
		//--判断密码是否匹配
		if(user.getPassword().equals(md5Password)) {
			//-- --是：返回查询到的用户数据中的password和salt属性设置未null再返回
			user.setPassword(null);
			user.setSalt(null);
			return user;
		}else {
			//-- --否：则抛出PasswordNotMatchException
			throw new PasswordNotMatchException("密码错误");
		}
	}
	/**
	 * 修改密码业务
	 */
	@Override
	public void changePassword(Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordNotMatchException, UpdateException {
		//根据uid查询用户信息
		User data=findByUid(uid);
		//判断查询结果是否为null
		if(data==null) {
			//是：用户数据不存在，抛出UserNotFoundException
			throw new UserNotFoundException("修改密码失败，用户数据不存在");
		}
		Integer isDelete=data.getIsDelete();
		//判断isDelete是否为1
		if(isDelete==1) {
			//是：用户已被标记为删除，抛出UserNotFoundException
			throw new UserNotFoundException("修改密码失败，用户数据不存在");
		}
		//从查询结果中取出盐值salt
		String salt=data.getSalt();
		//将oldPassword和salt进行加密，得到oldMd5Password
		String  oldMd5Password=getMd5Password(oldPassword, salt);
		//判断oldMd5Password和查询到的密码是否匹配
		if(oldMd5Password.equals(data.getPassword())) {
			//是：原密码正确 将newPassword和salt进行加密，得到newMd5Password
			String newMd5Password=getMd5Password(newPassword, salt);
			//--创建Date对象表示modifiedTime
			Date now=new Date();
			//--执行更新密码
			updatePassword(uid, newMd5Password, data.getUsername(), now);
		}else {
			//否：原密码错误，抛出PasswordNotMatchException
			throw new PasswordNotMatchException("修改密码失败，原密码错误");
		}
	}
	/**
	 * 修改用户信息业务
	 */
	@Override
	public void changeUserInfo(User user) throws UserNotFoundException, UpdateException {
		//根据user.getUid()用户id查找对应的用户
		Integer uid=user.getUid();
		User data=findByUid(uid);
		//判断查询结构是否未null
		if(data==null) {
			//否：null,用户不存在，抛出异常UserNotFoundException
			throw new UserNotFoundException("修改用户资料失败，用户不存在");
		}
		//取出isDelete
		Integer isDelete=data.getIsDelete();
		//判断用户是否已删除
		if(isDelete.equals(1)) {
			//是：1，用户已删除，抛出异常UserNotFoundException
			throw new UserNotFoundException("修改用户资料失败，用户不存在");
		}
		//在user中封装modified_user和modified_time
		Date now=new Date();
		user.setModifiedTime(now);
		user.setModifiedUser(data.getUsername());
		//执行更新用户信息操作updateUserInfo(user)
		updateUserInfo(user);
	}

	/**
	 *根据uid查找用户数据
	 *@param uid 用户id
	 *@return 用户数据，用户名，电话，邮箱，性别 
	 */
	@Override
	public User listUserInfo(Integer uid) throws UserNotFoundException {
		//查询数据
		User data=findByUid(uid);
		//判断用户数据是否存在
		if(data==null) {
			throw new UserNotFoundException("尝试访问的用户数据不存在！");
		}
		//判断用户是否被标记删除
		if(data.getIsDelete().equals(1)) {
			throw new UserNotFoundException("尝试访问的用户数据不存在");
		}
		//清楚不希望对外暴露 的数据
		data.setPassword(null);
		data.setSalt(null);
		data.setIsDelete(null);
		return data;
	};

	@Override
	public void changeAvater(Integer uid, String avater) throws UserNotFoundException, UpdateException {
		//根据uid查询用户信息：User findByUid(Integer uid);
		User data=findByUid(uid);
		//判断查询结果是否未null
		if(data==null) {
			//是：抛出UserNotFoundException
			throw new UserNotFoundException("用户未找到");
		}
		//判断查询结果isDelete是否为1、
		Integer isDelete=data.getIsDelete();
		if(isDelete.equals(1)) {
			//是：抛出UserNotFoundException
			throw new UserNotFoundException("用户未找到");
		}
		//从查询结果中获取用户名
		String modifiedUser=data.getUsername();
		//创建当前时间对象
		Date now=new Date();
		//更新头像：updateAvater(Integer uid,String avater,String modifiedUser,Date modifiedTime){}
		updateAvatar(uid, avater, modifiedUser, now);
	}
	
	/**
	 * 插入用户数据
	 * @param user 用户数绝
	 * @return 受影响的行数
	 */
	private void addNew(User user) {
		Integer rows=userMapper.addNew(user);
		if(rows!=1) {
			throw new InsertException("数据插入异常，请练习管理员！");
		}
	};
	/**
	 * 根据用户名查询用户信息
	 * @param username 用户名
	 * @return 匹配用户的数据，如果没有匹配的数据，则返回null
	 */
	private User findByUsername(String username) {
		return userMapper.findByUsername(username);
	};

	/**
	 * 根据用户id查询用户信息
	 * @param uid 用户id
	 * @return 匹配用户的数据，如果没有匹配的数据，则返回null
	 */
	private User findByUid(Integer uid) {
		return userMapper.findByUid(uid);
	};

	/**
	 *修改密码 
	 * @param uid 用户id
	 * @param password 用户老密码
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updatePassword(Integer uid,String password,String modifiedUser,Date modifiedTime) {
		Integer rows=userMapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		if(rows!=1) {
			throw new UpdateException("密码修改异常");
		}
	};

	/**
	 * 修改用户信息
	 * @param user
	 */
	private void updateUserInfo(User user) {
		Integer rows=userMapper.updateUserInfo(user);
		if(rows!=1) {
			throw new UpdateException("用户信息修改异常");
		}
	}
	/**
	 * 执行密码加密
	 * @param password 密码原文
	 * @param salt	盐值
	 * @return	加密后的密文
	 */
	private String getMd5Password(String password,String salt) {
		//加密规则
		// 1.将盐值添加到原文的左侧
		// 2.执行加密10次
		String str=salt+password;
		for(int i=0;i<10;i++) {
			str=DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
		}
		return str;
	}

	private void updateAvatar( Integer uid,String avater,String modifiedUser,Date modifiedTime) {
		Integer rows=userMapper.updateAvatar(uid, avater, modifiedUser, modifiedTime);
		if(rows!=1) {
			throw new UpdateException("修改用户头像异常");
		}
	};





}
