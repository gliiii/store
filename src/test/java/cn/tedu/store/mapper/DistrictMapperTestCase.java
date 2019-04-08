package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictMapperTestCase {

	@Autowired
	DistrictMapper mapper;
	
	@Test
	public void findListByParent() {
		System.err.println("BEGIN");
		String parent="86";
		List<District> list=mapper.findListByParent(parent);
		for (District district : list) {
			System.err.println(district);
		}
		System.err.println("END");
	}
	
	@Test
	public void findByCode() {
		System.err.println("BEGIN");
		String code="330000";
		District data=mapper.findByCode(code);
		System.err.println(data);
		System.err.println("END");
	}
}
