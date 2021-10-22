package com.acho.dao;

import com.acho.bean.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author:Acho-leon
 * @Modified By:
 * @params:
 * @creat:2021-10-20-13:54
 */

@Repository
@Mapper
public interface GoodsMapper {

    public List<Goods> findAll();
}
