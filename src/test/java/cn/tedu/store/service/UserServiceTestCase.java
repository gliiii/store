package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTestCase {

	@Autowired
	IUserService service;

	@Test
	public void regService() {
		try {
			User user=new User();
			user.setUsername("digest");
			user.setPassword("8888");
			user.setPhone("15527889316");
			user.setEmail("admin@tedu.cn");
			user.setGender(1);
			user.setAvater("http://www.tedu.cn");
			service.reg(user);
			System.err.println("Ok.");
		}catch(ServiceException e){
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void loginService() {
		try {
			String username="digest";
			String password="8888";
			User user=service.login(username, password);
			System.err.println(user);
		}catch(ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void updatePasswordService() {
		try {
			
			Integer uid=23;
			String oldPassword="1234";
			String newPassword="8888";
			service.changePassword(uid, oldPassword, newPassword);
			System.err.println("ok");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void updateUserInfo() {
		try {
			User user=new User();
			user.setUid(50);
			user.setUsername("zhangsan");
			user.setGender(1);
			user.setPhone("15872520020");
			user.setEmail("zhangshan@tedu.cn");
			service.changeUserInfo(user);
			System.err.println("ok");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void listUserInfo() {
		try {
			Integer uid=12;
			User user=service.listUserInfo(uid);
			System.err.println(user);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changeAvater() {
		try {
			String avater="www://doc.tedu.cn";
			Integer uid=6;
			service.changeAvater(uid, avater);
			System.err.println("ok");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
}
