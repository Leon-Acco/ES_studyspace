package com.acho.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @Description:
 * @Author:Acho-leon
 * @Modified By:
 * @params:
 * @creat:2021-10-19-19:43
 */

@SpringBootConfiguration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchConfig {
    private String  host;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private int port;



    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Bean
    public RestHighLevelClient client(){


        return  new RestHighLevelClient(RestClient.builder(new HttpHost(host,port,"http")));
    }
}
