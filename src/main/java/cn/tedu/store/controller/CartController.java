package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.util.ResponseResult;
import cn.tedu.store.vo.CartVO;

@RestController
@RequestMapping("/carts")
public class CartController extends BaseController{
	@Autowired
	private ICartService cartService;
	
	
	@PostMapping("/add")
	public ResponseResult<Void> handleInsert(@RequestParam("gid")Long gid,
			@RequestParam("num")Integer num,HttpSession session){
		//从session中获取uid,username
		Integer uid=getUidFromSession(session);
		String username=session.getAttribute("username").toString();
		//创建新的cart对象
		Cart cart=new Cart();
		//向cart对象中封装gid，num，uid
		cart.setGid(gid);
		cart.setNum(num);
		cart.setUid(uid);
		//调用业务层对象addToCart(cart, username)方法
		cartService.addToCart(cart, username);
		//返回
		return new ResponseResult<>(SUCCESS);
	}
	
	
	//http://localhost:8080/carts/?by=cids&cids=6
	@GetMapping("/")
	public ResponseResult<List<CartVO>> handleGetListByUid(HttpSession session,String by,
			Integer[] cids){
		//从session中获取uid
		Integer uid=getUidFromSession(session);
		//调用业务层getListByUid(uid)方法
		List<CartVO> data;
		if("cids".equals(by)) {
			data=cartService.getListByCids(cids);
		}else {
			data=cartService.getListByUid(uid);
		}
		//返回
		return new ResponseResult<>(SUCCESS,data);
	}
	
	@PostMapping("/{cid}/add_num")
	public ResponseResult<Integer> handleAddNum(HttpSession session,@PathVariable("cid")Integer cid){
		//根据session获取uid,username
		Integer uid=getUidFromSession(session);
		String username=session.getAttribute("username").toString();
		//调用业务层方法addNum(cid, uid, username)
		Integer data=cartService.addNum(cid, uid, username);
		return new ResponseResult<>(SUCCESS,data);
	}
	
	@PostMapping("/{cid}/reduce_num")
	public ResponseResult<Integer> handleReduceNum(HttpSession session,@PathVariable("cid")Integer cid){
		//根据session获取uid,username
		Integer uid=getUidFromSession(session);
		String username=session.getAttribute("username").toString();
		//调用业务层方法addNum(cid, uid, username)
		Integer data=cartService.reduceNum(cid, uid, username);
		return new ResponseResult<>(SUCCESS,data);
	}
	
	@PostMapping("/{cid}/delete")
	public ResponseResult<Void> handleDeleteByCid(HttpSession session,
			@PathVariable("cid")Integer cid){
		//根据session获取uid
		Integer uid=getUidFromSession(session);
		//调用业务层方法deleteByCid(cid,uid)
		cartService.deleteByCid(cid, uid);
		return new ResponseResult<Void>(SUCCESS);
	}
	
}
