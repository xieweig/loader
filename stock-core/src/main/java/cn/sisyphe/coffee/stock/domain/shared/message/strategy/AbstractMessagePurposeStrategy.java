package cn.sisyphe.coffee.stock.domain.shared.message.strategy;

import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.framework.message.core.MessagingHelper;
import cn.sisyphe.framework.web.ResponseResult;

import static cn.sisyphe.coffee.stock.domain.shared.Constants.BILL_EXCHANGE;

/**
 * @author ncmao
 * @Date 2018/1/31 10:43
 * @description
 */
public abstract class AbstractMessagePurposeStrategy {


    public void send(PendingBillItem pendingBillItem, ResponseResult responseResult, Boolean success) {
        if (responseResult != null) {
            MessagingHelper.messaging().convertAndSend(BILL_EXCHANGE, success ? getSuccessRoutingKey() : getFailRoutingKey(), responseResult);
        }
    }


    protected abstract String getFailRoutingKey();


    protected abstract String getSuccessRoutingKey();
}
