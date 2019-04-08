package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Goods;

/**
 * 处理商品数据业务层接口
 */
public interface IGoodsService {
	/**
	 * 获取热销商品的列表
	 * @param count	热销商品数量
	 * @return 热销商品数据
	 */
	List<Goods> getHotGoods(Integer count);
	
	/**
	 * 根据商品Id查询商品信息
	 * @param id 商品id
	 * @return 商品信息
	 */
	Goods getById(Long id);
	
}
