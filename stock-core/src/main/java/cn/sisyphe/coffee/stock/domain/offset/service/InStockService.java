package cn.sisyphe.coffee.stock.domain.offset.service;

import cn.sisyphe.coffee.stock.domain.offset.Offset;
import cn.sisyphe.coffee.stock.domain.offset.OffsetService;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillDetail;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage;
import cn.sisyphe.framework.web.exception.DataException;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by heyong on 2018/1/25 15:02
 * Description: 入库冲减服务
 *
 * @author heyong
 */
public class InStockService implements StockService {

    /**
     * 冲减服务
     */
    private OffsetService offsetService;
    /**
     * 待冲减项
     */
    private PendingBillItem pendingBillItem;


    public InStockService(OffsetService offsetService, PendingBillItem pendingBillItem) {
        this.offsetService = offsetService;
        this.pendingBillItem = pendingBillItem;
    }

    /**
     * 冲减
     */
    @Override
    public void offset() {
        // 检查参数
        checkParams();

        Set<Offset> outStockOffsetList = null;
        if (!StringUtils.isEmpty(offsetService.getPendingBill().getSourceCode())) {
            // 有出库单来源的入库单
            outStockOffsetList = findOutStockOffsetBySourceCode(offsetService.getPendingBill().getSourceCode());
        }

        if (outStockOffsetList == null || outStockOffsetList.size() <= 0) {
            // 自主入库
            offsetInStock(pendingBillItem);
        } else {
            // 有出库单来源的入库
            offsetWithOutStock(outStockOffsetList);
        }

    }

    /**
     * 参数检查
     */
    private void checkParams() {
        if (pendingBillItem == null || pendingBillItem.getInStation() == null ||
                StringUtils.isEmpty(pendingBillItem.getInStation().getStationCode()) ||
                StringUtils.isEmpty(pendingBillItem.getInStation().getStorageCode())) {

            throw new DataException("200007", "参数不完整", null, pendingBillItem);
        }
    }


    /**
     * 自主入库
     *
     * @param pendingBillItem
     */
    private void offsetInStock(PendingBillItem pendingBillItem) {
        Set<Offset> inStockOffsetList = new LinkedHashSet<>();

        for (PendingBillDetail detail : pendingBillItem.getPendingBillDetailList()) {

            if (detail.getOffset() != null && detail.getOffset()) {
                continue;
            } else if (detail.getActualTotalAmount() == null) {
                throw new DataException("200008", "没有实拣数量");
            } else if (detail.getRawMaterial() == null || StringUtils.isEmpty(detail.getRawMaterial().getRawMaterialCode())) {
                throw new DataException("200009", "原料信息不完整");
            }

            // 入库
            inStockOffsetList.add(offsetService.createByPendingDetail(detail, pendingBillItem.getInStation(), detail.getActualTotalAmount(), InOutStorage.IN_STORAGE
                    , pendingBillItem.getInStation(), pendingBillItem.getOutStation(), pendingBillItem.getSourceBillType()));
        }

        // 保存冲减
        offsetService.save(inStockOffsetList);
    }


    /**
     * 使用出库单进行入库
     *
     * @param outStockOffsetList
     */
    private void offsetWithOutStock(Set<Offset> outStockOffsetList) {

        Set<Offset> inStockOffsetList = new LinkedHashSet<>();

        for (Offset outOffset : outStockOffsetList) {
            // 通过出库冲减生成入库冲减
            inStockOffsetList.add(offsetService.createByOffsetting(outOffset, pendingBillItem.getInStation(), outOffset.getTotalOffsetAmount(), InOutStorage.IN_STORAGE
                    , pendingBillItem.getInStation(), pendingBillItem.getOutStation(), pendingBillItem.getSourceBillType()));
        }

        // 保存冲减
        offsetService.save(inStockOffsetList);
    }


    /**
     * 根据源单号获取出库明细
     *
     * @param sourceCode
     * @return
     */
    private Set<Offset> findOutStockOffsetBySourceCode(String sourceCode) {
        return offsetService.getOffsetDataPersistence().getOffsetRepository().findAllBySourceCodeOrderByCreateTime(sourceCode);
    }
}
