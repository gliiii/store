package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.OrderVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTestCase {
	@Autowired
	IOrderService orderService;
	
	@Test
	public void createOrder() {
		try {
			Integer uid=16;
			Integer aid=12;
			Integer[] cids= {3,5};
			String username="web";
			Order order=orderService.createOrder(uid, aid, cids, username);
			System.err.println("order:"+order);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByOid() {
		try {
			Integer oid=7;
			Integer uid=16;
			OrderVO order=orderService.getByOid(oid, uid);
			System.err.println("order:"+order);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	
	}
}
