package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Goods;
/**
 * 处理商品数据的持久层接口
 * @author soft01
 *
 */
public interface GoodsMapper {
	/**
	 * 获取热销商品的列表
	 * @param count	热销商品数量
	 * @return 热销商品数据
	 */
	List<Goods> findHotGoods(Integer count);
	
	/**
	 * 根据商品Id查询商品信息
	 * @param id 商品id
	 * @return 商品信息
	 */
	Goods findById(Long id);
}
