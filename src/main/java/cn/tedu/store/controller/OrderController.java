package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.util.ResponseResult;
import cn.tedu.store.vo.OrderVO;

@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController{
	@Autowired
	private IOrderService orderService;
	
	@PostMapping("/create")
	public ResponseResult<Order> handleCreateOrder(Integer aid,Integer[] cids,
			HttpSession session){
		//从session中获取用户id
		Integer uid=getUidFromSession(session);
		String username=session.getAttribute("username").toString();
		//执行
		Order order=orderService.createOrder(uid, aid, cids, username);
		//返回
		return new ResponseResult<>(SUCCESS,order);
	}
	
	@PostMapping("/{oid}")
	public ResponseResult<OrderVO> handleGetByOid(HttpSession session,
			@PathVariable("oid") Integer oid){
		//从session中获取用户id
		Integer uid=getUidFromSession(session);
		OrderVO order=orderService.getByOid(oid, uid);
		return new ResponseResult<>(SUCCESS,order);
	}
}
