package cn.sisyphe.coffee.stock.domain.pending;

import cn.sisyphe.coffee.stock.domain.pending.enums.BillTypeEnum;
import cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage;
import cn.sisyphe.coffee.stock.domain.shared.BaseEntity;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyong on 2018/1/5 11:48
 * Description: 待冲减项 -- 以项为整体
 *
 * @author heyong
 */
@Entity
@Table(name = "pending_bill_item")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class PendingBillItem extends BaseEntity {

    /**
     * 项目号
     */
    @Id
    @Column(updatable = false)
    private String itemCode;

    /**
     * 调入站点
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "stationCode", column = @Column(name = "in_station_code")),
            @AttributeOverride(name = "storageCode", column = @Column(name = "in_storage_code"))
    })
    private Station inStation;

    /**
     * 调出站点
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "stationCode", column = @Column(name = "out_station_code")),
            @AttributeOverride(name = "storageCode", column = @Column(name = "out_storage_code"))
    })
    private Station outStation;

    /**
     * 进出库类型
     */
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private InOutStorage inOutStorage;

    /**
     * 是否已冲减
     */
    private Boolean isOffset = false;

    /**
     * 待冲减的物品
     */
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @org.hibernate.annotations.ForeignKey(name = "none")
    @JoinColumn(name = "itemCode", referencedColumnName = "itemCode")
    private List<PendingBillDetail> pendingBillDetailList = new ArrayList<>();

    /**
     * 来源单据的类型
     */
    @Enumerated(EnumType.STRING)
    private BillTypeEnum sourceBillType;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Station getInStation() {
        return inStation;
    }

    public void setInStation(Station inStation) {
        this.inStation = inStation;
    }


    public Station getOutStation() {
        return outStation;
    }

    public void setOutStation(Station outStation) {
        this.outStation = outStation;
    }


    public InOutStorage getInOutStorage() {
        return inOutStorage;
    }

    public void setInOutStorage(InOutStorage inOutStorage) {
        this.inOutStorage = inOutStorage;
    }

    public Boolean getOffset() {
        return isOffset;
    }

    public void setOffset(Boolean offset) {
        isOffset = offset;
    }

    public List<PendingBillDetail> getPendingBillDetailList() {
        return pendingBillDetailList;
    }

    public void setPendingBillDetailList(List<PendingBillDetail> pendingBillDetailList) {
        this.pendingBillDetailList = pendingBillDetailList;
    }

    public void addPendingBillDetails(List<PendingBillDetail> pendingBillDetails) {
        this.pendingBillDetailList.addAll(pendingBillDetails);
    }

    public BillTypeEnum getSourceBillType() {
        return sourceBillType;
    }

    public void setSourceBillType(BillTypeEnum sourceBillType) {
        this.sourceBillType = sourceBillType;
    }

    @Override
    public String toString() {
        return "PendingBillItem{" +
                "itemCode='" + itemCode + '\'' +
                ", inStation=" + inStation +
                ", outStation=" + outStation +
                ", inOutStorage=" + inOutStorage +
                ", isOffset=" + isOffset +
                '}';
    }
}
