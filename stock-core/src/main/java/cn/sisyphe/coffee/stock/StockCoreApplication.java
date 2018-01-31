package cn.sisyphe.coffee.stock;

import cn.sisyphe.coffee.stock.infrastructure.offset.jpa.JPAOffsetRepository;
import cn.sisyphe.framework.message.core.annotation.EnableS2Messaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by heyong on 2018/1/3 16:42
 * Description:
 *
 * @author heyong
 */
@SpringBootApplication
@EnableScheduling
@EnableS2Messaging

public class StockCoreApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StockCoreApplication.class, args);

    }



    @Autowired
    private JPAOffsetRepository jpaOffsetRepository;

    @Override
    public void run(String... strings) {

        //System.err.println(jpaOffsetRepository.findOne(1L));
    }


}
