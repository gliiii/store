package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

public interface CartMapper {
	/**
	 * 添加购物车
	 * @param cart 购物车数据
	 * @return 影响行数
	 */
	Integer insert(Cart cart);
	
	/**
	 * 更新购物车商品数量
	 * @param cid 购物车商品id
	 * @param num 商品数量
	 * @param modifiedUser 修改人
	 * @param modifiedTime 修改时间
	 * @return 受影响行数
	 */
	Integer updateNum(@Param("cid")Integer cid,
			@Param("num")Integer num,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTime);
	
	/**
	 * 根据用户id商品id查询购物车商品数据
	 * @param uid 用户id
	 * @param gid 商品id
	 * @return 商品数据
	 */
	Cart findByUidAndGid(@Param("uid")Integer uid,
			@Param("gid")Long gid);
	
	/**
	 * 查询指定用户的购物车数据
	 * @param uid 用户id
	 * @return 指定用户的购物车数据
	 */
	List<CartVO> findListByUid(Integer uid);
	
	/**
	 * 根据购物车id查询购物车商品信息
	 * @param cid 购物车id
	 * @return 购物车商品信息
	 */
	Cart findByCid(Integer cid);
	
	/**
	 * 查询指定的某些id的购物车数据
	 * @param cids 多个购物车数据的id的数组
	 * @return 指定的某些id的购物车数据
	 */
	List<CartVO> findListByCids(Integer[] cids);
	
	/**
	 * 删除指定购物车商品
	 * @param cid 购物车商品id
	 * @return 受影响的行数
	 */
	Integer deleteByCid(Integer cid);
}
