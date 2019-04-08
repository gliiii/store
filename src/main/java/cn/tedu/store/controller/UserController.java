package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 处理用户请求的控制器类
 */
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.FileContentTypeException;
import cn.tedu.store.service.ex.FileEmptyException;
import cn.tedu.store.service.ex.FileSizeException;
import cn.tedu.store.service.ex.FileUploadIOException;
import cn.tedu.store.util.ResponseResult;
@RestController
@RequestMapping("/users")
public class UserController extends BaseController{

	@Autowired
	private IUserService userService;

	/**
	 * 允许上传文件类型的集合
	 */
	public static final List<String> UPLOAD_CONTENT_TYPES=new ArrayList<String>();
	
	/**
	 * 上传文件时允许上传的最大大小
	 */
	public static final long UPLOAD_MAX_SIZE=5*1024*1024;
	
	/**
	 *存储上传的文件的文件夹名称 
	 */
	public static final String UPLOAD_DIR="upload";
	
	/**
	 * 添加允许上传的文件类型
	 */
	static {
		UPLOAD_CONTENT_TYPES.add("image/jpeg");
		UPLOAD_CONTENT_TYPES.add("image/png");
		UPLOAD_CONTENT_TYPES.add("image/gif");
		UPLOAD_CONTENT_TYPES.add("image/bmp");
	}
	
	@PostMapping("/reg")
	public ResponseResult<Void> handleReg(User user){
		userService.reg(user);
		return new ResponseResult<Void>(SUCCESS);
	}

	@PostMapping("/login")
	public ResponseResult<User> handleLogin(@RequestParam("username") String username,
			@RequestParam("password")	String password,HttpSession session){
		User user=userService.login(username, password);
		session.setAttribute("uid", user.getUid());
		session.setAttribute("username", user.getUsername());
		return new ResponseResult<>(SUCCESS,user);  
	}

	@PostMapping("change_password")
	public ResponseResult<Void> handleChangePassword(
			@RequestParam("old_password") String oldPassword,
			@RequestParam("new_password") String newPassword,
			HttpSession session){
		Integer uid=getUidFromSession(session);
		userService.changePassword(uid, oldPassword, newPassword);
		return new ResponseResult<>(SUCCESS);   
	}

	@PostMapping("change_userinfo")
	public ResponseResult<Void> handleChangeUserInfo(User user,HttpSession session){
		Integer uid=getUidFromSession(session);
		user.setUid(uid);
		userService.changeUserInfo(user);
		return new ResponseResult<>(SUCCESS);
	}
	
	@PostMapping("change_avater")
	public ResponseResult<String> handleChangeAvater(HttpServletRequest request,
		@RequestParam("avater")	MultipartFile avater){
		//判断上传的文件是否为空：avater.isEmpty()
		if(avater.isEmpty()) {
			//是：抛出异常：FileEmptyException
			throw new FileEmptyException("上传文件失败！没有选择文件，或上传文件失败");
		}
		//static:创建list集合
		//static: 向List集合中添加允许上传的文件类型
		
		//判断文件类型是否在不允许的范围内：avater.getContentType()/list.contains(contentType)
		String contentType=avater.getContentType();
		if(!UPLOAD_CONTENT_TYPES.contains(contentType)) {
			//是：抛出异常：FileContentTypeException
			throw new FileContentTypeException("上传文件失败！不支持上传"+contentType+"类型文件");
		}
		
		//判断文件大小是否超出了限制:avatar.getSize()
		long size=avater.getSize();
		if(size>UPLOAD_MAX_SIZE) {
			//是：抛出异常：FileSizeException
			throw new FileSizeException("上传文件失败！尝试上传的文件大小超出了限制！"
					+ "仅允许上传不超过的"+UPLOAD_MAX_SIZE/1024/1024+"M文件");
		}
		//标准上传流程
		//创建存储文件的文件夹
		String parentPath=request.getServletContext().getRealPath(UPLOAD_DIR);
		File parent=new File(parentPath);
		if(!parent.exists()) {
			parent.mkdirs();
		}
		//确定上传的文件名
		String originalFilename=avater.getOriginalFilename();
		String suffix="";
		int beginIndex=originalFilename.lastIndexOf(".");
		if(beginIndex!=-1) {
			suffix=originalFilename.substring(beginIndex);
		}
		String fileName=UUID.randomUUID()+suffix;
		//执行存储
		File dest=new File(parent,fileName);
		try {
			avater.transferTo(dest);
		} catch (IOException e) {
			throw new FileUploadIOException("上传文件失败！出现读写错误，请联系系统管理员或稍后尝试");
		}
		//从request中获取session再获取uid
		Integer uid=getUidFromSession(request.getSession());
		String avaterUrl="/"+UPLOAD_DIR+"/"+fileName;
		//将上传的文件路径存储到数据库：service.changeAvater(uid,avater);
		userService.changeAvater(uid, avaterUrl);
		//返回
		ResponseResult<String>rr=new ResponseResult<>();
		rr.setState(SUCCESS);
		rr.setData(avaterUrl);
		return rr; 
	}
	
	
	@GetMapping("list_userinfo")
	public ResponseResult<User> handleListUserInfo(HttpSession session){
		Integer uid=getUidFromSession(session);
		User data=userService.listUserInfo(uid);
		return new ResponseResult<>(SUCCESS,data);
	}
}
