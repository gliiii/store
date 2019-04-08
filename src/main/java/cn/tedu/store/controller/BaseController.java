package cn.tedu.store.controller;
/**
 * 控制器类的基类
 * @author soft01
 *
 */

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.FileContentTypeException;
import cn.tedu.store.service.ex.FileEmptyException;
import cn.tedu.store.service.ex.FileSizeException;
import cn.tedu.store.service.ex.FileUploadException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.OrderNotFoundException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserConfilctException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.util.ResponseResult;

public class BaseController {
	/**
	 * 根据uid获取session对象
	 * @param session
	 * @return 当前登陆的用户id
	 */
	protected Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(session.getAttribute("uid").toString());
	}
	
	/**
	 * 处理请求时，用于表示操作正确的代码
	 */
	public static final int SUCCESS=200;
	
	@ExceptionHandler({ServiceException.class,FileUploadException.class})
	public ResponseResult<Void> handleException(Exception e){
		//声明返回对象
		ResponseResult<Void> rr=new ResponseResult<>();
		rr.setMessage(e.getMessage());
		//根据异常的不同，返回的错误代码也不同
		if(e instanceof UserConfilctException ) {
			//400-用户名冲突异常
			rr.setState(400);
		}else if(e instanceof InsertException) {
			//500-数据插入异常
			rr.setState(500);
		}else if(e instanceof UserNotFoundException) {
			//401-用户数据不存在异常
			rr.setState(401);
		}else if(e instanceof PasswordNotMatchException) {
			//402-密码不匹配异常
			rr.setState(402);
		}else if(e instanceof UpdateException) {
			//501-更新数据异常
			rr.setState(501);
		}else if(e instanceof FileEmptyException) {
			//601-上传文件为空异常
			rr.setState(601);
		}else if(e instanceof FileSizeException) {
			//602-上传文件大小超出限制异常
			rr.setState(602);
		}else if(e instanceof FileContentTypeException) {
			//603-上传文件类型错误异常
			rr.setState(603);
		}else if(e instanceof FileUploadException) {
			//603-上传文件时读写异常
			rr.setState(604);
		}else if(e instanceof AddressNotFoundException) {
			//403-用户的收货地质数据不存在的异常
			rr.setState(403);
		}else if(e instanceof AccessDeniedException) {
			//404-拒绝访问的异常
			rr.setState(404);
		}else if(e instanceof DeleteException) {
			//404-拒绝访问的异常
			rr.setState(502);
		}else if(e instanceof CartNotFoundException) {
			//405-购物车数据不存在异常
			rr.setState(405);
		}else if(e instanceof OrderNotFoundException) {
			//406-订单未找到异常
			rr.setState(406);
		}
		//返回
		return rr;
	}
}
