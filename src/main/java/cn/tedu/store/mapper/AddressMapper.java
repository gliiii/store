package cn.tedu.store.mapper;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Address;
/**
 * 处理用户地址的持久层接口
 */
public interface AddressMapper {
	/**
	 * 增加用户地址	
	 * @param address 用户地址
	 * @return 受影响行数
	 */
	Integer insert(Address address);
	
	/**
	 * 根据用户id查询用户地址数量
	 * @param uid 用户id
	 * @return 该用户地址数量
	 */
	Integer getCountByUid(Integer uid);
	
	/**
	 * 获取指定用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	List<Address> findListByUid(Integer uid);
	
	/**
	 * 根据地址id查询用户id
	 * @param aid 地址id
	 * @return 用户id
	 */
	Address findByAid(Integer aid);

	/**
	 * 根据用户id查询最后一条修改数据
	 * @param uid
	 * @return
	 */
	Address findLastModifiedByUid(Integer uid);

	/**
	 * 将所有默认地址设为非默认状态
	 * @param uid 用户id
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改时间
	 * @return 受影响行数
	 */
	Integer updateNonDefault(@Param("uid")Integer uid,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTime);
	
	/**
	 * 根据地址id设置为默认状态
	 * @param aid 地址id
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改时间
	 * @return 受影响行数
	 */
	Integer updateDefault(@Param("aid")Integer aid,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTime);
	
	/**
	 * 根据用户id删除用户数据
	 * @param aid 用户id
	 * @return	受影响行数
	 */
	Integer deleteByAid(Integer aid);
}
