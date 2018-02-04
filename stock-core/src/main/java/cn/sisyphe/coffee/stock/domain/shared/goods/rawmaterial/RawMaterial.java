package cn.sisyphe.coffee.stock.domain.shared.goods.rawmaterial;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * @author XiongJing
 * @date 2017/2/27
 * remark：原料信息
 * version: 1.0bate
 */
@Embeddable
public class RawMaterial {
    /**
     * 原料编号
     */
    @Column(updatable = false)
    private String rawMaterialCode;

    /**
     * 原料名称
     */
    @Transient
    private String rawMaterialName;

    /**
     * 原料单位
     */
    @Transient
    private String standardUnit;

    public RawMaterial() {
    }

    public RawMaterial(String rawMaterialCode) {
        this.rawMaterialCode = rawMaterialCode;
    }

    public String getRawMaterialCode() {
        return rawMaterialCode;
    }

    public void setRawMaterialCode(String rawMaterialCode) {
        this.rawMaterialCode = rawMaterialCode;
    }

    public String getRawMaterialName() {
        return rawMaterialName;
    }

    public void setRawMaterialName(String rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
    }

    public String getStandardUnit() {
        return standardUnit;
    }

    public void setStandardUnit(String standardUnit) {
        this.standardUnit = standardUnit;
    }

    @Override
    public String toString() {
        return "RawMaterial{" +
                "rawMaterialCode='" + rawMaterialCode + '\'' +
                ", rawMaterialName='" + rawMaterialName + '\'' +
                ", standardUnit='" + standardUnit + '\'' +
                '}';
    }
}
