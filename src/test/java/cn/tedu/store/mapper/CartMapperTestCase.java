package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTestCase {

	@Autowired
	private CartMapper cartMapper;
	
	@Test
	public void insert() {
		Cart cart=new Cart();
		cart.setUid(16);
		cart.setGid(10000017L);
		cart.setNum(1);
		Integer rows=cartMapper.insert(cart);
		System.err.println("rows:"+rows);
	}
	
	@Test
	public void updateNum() {
		Integer cid=3;
		Integer num=2;
		String modifiedUser="web";
		Date modifiedTime=new Date();
		Integer rows=cartMapper.updateNum(cid, num, modifiedUser, modifiedTime);
		System.err.println("rows:"+rows);
	}
	
	@Test
	public void findByUidAndGid() {
		Integer uid=16;
		Long gid=10000017L;
		Cart cart=cartMapper.findByUidAndGid(uid, gid);
		System.err.println("cart:"+cart);
	}
	
	@Test
	public void findListByUid() {
		Integer uid=16;
		List<CartVO> list=cartMapper.findListByUid(uid);
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
	}
	
	@Test
	public void findByCid() {
		Integer cid=3;
		Cart good=cartMapper.findByCid(cid);
		System.err.println(good);
	}
	
	@Test
	public void findByCids() {
		Integer[] cids= {3,4,5};
		List<CartVO> list=cartMapper.findListByCids(cids);
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
	}
	@Test
	public void deleteByCid() {
		Integer cid=4;
		Integer rows=cartMapper.deleteByCid(cid);
		System.err.println("rows:"+rows);
	}
}
