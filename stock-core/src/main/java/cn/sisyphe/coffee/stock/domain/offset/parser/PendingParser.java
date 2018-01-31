package cn.sisyphe.coffee.stock.domain.offset.parser;

import cn.sisyphe.coffee.stock.domain.offset.OffsetService;
import cn.sisyphe.coffee.stock.domain.offset.strategy.CargoFirstOffsetStrategy;
import cn.sisyphe.coffee.stock.domain.offset.strategy.OffsetStrategy;
import cn.sisyphe.coffee.stock.domain.pending.PendingBill;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.framework.web.ResponseResult;

/**
 * 查询未冲减单据
 */
public class PendingParser implements BillParser {

    /**
     * 冲减服务
     */
    private OffsetService offsetService;

    /**
     * 冲减操作上下文
     *
     * @param offsetService
     */
    @Override
    public void setOffsetService(OffsetService offsetService) {
        this.offsetService = offsetService;
    }

    /**
     * 冲减策略
     *
     * @return
     */
    @Override
    public OffsetStrategy getOffsetStrategy() {
        return new CargoFirstOffsetStrategy(offsetService);
    }

    /**
     * 是否保存未冲减的单据
     *
     * @return
     */
    @Override
    public Boolean isSaveUnOffsetBill() {
        return true;
    }

    /**
     * 未冲减 PendingBill
     *
     * @return
     */
    @Override
    public PendingBill parseBill(ResponseResult responseResult) {

        return offsetService.getOffsetDataPersistence().getPendingRepository().findFirstPendingBillIsOffset(false);
    }

    /**
     * 成功
     *
     * @param pendingBillItem
     */
    @Override
    public void success(PendingBillItem pendingBillItem) {

    }

    /**
     * 失败
     *
     * @param pendingBillItem
     */
    @Override
    public void fail(PendingBillItem pendingBillItem) {

    }


}
