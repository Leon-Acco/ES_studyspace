package com.acho.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    private String host;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    private Integer port;


    @Override
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port));
        return new RestHighLevelClient(builder);
    }

       /* 重写父类方法
    @Override
    public RestHighLevelClient elasticsearchClient() {
        return RestClients.create(ClientConfiguration.create("localhost:9201")).rest();
    }*/
//
//    @Bean
//    @Override
//    public EntityMapper entityMapper() {
//        ElasticsearchEntityMapper entityMapper
//                = new ElasticsearchEntityMapper(elasticsearchMappingContext(), new DefaultConversionService());
//        entityMapper.setConversions(elasticsearchCustomConversions());
//        return entityMapper;
//    }


}