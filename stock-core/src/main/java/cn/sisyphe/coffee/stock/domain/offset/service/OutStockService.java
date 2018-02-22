package cn.sisyphe.coffee.stock.domain.offset.service;

import cn.sisyphe.coffee.stock.domain.offset.Offset;
import cn.sisyphe.coffee.stock.domain.offset.OffsetService;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillDetail;
import cn.sisyphe.coffee.stock.domain.pending.PendingBillItem;
import cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage;
import cn.sisyphe.coffee.stock.utils.SMath;
import cn.sisyphe.framework.web.exception.DataException;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by heyong on 2018/1/25 14:57
 * Description: 出库冲减服务
 *
 * @author heyong
 */
public class OutStockService implements StockService {

    /**
     * 冲减服务
     */
    private OffsetService offsetService;

    /**
     * 待冲减项
     */
    private PendingBillItem pendingBillItem;

    public OutStockService(OffsetService offsetService, PendingBillItem pendingBillItem) {
        this.offsetService = offsetService;
        this.pendingBillItem = pendingBillItem;
    }

    /**
     * 冲减
     */
    @Override
    public void offset() {
        // 参数检查
        checkParams();

        for (PendingBillDetail detail : pendingBillItem.getPendingBillDetailList()) {

            if (detail.getOffset() != null && detail.getOffset()) {
                continue;
            }

            offsetDetail(detail);
        }
    }


    /**
     * 参数检查
     */
    private void checkParams() {
        if (pendingBillItem == null || pendingBillItem.getOutStation() == null ||
                StringUtils.isEmpty(pendingBillItem.getOutStation().getStationCode()) ||
                StringUtils.isEmpty(pendingBillItem.getOutStation().getStorageCode())) {

            throw new DataException("200012", "参数不完整", null, pendingBillItem);
        }
    }


    /**
     * 冲减明细
     *
     * @param detail
     */
    private void offsetDetail(PendingBillDetail detail) {

        if (detail.getActualTotalAmount() == null) {
            throw new DataException("200013", "没有实拣数量");
        }

        // 获取可冲减的货物
        Offset offsetting = offsetService.getBillParser().getOffsetStrategy().getOffsetting(pendingBillItem.getOutStation(), detail.getRawMaterial(), detail.getCargo());

        if (offsetting == null || offsetting.getSurplusAmount() <= 0) {
            // 没有可冲减的物品
            throw new DataException("200014", "没有可冲减的物品", null, pendingBillItem, detail);
        } else if (detail.getRawMaterial() == null || StringUtils.isEmpty(detail.getRawMaterial().getRawMaterialCode())) {
            throw new DataException("200015", "原料信息不完整");
        }


        // 冲减量
        int amount = SMath.min(detail.getActualTotalAmount(), offsetting.getSurplusAmount());

        // 剩余未冲减数量
        offsetting.setSurplusAmount(offsetting.getSurplusAmount() - amount);
        // 已冲减数量
        offsetting.setOffsetAmount(offsetting.getOffsetAmount() + amount);
        // 剩余未冲减数量
        detail.setActualTotalAmount(detail.getActualTotalAmount() - amount);
        // 保存冲减数据
        offsetService.save(createOffsetList(offsetting, amount));

        if (detail.getActualTotalAmount() > 0) {
            // 重复执行
            offsetDetail(detail);
        }
    }

    /**
     * 生成冲减记录
     *
     * @param offsetting
     * @param amount
     */
    private Set<Offset> createOffsetList(Offset offsetting, int amount) {
        Set<Offset> offsetList = new LinkedHashSet<>();

        // 更新已冲减数据
        offsetList.add(offsetting);
        // 新增出库冲减
        offsetList.add(offsetService.createByOffsetting(offsetting, offsetting.getStation(), amount, InOutStorage.OUT_STORAGE
                , pendingBillItem.getInStation(), pendingBillItem.getOutStation(), pendingBillItem.getSourceBillType()));

        return offsetList;
    }


}
