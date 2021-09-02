package com.spring.base.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

//*
// * RestTemplate配置类


@Configuration
public class RestTemplateConfig {


    //最好是用不注释的方法，在注入的同时设置连接时间，这种注释的也可以，但是没有设置超时时间
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

//    @Bean
//    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
//       RestTemplate restTemplate = new RestTemplate(factory);
//        List<HttpMessageConverter<?>> messageConverters =
// restTemplate.getMessageConverters();
//        Iterator<HttpMessageConverter<?>> iterator=messageConverters.iterator();
//        while(iterator.hasNext()){
//            HttpMessageConverter<?> converter=iterator.next();
//            //原有的String是ISO-8859-1编码 去掉
//            if(converter instanceof StringHttpMessageConverter){
//                iterator.remove();
//            }
//
//        }
//        messageConverters.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
//		return restTemplate;
//    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        return factory;
    }
}
