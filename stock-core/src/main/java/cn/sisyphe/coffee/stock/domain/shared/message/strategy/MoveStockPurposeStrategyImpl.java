package cn.sisyphe.coffee.stock.domain.shared.message.strategy;

import static cn.sisyphe.coffee.stock.domain.shared.Constants.MOVE_STOCK_FAIL_KEY;
import static cn.sisyphe.coffee.stock.domain.shared.Constants.MOVE_STOCK_SUCCESS_KEY;

/**
 * @Date 2018/1/31 10:53
 * @description
 */
public class MoveStockPurposeStrategyImpl extends AbstractMessagePurposeStrategy {
    @Override
    protected String getFailRoutingKey() {
        return MOVE_STOCK_FAIL_KEY;
    }

    @Override
    protected String getSuccessRoutingKey() {
        return MOVE_STOCK_SUCCESS_KEY;
    }
}
