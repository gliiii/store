package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.District;
/**
 * 处理省市区的持久层接口
 */
public interface DistrictMapper {
	/**
	 * 根据父级代号获取所有省/某省的所有市/某市的所有区
	 * @param parent 省的代号
	 * @return 数据集合
	 */
	List<District> findListByParent(String parent);
	
	/**
	 * 根据省/市/区的代号查寻数据
	 * @param code 省/市/区的代号
	 * @return 省/市/区名
	 */
	District findByCode(String code);
}
