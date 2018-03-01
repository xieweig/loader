package cn.sisyphe.coffee.stock.domain.pending;

import cn.sisyphe.coffee.stock.domain.shared.BaseEntity;
import cn.sisyphe.coffee.stock.domain.shared.goods.cargo.Cargo;
import cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial.RawMaterial;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by heyong on 2018/1/5 12:03
 * Description: 待处理单明细
 *
 * @author heyong
 */
@Entity
@Table(name = "pending_bill_detail")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class PendingBillDetail extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long detailId;

    /**
     * 原料
     */
    private RawMaterial rawMaterial;

    /**
     * 货物
     */
    private Cargo cargo;

    /**
     * 成本金额
     */
    @Column(updatable = false)
    private BigDecimal unitCost;

    /**
     * 实拣数量
     */
    @Transient
    private Float actualAmount;

    /**
     * 应拣数量
     */
    @Transient
    private Float shipAmount;

    /**
     * 应拣总量
     */
    private Integer shipTotalAmount;

    /**
     * 实拣总量
     */
    private Integer actualTotalAmount;

    /**
     * 过期时间
     */
    @Column(updatable = false)
    private Date expirationTime;

    /**
     * 是否冲减
     */
    private Boolean isOffset;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public Float getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Float actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Float getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(Float shipAmount) {
        this.shipAmount = shipAmount;
    }

    public Integer getShipTotalAmount() {
        return shipTotalAmount;
    }

    public void setShipTotalAmount(Integer shipTotalAmount) {
        this.shipTotalAmount = shipTotalAmount;
    }

    public Integer getActualTotalAmount() {
        return actualTotalAmount;
    }

    public void setActualTotalAmount(Integer actualTotalAmount) {
        this.actualTotalAmount = actualTotalAmount;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Boolean getOffset() {
        return isOffset;
    }

    public void setOffset(Boolean offset) {
        isOffset = offset;
    }

    @Override
    public String toString() {
        return "PendingBillDetail{" +
                "detailId=" + detailId +
                ", rawMaterial=" + rawMaterial +
                ", cargo=" + cargo +
                ", unitCost=" + unitCost +
                ", actualAmount=" + actualAmount +
                ", shipAmount=" + shipAmount +
                ", shipTotalAmount=" + shipTotalAmount +
                ", actualTotalAmount=" + actualTotalAmount +
                ", expirationTime=" + expirationTime +
                ", isOffset=" + isOffset +
                '}';
    }
}