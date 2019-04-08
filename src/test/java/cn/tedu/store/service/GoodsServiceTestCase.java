package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Goods;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsServiceTestCase {
	@Autowired
	IGoodsService service;
	
	@Test
	public void getHotGoods() {
		Integer count=3;
		List<Goods> goods=service.getHotGoods(count);
		for (Goods goods2 : goods) {
			System.err.println(goods2);
		}
	}
	
	@Test
	public void getById() {
		Long id=10000001L;
		Goods good=service.getById(id);
		System.err.println("good:"+good);
	}
}
