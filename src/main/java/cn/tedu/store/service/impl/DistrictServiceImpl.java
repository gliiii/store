package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.DistrictMapper;
import cn.tedu.store.service.IDistrictService;

@Service
public class DistrictServiceImpl implements IDistrictService{
	
	@Autowired
	private DistrictMapper mapper;
	
	@Override
	public List<District> getListByParent(String parent) {
		return findListByParent(parent);
	}

	@Override
	public District getByCode(String code) {
		return findByCode(code);
	}
	/**
	 * 根据父级代号获取所有省/某省的所有市/某市的所有区
	 * @param parent 省的代号
	 * @return 数据集合
	 */
	private List<District> findListByParent(String parent){
		return  mapper.findListByParent(parent);
	}
	/**
	 * 根据省/市/区的代号查寻数据
	 * @param code 省/市/区的代号
	 * @return 省/市/区名
	 */
	private District findByCode(String code) {
		return mapper.findByCode(code);
	};
}
