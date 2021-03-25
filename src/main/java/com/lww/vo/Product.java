package com.lww.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * 商品的实体对象
 */
@Getter
@Setter
public class Product {
	private Integer pid;
	private Integer csid;
	private String pname;
	private Double market_price;
	private Double shop_price;
	private String image;
	private String pdesc;
	private Integer is_hot;
	private Date pdate;
}
