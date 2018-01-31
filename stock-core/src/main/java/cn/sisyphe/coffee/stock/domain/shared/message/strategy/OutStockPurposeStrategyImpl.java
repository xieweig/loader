package cn.sisyphe.coffee.stock.domain.shared.message.strategy;

import static cn.sisyphe.coffee.stock.domain.shared.Constants.OUT_STOCK_FAIL_KEY;
import static cn.sisyphe.coffee.stock.domain.shared.Constants.OUT_STOCK_SUCCESS_KEY;

/**
 * @Date 2018/1/31 10:52
 * @description
 */
public class OutStockPurposeStrategyImpl extends AbstractMessagePurposeStrategy {


    @Override
    protected String getFailRoutingKey() {
        return OUT_STOCK_FAIL_KEY;
    }

    @Override
    protected String getSuccessRoutingKey() {
        return OUT_STOCK_SUCCESS_KEY;
    }
}
