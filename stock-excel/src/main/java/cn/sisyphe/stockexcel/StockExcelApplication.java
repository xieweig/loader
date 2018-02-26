package cn.sisyphe.stockexcel;

import cn.sisyphe.common.excel.ExcelManager;
import cn.sisyphe.framework.cache.core.annotation.EnableS2Cache;
import cn.sisyphe.framework.message.core.annotation.EnableS2Messaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by XiongJing on 2018/2/24.
 * remark：
 * version:
 *
 * @author XiongJing
 */
@SpringBootApplication
@ComponentScan({"cn.sisyphe.stockexcel", "cn.sisyphe.coffee.stock"})
@EnableS2Cache
//@Import(GlobalExceptionHandler.class)
//@EnableScopeAuth
@EnableS2Messaging
@EnableSwagger2
@EnableEurekaClient
@EnableFeignClients
@EnableAsync
public class StockExcelApplication implements CommandLineRunner {
    @Value(value = "${down.load.path}")
    private String downLoadPath;

    public static void main(String[] args) {
        SpringApplication.run(StockExcelApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        ExcelManager.instance().setFilePath(downLoadPath);
    }
}
