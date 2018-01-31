package cn.sisyphe.coffee.stock.amqp;

import cn.sisyphe.coffee.stock.application.OffsetManager;
import cn.sisyphe.coffee.stock.domain.offset.parser.StationParser;
import cn.sisyphe.framework.web.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Created by heyong on 2018/1/8 14:52
 * Description: 接收站间站内单据
 *
 * @author heyong
 */
@Component
public class StationBillReceiver {


    private static final Logger LOGGER = LoggerFactory.getLogger(StationBillReceiver.class);
    @Autowired
    private OffsetManager offsetManager;


    /**
     * 接收站点单据
     *
     * @param responseResult
     */
    @RabbitListener(queues = "bill-to-stock")
    public void receiver(ResponseResult responseResult) {
        Map<String, Object> result = responseResult.getResult();
        LOGGER.info(result.get("bill").toString());
        offsetManager.offset(responseResult, new StationParser());
        //为了消息不被消费，测试
    }

}
