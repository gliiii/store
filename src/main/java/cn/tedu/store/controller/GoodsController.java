package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Goods;
import cn.tedu.store.service.IGoodsService;
import cn.tedu.store.util.ResponseResult;

/**
 * 处理商品数据的控制器类
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController{

	@Autowired
	private IGoodsService goodsService;
	
	@GetMapping("hot")
	public ResponseResult<List<Goods>> handleGetHotGoods(){
		Integer count=4;
		List<Goods> data=goodsService.getHotGoods(count);
		return new ResponseResult<>(SUCCESS,data); 
	}
	
	@GetMapping("{id}/details") 
	public ResponseResult<Goods> handleGetById(@PathVariable("id")Long id){
		Goods data=goodsService.getById(id);
		return new ResponseResult<>(SUCCESS,data);
	}
}
