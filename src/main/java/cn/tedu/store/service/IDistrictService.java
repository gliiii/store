package cn.tedu.store.service;
/**
 * 省市区业务层接口
 */

import java.util.List;

import cn.tedu.store.entity.District;

public interface IDistrictService {
		/**
		 * 根据父级代号获取所有省/某省的所有市/某市的所有区的列表
		 * @param parent	父级代码
		 * @return 省市区数据列表
		 */
		List<District> getListByParent(String parent);
		
		/**
		 * 根据省/市/区的代号获取详情
		 * @param code 省市区代号
		 * @return 省市区名称
		 */
		District getByCode(String code);
}
