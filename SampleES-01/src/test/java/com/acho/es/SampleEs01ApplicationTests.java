package com.acho.es;

import com.acho.bean.Person;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@SpringBootTest
class SampleEs01ApplicationTests {
    @Autowired
    private RestHighLevelClient client;

    @Test
    void contextLoads() {
        System.out.println(client);
    }

    /**
     * 创建索引
     */
    @Test
    void  addIndex(){
        CreateIndexRequest javaES = new CreateIndexRequest("java_es");//创建索引库的名字需要是小写。
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(javaES, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse.isAcknowledged());
            System.out.println("创建成功");
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
    }

    @Test
    void Search(){
        GetIndexRequest getIndexRequest = new GetIndexRequest("jack");

        try {
            GetIndexResponse getIndexResponse
                    = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);


            Map<String, MappingMetaData> mappings = getIndexResponse.getMappings();
            mappings.forEach((k,v) -> System.out.println(k+mappings.get(k).getSourceAsMap()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("失败");
        }
    }
    @Test
    void createMapping() throws IOException {
        CreateIndexRequest jack = new CreateIndexRequest("jack");
        String mapping = "{\n" +
                "      \"properties\" : {\n" +
                "        \"address\" : {\n" +
                "          \"type\" : \"text\",\n" +
                "          \"analyzer\" : \"ik_max_word\"\n" +
                "        },\n" +
                "        \"age\" : {\n" +
                "          \"type\" : \"long\"\n" +
                "        },\n" +
                "        \"name\" : {\n" +
                "          \"type\" : \"keyword\"\n" +
                "        }\n" +
                "      }\n" +
                "    }";
        jack.mapping(mapping, XContentType.JSON);
        CreateIndexResponse createIndexResponse
                = client.indices().create(jack, RequestOptions.DEFAULT);

        //3.根据返回值判断结果
        System.out.println(createIndexResponse.isAcknowledged());


    }
    @Test
    void  addDoc() throws IOException {
        Person person = new Person(1, "cheng", "武汉江汉区", 22);
        String data = JSON.toJSONString(person);
        int id = person.getId();
        String s = String.valueOf(id);
        IndexRequest indexRequest = new IndexRequest("jack").id(s).source(data, XContentType.JSON);

        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);



    }

    @Test
    void  delete() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("jack", "1");

        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);


    }
    @Test
    void findDoc() throws IOException {
        GetRequest jack = new GetRequest("jack").id(String.valueOf(1));

        GetResponse response = client.get(jack, RequestOptions.DEFAULT);
        response.getSourceAsMap().forEach((k,v) ->
                        System.out.println(k+":"+v)
                );

    }


    @Test
    void bulkOperation(){

    }



}
