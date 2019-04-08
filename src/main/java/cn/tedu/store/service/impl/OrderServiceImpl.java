package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.OrderNotFoundException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.vo.CartVO;
import cn.tedu.store.vo.OrderVO;
/**
 * 商品订单管理业务层实现类
 */
@Service
@Transactional
public class OrderServiceImpl implements IOrderService{
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private IAddressService addressService;
	
	@Autowired
	private ICartService cartService;
	
	@Override
	public Order createOrder(Integer uid, Integer aid, Integer[] cids, String username)
			throws InsertException,AddressNotFoundException,
									UserNotFoundException,CartNotFoundException{
		//根据cids查询对应的购物车数据：cartService.getListByCids(Integer[] cids)
		List<CartVO> cartList=cartService.getListByCids(cids);
		Long totalPrice=0L;
		//遍历并计算总价：total_price
		for (CartVO cartVO : cartList) {
			 totalPrice+=cartVO.getPrice()*cartVO.getNum();
		}
		//创建订单Order对象
		Order order=new Order();
		//向Order对象中封装uid
		order.setUid(uid);
		//根据aid查询地址数据:addressService.getByAid(aid)
		Address data=addressService.getByAid(aid);
		//判断查询结果，如果为null，抛出异常
		if(data==null) {
			throw new AddressNotFoundException("地址不存在");
		}
		//向Order对象封装receiver,phone,address
		String receiver=data.getReceiver();
		String phone=data.getPhone();
		String address=data.getDistrict()+data.getAddress();
		Date now=new Date();
		
		order.setReceiver(receiver);
		order.setPhone(phone);
		order.setAddress(address);
		//向Order对象封装state值为0
		order.setState(0);
		//向Order对象封装orderTime为now
		order.setOrderTime(now);
		//向Order对象封装total_price
		order.setTotalPrice(totalPrice);
		//向Order对象封装4项日志
		order.setCreatedUser(username);
		order.setCreatedTime(now);
		order.setModifiedUser(username);
		order.setModifiedTime(now);
		//执行插入订单数据：insertOrder(order)
		insertOrder(order);
		//根据cids查询对应的购物车数据：cartService.getListByCids(Integer[] cids)
		for (CartVO cartVO : cartList) {
			//遍历查询结果
			Long goodsId=cartVO.getGid();
			String goodsImage=cartVO.getImage();
			Integer goodsNum=cartVO.getNum();
			Long goodsPrice=cartVO.getPrice();
			String goodsTitle=cartVO.getTitle();
			
			//--创建OrderItem对象
			OrderItem orderItem=new OrderItem();
			//--向OrderItem对象中封装商品相关的五个数据
			orderItem.setGoodsId(goodsId);
			orderItem.setGoodsImage(goodsImage);
			orderItem.setGoodsNum(goodsNum);;
			orderItem.setGoodsPrice(goodsPrice);;
			orderItem.setGoodsTitle(goodsTitle);;
			//向Order对象封装4项日志
			orderItem.setCreatedUser(username);
			orderItem.setCreatedTime(now);
			orderItem.setModifiedUser(username);
			orderItem.setModifiedTime(now);
			//--向OrderItem对象中封装oid
			orderItem.setOid(order.getOid());
			//--执行插入订单商品数据：insertOrderItem(orderItem)
			insertOrderItem(orderItem);
		}
		//--TODO 将t_goods表中的库存减少
		//-- TODO 删除t_cart表中对应的数据
		
		//返回
		return order;
	}

	@Override
	public OrderVO getByOid(Integer oid,Integer uid )
			throws OrderNotFoundException,AccessDeniedException {
		//根据oid查询订单信息
		OrderVO order=findByOid(oid);
		//判断订单是否不存在
		if(order==null) {
			//是：null，不存在：抛出异常OrderNotFoundException
			throw new OrderNotFoundException("订单不存在");
		}
		//判断订单中uid是否与参数uid不一致
		if(order.getUid()!=uid) {
			//是：不一致，抛出异常:AccessDeniedException
			throw new AccessDeniedException("访问被拒绝");
		}
		//返回
		return order;
	}

	/**
	 * 添加用户订单
	 * @param order 用户订单
	 * @return 受影响行数
	 */
	private void insertOrder(Order order) {
		Integer rows=orderMapper.insertOrder(order);
		if(rows!=1) {
			throw new InsertException("创建购物订单失败！");
		}
	};
	
	/**
	 * 添加用户商品订单
	 * @param orderItem
	 * @return 受影响行数
	 */
	private void insertOrderItem(OrderItem orderItem) {
		Integer rows=orderMapper.insertOrderItem(orderItem);
		if(rows!=1) {
			throw new InsertException("创建购物订单商品失败！");
		}
	};
	
	/**
	 * 根据id查询订单详情
	 * @param oid
	 * @return
	 */
	private OrderVO findByOid(Integer oid) {
		return orderMapper.findByOid(oid);
	};
}
