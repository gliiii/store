package cn.tedu.store.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.vo.OrderVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTestCase {
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Test
	public void insertOrder() {
		Order order=new Order();
		order.setUid(19);
		Integer rows=orderMapper.insertOrder(order);
		System.err.println("rows:"+rows);
		System.err.println(order.getOid());
	}
	
	@Test
	public void insertOrderItem() {
		OrderItem orderItem=new OrderItem();
		orderItem.setOid(1);
		Integer rows=orderMapper.insertOrderItem(orderItem);
		System.err.println("rows:"+rows);
	}
	
	@Test
	public void findByOid() {
		Integer oid = 7;
		OrderVO data = orderMapper.findByOid(oid);
		System.err.println(data);
	}
}
