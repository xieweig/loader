package cn.sisyphe.coffee.stock.amqp;

import cn.sisyphe.coffee.stock.application.OffsetManager;
import cn.sisyphe.coffee.stock.application.ShareManager;
import cn.sisyphe.coffee.stock.domain.offset.parser.SaleParser;
import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by heyong on 2018/1/8 14:58
 * Description: 零售单接收
 *
 * @author heyong
 */
@Component
public class SaleBillReceiver {

    @Autowired
    private OffsetManager offsetManager;

    @Autowired
    private ShareManager shareManager;

    /**
     * 接收站点单据
     *
     * @param responseResult
     */
    @RabbitListener(queues = "sale-to-stock")
    public void receiver(ResponseResult responseResult) {

        SaleParser billParser = new SaleParser();
        billParser.setShareManager(shareManager);
        billParser.setResponseResult(responseResult);
        offsetManager.offset(responseResult, billParser);
    }
}
