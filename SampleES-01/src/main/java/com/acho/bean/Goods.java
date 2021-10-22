package com.acho.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goods {
    private int id;
    private String title;
    private double price;
    private int stock;
    private int saleNum;
    private Date createTime;
    private String categoryName;
    private String brandName;
    private Map spec;
   // @JSONField(serialize = false)//在转换JSON时，忽略该字段
    private String specStr;//接收数据库的信息 "{}"

}  
