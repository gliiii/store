package cn.tedu.store.service.impl;
/**
 * 处理购物车数据的业务层接口
 */
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;
@Service
public class CartServiceImpl implements ICartService{
	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public void addToCart(Cart cart, String username)throws InsertException,UpdateException {
		//创建Date对象
		Date now=new Date();
		// 根据参数Cart中的uid和gid执行查询：Cart findByUidAndGid
		Integer uid=cart.getUid();
		Long gid=cart.getGid();
		Cart data=findByUidAndGid(uid, gid);
		//判断查询结果是否为null
		if(data==null) {
			//是：表示该用户尚未添加该商品到购物车，则向参数对象cart中封装4个日志属性
			cart.setCreatedUser(username);
			cart.setCreatedTime(now);
			cart.setModifiedUser(username);
			cart.setModifiedTime(now);
			//--则执行插入数据：void insert(Cart cart);
			insert(cart);
		}else {
			//否：表示该用户已经添加该商品到购物车，取出cid
			Integer cid=data.getCid();
			//--num值为参数cart中的num加上前序查询结果中的num
			Integer newNum=cart.getNum();
			Integer oldNum=data.getNum();
			Integer num=newNum+oldNum;
			//--则执行修改数量：void updateNum
			//(Integer cid,Integer num,String modifiedUser,Date modifiedTime)
			updateNum(cid, num, username, now);
		}
	}
	@Override
	public List<CartVO> getListByUid(Integer uid) {
		return findListByUid(uid);
	}
	@Override
	public Integer addNum(Integer cid, Integer uid, String username)
			throws UpdateException, AccessDeniedException, CartNotFoundException {
		//根据cid查询数据
		Cart good=findByCid(cid);
		//判断查询结果是否为null
		if(good==null) {
			//是：抛出异常：CartNotFoundException 
			throw new CartNotFoundException("购物车商品不存在！");
		}
		//判断查询结果中的uid与参数uid是否不同
		if(good.getUid()!=uid) {
			//是：抛出异常：AccessDeniedException
			throw new AccessDeniedException("访问被拒绝！");
		}
		//从查询结果中取出当前的num
		Integer num=good.getNum();
		//将num自增
		num++;
		//创建当前时间对象
		Date now =new Date();
		//更新updateNum(cid, num, modifiedUser, modifiedTime);
		updateNum(cid, num, username, now);
		//返回num
		return num;
	}
	
	@Override
	public Integer reduceNum(Integer cid, Integer uid, String username)
			throws UpdateException, AccessDeniedException, CartNotFoundException {
		//根据cid查询数据
		Cart good=findByCid(cid);
		//判断查询结果是否为null
		if(good==null) {
			//是：抛出异常：CartNotFoundException 
			throw new CartNotFoundException("购物车商品不存在！");
		}
		//判断查询结果中的uid与参数uid是否不同
		if(good.getUid()!=uid) {
			//是：抛出异常：AccessDeniedException
			throw new AccessDeniedException("访问被拒绝！");
		}
		//从查询结果中取出当前的num
		Integer num=good.getNum();
		//将num自减
		num--;
		//限制num最小值
		if(num<=0) {
			num=1;
		}
		//创建当前时间对象
		Date now=new Date();
		//更新updateNum(cid, num, modifiedUser, modifiedTime);
		updateNum(cid, num, username, now);
		//返回num
		return num;
	}
	@Override
	public List<CartVO> getListByCids(Integer[] cids) {
		//应该检查参数是否有效：非null，数组至少包含一个元素
		//应该检查数据归属
		return findListByCids(cids);
	}
	
	@Override
	public void deleteByCid(Integer cid, Integer uid) {
		//根据cid查询数据
		Cart good=findByCid(cid);
		//判断商品数据是否不存在
		if(good==null) {
			//是：null，抛出异常CartNotFoundException
			throw new CartNotFoundException("购物车商品不存在！");
		}
		//判断购物车商品uid是否与参数uid不相等
		if(good.getUid()!=uid) {
			//是：不相等，抛出异常AccessDeniedException
			throw new AccessDeniedException("用户访问被拒绝！");
		}
		//执行删除deleteByCid(cid)
		deleteByCid(cid);
	}
	/**
	 * 添加购物车
	 * @param cart 购物车数据
	 */
	private void insert(Cart cart) {
		Integer rows=cartMapper.insert(cart);
		if(rows!=1) {
			throw new InsertException("创建购物车数据时出现未知错误！");
		}
	};
	/**
	 * 更新购物车商品数量
	 * @param cid 购物车商品id
	 * @param num 商品数量
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改时间
	 */
	private void updateNum(Integer cid,Integer num,String modifiedUser,Date modifiedTime) {;
	Integer rows=cartMapper.updateNum(cid, num, modifiedUser, modifiedTime);
	if(rows!=1) {
		throw new UpdateException("更新购物车数据时出现未知错误！");
		}
	}

	/**
	 * 根据用户id商品id查询购物车商品数据
	 * @param uid 用户id
	 * @param gid 商品id
	 * @return 商品数据
	 */
	private Cart findByUidAndGid(Integer uid,Long gid) {
		return cartMapper.findByUidAndGid(uid, gid);
	};

	/**
	 * 查询指定用户的购物车数据
	 * @param uid 用户id
	 * @return 指定用户的购物车数据
	 */
	private List<CartVO> findListByUid(Integer uid){
		return cartMapper.findListByUid(uid);
	};
	
	/**
	 * 根据购物车id查询购物车商品信息
	 * @param cid 购物车id
	 * @return 购物车商品信息
	 */
	private Cart findByCid(Integer cid) {
		return cartMapper.findByCid(cid);
	};
	
	/**
	 * 查询指定的某些id的购物车数据
	 * @param cids 多个购物车数据的id的数组
	 * @return 指定的某些id的购物车数据
	 */
	private List<CartVO> findListByCids(Integer[] cids){
		return cartMapper.findListByCids(cids);
	};
	
	/**
	 * 删除指定购物车商品
	 * @param cid 购物车商品id
	 * @return 受影响的行数
	 */
	private void deleteByCid(Integer cid) {
		Integer rows=cartMapper.deleteByCid(cid);
		if(rows!=1) {
			throw new DeleteException("删除购物车商品失败！");
		}
	};
}
