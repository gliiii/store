package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

/**
 * 用户地址业务层实现类
 */
@Service
public class AddressServiceImpl implements IAddressService{

	@Autowired
	private AddressMapper addressMapper;
	
	@Autowired
	private IDistrictService districtService;
	
	@Override
	public void addNew(Address address,String username) throws InsertException {
		//TODO 确定district的值
		String district=getDistrictByCodes(address.getProvince(),address.getCity(),address.getArea());
		address.setDistrict(district);
		//获取当前用户的收获地址：getCountByUid(address.getUid());
		Integer count=getCountByUid(address.getUid());
		//如果数量为0，则isDefault为1，否则，isDefault为0
		address.setIsDefault(count==0?1:0);
		//四项日志
		Date now=new Date();
		address.setCreatedUser(username);
		address.setCreatedTime(now);
		address.setModifiedUser(username);
		address.setModifiedTime(now);
		//执行插入数据：insert(address);
		insert(address);
	}
	
	@Override
	public List<Address> getListByUid(Integer uid) {
		return findListByUid(uid);
	}

	@Override
	public Address getByAid(Integer aid) {
		return findByAid(aid);
	}

	@Override
	@Transactional
	public void setDefault(Integer aid,Integer uid,String username) 
			throws UpdateException,AddressNotFoundException,AccessDeniedException {
		//根据aid查询数据：findByAid(aid)
		Address address=findByAid(aid);
		//判断查询结果是否为null
		if(address==null) {
			//是：抛出异常：AddressNotFoundException
			throw new AddressNotFoundException("地址未找到！");
		}
		//判断将查找到的uid与session中的uid进行对比是否不同
		if(uid!=address.getUid()) {
			//是，匹配不一致，抛出异常：AccessDeniedException 
			throw new AccessDeniedException("用户ID匹配不一致！");
		}
		//创建当前时间对象
		Date modifiedTime=new Date();
		//根据uid将用户所有地址设置为非默认状态:updateNonDefault(uid,modifiedUser, modifiedTime)
		updateNonDefault(uid, username, modifiedTime);;
		//根据aid将指定地址设置为默认状态:updataDefault(aid,modifiedUser,modifiedTime)
		updateDefault(aid, username, modifiedTime);
	}

	@Override
	@Transactional
	public void deleteByAid(Integer uid, Integer aid,String username)
			throws DeleteException, AddressNotFoundException, AccessDeniedException {
		//根据aid查询即将删除的数据
		Address data=findByAid(aid);
		//判断查询结果是否未null
		if(data==null) {
			//是：抛出异常：AddressNotFoundException
			throw new AddressNotFoundException("地址不存在");
		}
		//判断查询结果的uid是否与参数uid不相同
		if(data.getUid()!=uid) {
			//是：抛出异常：AccessDeniedException 
			throw new AccessDeniedException("删除收货地址失败，访问权限不足");
		}
		//执行删除
		deleteByAid(aid);
		//判断刚刚删除的数据（最开始找出来的数据）isDefault是否未1
		if(data.getIsDefault()==1) {
			//--查询当前用户还有多少条收货地址
			Integer count=getCountByUid(uid);
			//--判断数量是否>0
			if(count>0) {
				//--是：找出最后一条收货地址的aid
				Address address=findLastModifiedByUid(uid);
				//-- --把找出来的数据设置为默认
				updateDefault(address.getAid(), username, new Date());
			}
		};
		
		
	}

	/**
	 * 增加用户地址
	 * @param address 用户地址
	 */
	private void insert(Address address) {
		Integer rows=addressMapper.insert(address);
		if(rows!=1) {
			throw new InsertException("增加用户地址失败");
		}
	}
	/**
	 * 根据用户id查询用户地址数量
	 * @param uid 用户id
	 * @return 该用户地址数量
	 */
	private Integer getCountByUid(Integer uid) {
		return addressMapper.getCountByUid(uid);
	}

	/**
	 * 根据省市区的代号获取地区的名称
	 * @param province 省的代号
	 * @param city 市的代号
	 * @param area 区的代号
	 * @return 详细地址
	 */
	private String getDistrictByCodes(String province, String city, String area) {
		String provinceName="NULL";
		String cityName="NULL";
		String areaName="NULL";
		
		District p=districtService.getByCode(province);
		if(p!=null) {
			provinceName=p.getName();
		}
		District c=districtService.getByCode(city);
		if(c!=null) {
			cityName=c.getName();
		}
		District a=districtService.getByCode(area);
		if(a!=null) {
			areaName=a.getName();
		}
		return provinceName+cityName+areaName;
	}
	
	/**
	 * 获取指定用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	private List<Address> findListByUid(Integer uid){
		return  addressMapper.findListByUid(uid);
	};
	
	/**
	 * 根据地址id查询用户id
	 * @param aid 地址id
	 * @return 用户id
	 */
	private Address findByAid(Integer aid) {
		return addressMapper.findByAid(aid);
	};
	
	/**
	 * 根据用户id查询最后一条修改数据
	 * @param uid
	 * @return
	 */
	private Address findLastModifiedByUid(Integer uid) {
		return addressMapper.findLastModifiedByUid(uid);
	}

	/**
	 * 将所有默认地址设为非默认状态
	 * @param uid 用户id
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改时间
	 * @return 受影响行数
	 */
	private void updateNonDefault(Integer uid,String modifiedUser,Date modifiedTime) {
		Integer rows=addressMapper.updateNonDefault(uid, modifiedUser, modifiedTime);
		if(rows<1) {
			throw new UpdateException("设置非默认地址出错！");
		}
	};
	
	/**
	 * 根据地址id设置为默认状态
	 * @param aid 地址id
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改时间
	 * @return 受影响行数
	 */
	private void updateDefault(Integer aid,String modifiedUser,Date modifiedTime) {
		Integer rows=addressMapper.updateDefault(aid, modifiedUser, modifiedTime);
		if(rows!=1) {
			throw new UpdateException("设置默认地址出错！");
		}
	};
	
	/**
	 * 根据用户id删除用户数据
	 * @param aid 用户id
	 * @return	受影响行数
	 */
	private void deleteByAid(Integer aid) {
		Integer rows=addressMapper.deleteByAid(aid);
		if(rows!=1) {
			throw new DeleteException("删除数据时出现位置错误");
		}
	};
}
