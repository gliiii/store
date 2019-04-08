package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.util.ResponseResult;

@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseController{
		
		@Autowired
		IAddressService addressService;
	
		@PostMapping("/addnew")
		public ResponseResult<Void> addNew(Address address,HttpSession session){
			//从session中获取username
			String username=session.getAttribute("username").toString();
			//从session中获取uid,并绑定到address中
			Integer uid=getUidFromSession(session);
			address.setUid(uid);
			//直接调用业务层对象的addnew(address,username);
			addressService.addNew(address, username);
			//返回
			return new ResponseResult<>(SUCCESS);
		}
		
		@PostMapping("{aid}/set_default")
		public ResponseResult<Void> setDefault(
				@PathVariable("aid") Integer aid,HttpSession session){
			//从session中获取uid
			Integer uid=getUidFromSession(session);
			//从session中获取用户名
			String username=session.getAttribute("username").toString();
			//执行
			addressService.setDefault(aid, uid, username);
			//返回
			return new ResponseResult<>(SUCCESS);
		}
		
		@PostMapping("{aid}/delete")
		public ResponseResult<Void> deleteByAid(
				@PathVariable("aid") Integer aid,HttpSession session){
			//从session中获取uid
			Integer uid=getUidFromSession(session);
			//从session中获取用户名
			String username=session.getAttribute("username").toString();
			//执行
			addressService.deleteByAid(uid, aid, username);;
			//返回
			return new ResponseResult<>(SUCCESS);
		}
		
		
		@GetMapping("/")
		public ResponseResult<List<Address>> getListByUid(HttpSession session){
			//从session中获取uid
			Integer uid=getUidFromSession(session);
			//通过业务层对象调用getListByUid方法
			List<Address> data=addressService.getListByUid(uid);
			//返回
			return new ResponseResult<>(SUCCESS,data);
		}
		
		
}
