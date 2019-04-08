package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTestCase {
	@Autowired
	ICartService service;
	
	@Test
	public void addToCart() {
		try {
			Cart cart=new Cart();
			cart.setCid(3);
			cart.setUid(16);
			cart.setGid(10000017L);
			cart.setNum(3);
			String username="web";
			service.addToCart(cart, username);
			System.err.println("ok");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		} 
	}
	
	@Test
	public void getListByUid() {
		Integer uid=16;
		List<CartVO> list=service.getListByUid(uid);
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
	}
	
	@Test
	public void addNum() {
		try {
			Integer cid=3;
			Integer uid=16;
			String username="web";
			Integer num=service.addNum(cid, uid, username);
			System.err.println("num"+num);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void reduceNum() {
		try {
			Integer cid=3;
			Integer uid=16;
			String username="web";
		Integer num=service.reduceNum(cid, uid, username);
		System.err.println("num:"+num);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getListByCids() {
		Integer[] cids= {3,4,5};
		List<CartVO> list=service.getListByCids(cids);
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
	}
	
	@Test
	public void deleteByCid() {
		try {
			Integer cid=7;
			Integer uid=16;
			service.deleteByCid(cid, uid);
			System.err.println("ok");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
}
