package com.lww.vo;

import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.Getter;
import lombok.Setter;

/**
 * 购物项对象
 */
@Getter
@Setter
public class CartItem {
	private Product product;	// 购物项中商品信息
	private int count;			// 购买某种商品数量
	private double subtotal;	// 购买某种商品小计
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	// 小计自动计算的.
	public double getSubtotal() {
		return count * product.getShop_price();
	}

}
