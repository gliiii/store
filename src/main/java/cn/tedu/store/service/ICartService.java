package cn.tedu.store.service;

import java.util.List;


import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;
/**
 * 处理购物车数据的业务层接口
 */
public interface ICartService {
	/**
 * 将商品添加到购物车
	 * @param cart 购物车数据，至少需要包括用户id，商品id，商品数量
	 * @param username 当前登陆的用户的用户名
	 * @throws InsertException 添加异常
	 * @throws UpdateException 更新异常 
	 */
	void addToCart(Cart cart,String username)throws InsertException,UpdateException;
	
	/**
	 * 查询指定用户的购物车数据
	 * @param uid 用户id
	 * @return 指定用户的购物车数据
	 */
	List<CartVO> getListByUid(Integer uid);
	
/**
 * 购物车数据中购物数量+1
 * @param cid
 * @param uid
 * @param username
 * @return
 * @throws UpdateException
 * @throws AccessDeniedException
 * @throws CartNotFoundException
 */
	Integer addNum(Integer cid,Integer uid,String username)
			throws UpdateException,AccessDeniedException,CartNotFoundException;
	
	
	/**
	 *  购物车数据中购物数量-1
	 * @param cid
	 * @param uid
	 * @param username
	 * @return
	 * @throws UpdateException
	 * @throws AccessDeniedException
	 * @throws CartNotFoundException
	 */
	Integer reduceNum(Integer cid,Integer uid,String username)
			throws UpdateException,AccessDeniedException,CartNotFoundException;
	
	/**
	 * 查询指定的某些id的购物车数据
	 * @param cids 多个购物车数据的id的数组
	 * @return 指定的某些id的购物车数据
	 */
	List<CartVO> getListByCids(Integer[] cids);
	
	/**
	 * 删除指定购物车商品
	 * @param cid 购物车商品id
	 * @return 受影响的行数
	 */
	void deleteByCid(Integer cid,Integer uid);
}
