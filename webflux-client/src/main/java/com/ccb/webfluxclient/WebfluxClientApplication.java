package com.ccb.webfluxclient;

import com.ccb.interfaces.ProxyCreator;
import com.ccb.interfaces.iml.JdkProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebfluxClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxClientApplication.class, args);
    }



    @Bean
    public ProxyCreator jdkProxyCreator() {
        return new JdkProxyCreator();
    }


    @Bean
    public FactoryBean<IUserApi> userApi(ProxyCreator proxyCreator) {
        return new FactoryBean<IUserApi>() {
            /**
             * 返回代理对象
             * @return
             */
            @Override
            public IUserApi getObject() {
                return (IUserApi) proxyCreator.craeteProxy(this.getObjectType());
            }

            /**
             * 返回的类型
             * @return
             */
            @Override
            public Class<?> getObjectType() {
                return IUserApi.class;
            }
        };
    }

}
