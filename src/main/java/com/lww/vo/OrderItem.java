package com.lww.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 订单项的实体
 *
 */
@ToString
@Getter
@Setter
public class OrderItem {
	private Integer itemid;
	private Integer count;
	private Double subtotal;
	private Integer oid;
	private Integer pid;
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
