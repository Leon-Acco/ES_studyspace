package com.acho.es;

import com.acho.bean.Goods;
import com.acho.dao.GoodsMapper;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:Acho-leon
 * @Modified By:
 * @params:
 * @creat:2021-10-20-14:04
 */

@SpringBootTest
public class ImportDataToEs {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RestHighLevelClient client;


    @Test
    void ImportData() throws IOException {
        List<Goods> Medata = goodsMapper.findAll();

        BulkRequest bulkRequest = new BulkRequest();

        for (Goods goods : Medata) {
            //2.2 设置spec规格信息 Map的数据   specStr:{}
            //goods.setSpec(JSON.parseObject(goods.getSpecStr(),Map.class));

            String specStr = goods.getSpecStr();
            //将json格式字符串转为Map集合
            Map map = JSON.parseObject(specStr, Map.class);
            //设置spec map
            goods.setSpec(map);
            //将goods对象转换为json字符串
            String data = JSON.toJSONString(goods);//map --> {}
            IndexRequest indexRequest = new IndexRequest("goods");
            indexRequest.id(goods.getId()+"").source(data, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }


            client.bulk(bulkRequest, RequestOptions.DEFAULT);



    }

    @Test
    void  ConditionSearch() throws IOException {
        //2,参数
        SearchRequest searchRequest = new SearchRequest("goods");
        //4,创建查询条件构建器SearchSourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //5,指定查询条件




        //5.1 MachAll
        //searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        //5.2 term
        //searchSourceBuilder.query(QueryBuilders.termQuery("brandName","阿尔卡特"));

        //5.3 terms
        //searchSourceBuilder.query(QueryBuilders.termsQuery("brandName","阿尔卡特","三星"));

        //5.4 range
        //searchSourceBuilder.query(QueryBuilders.rangeQuery("price").gte(299).lte(10000));

        //5.5machQuery
        //searchSourceBuilder.query(QueryBuilders.matchQuery("title","华为手机"));

        //5.6 wildcard
        //searchSourceBuilder.query(QueryBuilders.wildcardQuery("title","*为"));

        //5.7 QueryString
        searchSourceBuilder.query(QueryBuilders.queryStringQuery("华为手机").field("title").field("brandName").field("categoryName") .defaultOperator(Operator.AND));

        //3,查询条件构健器
        searchRequest.source(searchSourceBuilder);
        /**
         * 跟query同级的有，聚合，高亮
         */
        //添加分页的条件
       searchSourceBuilder.from(0).size(10);


        //1,查询,获取查询结果(searchRequest)
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


        //显示获取的信息数据


        SearchHits hits = searchResponse.getHits();
        long value = hits.getTotalHits().value;
        System.out.println("总记录数为：" + value);

        SearchHit[] hitsHits = hits.getHits();

        //准备一个容器装入数据
        ArrayList<Goods> goodsArrayList = new ArrayList<>();

        for (SearchHit goodsJson : hitsHits) {

            String sourceAsString = goodsJson.getSourceAsString();

            //将Json数据转换为对象

            Goods goods = JSON.parseObject(sourceAsString, Goods.class);


            goodsArrayList.add(goods);

        }

        goodsArrayList.forEach(System.out::println);


    }


















}
