package cn.tedu.store.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

/**
 * 用户地址的业务层接口
 */
public interface IAddressService {
	/**
	 * 增加用户地址
	 * @param address 用户地址
	 * @param username 用户名
	 * @throws InsertException 插入数据异常
	 */
	void addNew(Address address,String username)throws InsertException;
	
	
	/**
	 * 获取指定用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	List<Address> getListByUid(Integer uid);
	
	/**
	 * 根据地址id将该地址设为默认地址
	 * @param aid 地址id
	 * @throws UpdateException 更新地址异常
	 */
	void setDefault(Integer aid,Integer uid,String username)throws UpdateException,AddressNotFoundException,AccessDeniedException;
	
	/**
	 * 删除指定的收货地址数据
	 * @param uid 当前登陆的用户的id
	 * @param aid	被删除的收货地址数据的id
	 * @param username	当前登陆的用户名
	 * @throws DeleteException 删除异常
	 * @throws AddressNotFoundException 地址未找到异常
	 * @throws AccessDeniedException	
	 */
	void deleteByAid(Integer uid,Integer aid,String username)throws DeleteException,AddressNotFoundException,AccessDeniedException;
	
	/**
	 * 根据aid查询地址数据
	 * @param aid
	 * @return
	 */
	Address getByAid(Integer aid);
}
