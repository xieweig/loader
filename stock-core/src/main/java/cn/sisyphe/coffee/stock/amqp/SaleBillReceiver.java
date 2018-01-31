package cn.sisyphe.coffee.stock.amqp;

import cn.sisyphe.coffee.stock.application.OffsetManager;
import cn.sisyphe.coffee.stock.domain.offset.parser.SaleParser;
import cn.sisyphe.framework.web.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by heyong on 2018/1/8 14:58
 * Description: 零售单接收
 * @author heyong
 */
@Component
public class SaleBillReceiver {

    @Autowired
    private OffsetManager offsetManager;

    /**
     * 接收站点单据
     * @param responseResult
     */
    public void receiver(ResponseResult responseResult) {

        offsetManager.offset(responseResult, new SaleParser());
    }
}
