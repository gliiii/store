package cn.tedu.store.mapper;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTestCase {
	@Autowired
	private UserMapper mapper;
	
	@Test
	public void addNew() {
		User user=new User();
		user.setUsername("root");
		user.setPassword("1234");
		Integer rows=mapper.addNew(user);
		System.err.println("rows:"+rows);
	}
	
	@Test
	public void findByUsername() {
		String username="digest";
		User user=mapper.findByUsername(username);
		System.err.println(user);
	}
	
	@Test
	public void updatePassword() {
		Integer uid=6;
		String password="1234";
		String modifiedUser="Admin";
		Date modifiedTime=new Date();
		Integer rows=mapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		System.err.println("rows:"+rows);
	}
	
	@Test
	public void findByUid() {
		Integer uid=6;
		User user=mapper.findByUid(uid);
		System.err.println(user);
	}
	
	@Test
	public void updateUserInfo() {
		User user=new User();
		user.setUid(1);
		user.setPhone("15872520020");
		user.setEmail("822509417@qq.com");
		user.setGender(1);
		user.setModifiedUser("admin");
		user.setModifiedTime(new Date());
		Integer rows=mapper.updateUserInfo(user);
		System.err.println("rows:"+rows);
	}
	
	@Test
	public void updateAvater() {
		Integer uid=1;
		String avater="www.tedu.cn";
		String modifiedUser="admin";
		Date modifiedTime=new Date();
		Integer rows= mapper.updateAvatar(uid, avater, modifiedUser, modifiedTime);
		System.out.println("rows:"+rows);
	}
}
