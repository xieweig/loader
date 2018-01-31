package cn.sisyphe.coffee.stock.domain.shared.message.strategy;

import static cn.sisyphe.coffee.stock.domain.shared.Constants.IN_STOCK_FAIL_KEY;
import static cn.sisyphe.coffee.stock.domain.shared.Constants.IN_STOCK_SUCCESS_KEY;

/**
 * @Date 2018/1/31 10:49
 * @description
 */
public class InStockPurposeStrategyImpl extends AbstractMessagePurposeStrategy {

    @Override
    protected String getFailRoutingKey() {
        return IN_STOCK_FAIL_KEY;
    }

    @Override
    protected String getSuccessRoutingKey() {
        return IN_STOCK_SUCCESS_KEY;
    }
}
