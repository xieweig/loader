package cn.sisyphe.coffee.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by heyong on 2018/1/31 18:05
 * Description:
 * @author heyong
 */

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
@EnableFeignClients
public class StockClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockClientApplication.class, args);
    }
}
