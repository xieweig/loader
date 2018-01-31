package cn.sisyphe.coffee.stock.domain.offset;

import cn.sisyphe.coffee.stock.domain.pending.enums.InOutStorage;
import cn.sisyphe.coffee.stock.domain.shared.BaseEntity;
import cn.sisyphe.coffee.stock.domain.shared.Station;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Created by heyong on 2018/1/5 9:52
 * Description: 已冲减数据
 *
 * @author heyong
 */
@Entity
@Table(name = "offset")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Offset extends BaseEntity {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long offsetId;

    /**
     * 批号
     */
    @Column(updatable = false)
    private String batchCode;

    /**
     * 站点
     */
    private Station station;

    /**
     * 原料
     */
    private RawMaterial rawMaterial;

    /**
     * 货物
     */
    private Cargo cargo;

    /**
     * 当前货物库存总量
     */
    @Column(updatable = false)
    private Integer inventoryTotalAmount;

    /**
     * 本次货物冲减总量
     */
    @Column(updatable = false)
    private Integer totalOffsetAmount;

    /**
     * 已冲减数量
     */
    private Integer offsetAmount;

    /**
     * 剩余未冲减数量
     */
    private Integer surplusAmount;

    /**
     * 成本金额
     */
    @Column(updatable = false)
    private BigDecimal unitCost;

    /**
     * 过期时间
     */
    @Column(updatable = false)
    private Date expirationTime;

    /**
     * 源单号
     */
    @Column(updatable = false)
    private String sourceCode;

    /**
     * 进出库类型
     */
    @Column(updatable = false)
    private Integer inOutStorage;

    public Long getOffsetId() {
        return offsetId;
    }

    public void setOffsetId(Long offsetId) {
        this.offsetId = offsetId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
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

    public Integer getInventoryTotalAmount() {
        return inventoryTotalAmount;
    }

    public void setInventoryTotalAmount(Integer inventoryTotalAmount) {
        this.inventoryTotalAmount = inventoryTotalAmount;
    }

    public Integer getTotalOffsetAmount() {
        return totalOffsetAmount;
    }

    public void setTotalOffsetAmount(Integer totalOffsetAmount) {
        this.totalOffsetAmount = totalOffsetAmount;
    }

    public Integer getOffsetAmount() {
        return offsetAmount;
    }

    public void setOffsetAmount(Integer offsetAmount) {
        this.offsetAmount = offsetAmount;
    }

    public Integer getSurplusAmount() {
        return surplusAmount;
    }

    public void setSurplusAmount(Integer surplusAmount) {
        this.surplusAmount = surplusAmount;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public InOutStorage getInOutStorage() {
        return InOutStorage.valueOf(inOutStorage);
    }

    public void setInOutStorage(InOutStorage inOutStorage) {
        this.inOutStorage = inOutStorage.getValue();
    }

    @Override
    public String toString() {
        return "Offset{" +
                "offsetId='" + offsetId + '\'' +
                ", batchCode='" + batchCode + '\'' +
                ", station=" + station +
                ", rawMaterial=" + rawMaterial +
                ", cargo=" + cargo +
                ", inventoryTotalAmount=" + inventoryTotalAmount +
                ", totalOffsetAmount=" + totalOffsetAmount +
                ", offsetAmount=" + offsetAmount +
                ", surplusAmount=" + surplusAmount +
                ", unitCost=" + unitCost +
                ", expirationTime=" + expirationTime +
                ", sourceCode='" + sourceCode + '\'' +
                ", inOutStorage=" + inOutStorage +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Offset offset = (Offset) o;
        return Objects.equals(offsetId, offset.offsetId) &&
                Objects.equals(batchCode, offset.batchCode) &&
                Objects.equals(station, offset.station) &&
                Objects.equals(rawMaterial, offset.rawMaterial) &&
                Objects.equals(cargo, offset.cargo) &&
                Objects.equals(inventoryTotalAmount, offset.inventoryTotalAmount) &&
                Objects.equals(totalOffsetAmount, offset.totalOffsetAmount) &&
                Objects.equals(offsetAmount, offset.offsetAmount) &&
                Objects.equals(surplusAmount, offset.surplusAmount) &&
                Objects.equals(unitCost, offset.unitCost) &&
                Objects.equals(expirationTime, offset.expirationTime) &&
                Objects.equals(sourceCode, offset.sourceCode) &&
                Objects.equals(inOutStorage, offset.inOutStorage);
    }

    @Override
    public int hashCode() {

        return Objects.hash(offsetId, batchCode, station, rawMaterial, cargo, inventoryTotalAmount, totalOffsetAmount, offsetAmount, surplusAmount, unitCost, expirationTime, sourceCode, inOutStorage);
    }
}
