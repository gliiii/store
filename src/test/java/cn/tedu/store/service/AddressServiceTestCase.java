package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTestCase {

		@Autowired
		private IAddressService service;
		
		@Test
		public void addNew() {
			try {
				Address address=new Address();
				address.setUid(1);
				String username="root";
				service.addNew(address, username);
				System.err.println("ok");
			} catch (ServiceException e) {
				System.err.println(e.getMessage());
			}
		}
		
		@Test
		public void getListByUid() {
			Integer uid=16;
			List<Address> addresses=service.getListByUid(uid);
			for (Address address : addresses) {
				System.err.println(address);
			}
		}
		
		@Test
		public void setDefault() {
			try {
				Integer aid=3;
				Integer uid=16;
				String username="web";
				service.setDefault(aid, uid, username);
				System.err.println("ok");
			} catch (ServiceException e) {
				System.err.println(e.getMessage());
			}
		}
		
		@Test
		public void deleteByAid() {
			try {
				Integer uid=16;
				Integer aid=3;
				String username="web";
				service.deleteByAid(uid, aid, username);
				System.out.println("ok");
			} catch (ServiceException e) {
				System.err.println(e.getMessage());
			}
		}
	
}
