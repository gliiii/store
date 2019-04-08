package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Goods;
import cn.tedu.store.mapper.GoodsMapper;
import cn.tedu.store.service.IGoodsService;

@Service
public class GoodsServiceImpl implements IGoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	
	@Override
	public List<Goods> getHotGoods(Integer count) {
		return findHotGoods(count);
	}

	@Override
	public Goods getById(Long id) {
		return findById(id);
	}

	/**
	 * 获取热销商品的列表
	 * @param count	热销商品数量
	 * @return 热销商品数据
	 */
	private List<Goods> findHotGoods(Integer count){
		return goodsMapper.findHotGoods(count);
	};

	/**
	 * 根据商品Id查询商品信息
	 * @param id 商品id
	 * @return 商品信息
	 */
	private Goods findById(Long id) {
		return goodsMapper.findById(id);
	};
}
