package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Goods;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsMapperTestCase {

		@Autowired
		GoodsMapper mapper;
		
		@Test
		public void findHotGoods() {
			Integer count=4;
			List<Goods> list=mapper.findHotGoods(count);
			for (Goods goods : list) {
				System.err.println(goods);
			}
		}
		
		@Test
		public void findById() {
			long id=10000001L;
			Goods good=mapper.findById(id);
			System.err.println("good:"+good);
		}
}
