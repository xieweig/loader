package cn.sisyphe.coffee.stock.domain.shared.goods.cargo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * @author heyong
 * @date 2017/1/23 16:40
 * 货物
 */
@Embeddable
public class Cargo {
    /**
     * 货物编号
     */
    @Column(updatable = false)
    private String cargoCode;

    /**
     * 货物名称
     */
    @Transient
    private String cargoName;

    /**
     * 货物原有的条码
     */
    @Transient
    private String barCode;

    /**
     * 计量
     */
    @Transient
    private Integer measurement;

    /**
     * 标准单位
     */
    @Transient
    private String standardUnit;

    /**
     * 保质期 - 天
     */
    @Transient
    private Integer effectiveTime;

    public Cargo() {
    }

    public Cargo(String cargoCode) {
        this.cargoCode = cargoCode;
    }

    public String getCargoCode() {
        return cargoCode;
    }

    public void setCargoCode(String cargoCode) {
        this.cargoCode = cargoCode;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Integer measurement) {
        this.measurement = measurement;
    }

    public String getStandardUnit() {
        return standardUnit;
    }

    public void setStandardUnit(String standardUnit) {
        this.standardUnit = standardUnit;
    }

    public Integer getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Integer effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "cargoCode='" + cargoCode + '\'' +
                ", cargoName='" + cargoName + '\'' +
                ", barCode='" + barCode + '\'' +
                ", measurement=" + measurement +
                ", standardUnit='" + standardUnit + '\'' +
                ", effectiveTime=" + effectiveTime +
                '}';
    }
}
