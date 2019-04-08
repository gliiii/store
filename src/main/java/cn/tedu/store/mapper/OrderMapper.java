package cn.tedu.store.mapper;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
/**
 * 商品订单的持久层接口
 */
import cn.tedu.store.vo.OrderVO;
public interface OrderMapper {
	/**
	 * 添加用户订单
	 * @param order 用户订单
	 * @return 受影响行数
	 */
	Integer insertOrder(Order order);
	
	/**
	 * 添加用户商品订单
	 * @param orderItem
	 * @return 受影响行数
	 */
	Integer insertOrderItem(OrderItem orderItem);
	
	/**
	 * 根据id查询订单详情
	 * @param oid
	 * @return
	 */
	OrderVO findByOid(Integer oid);
}
