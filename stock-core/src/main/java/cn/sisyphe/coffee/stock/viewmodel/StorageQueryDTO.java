package cn.sisyphe.coffee.stock.viewmodel;

import java.util.Date;

/**
 * Created by XiongJing on 2018/1/29.
 * remark：多条件查询返回给前端的字段
 * version: 1.0
 *
 * @author XiongJing
 */
public class StorageQueryDTO {

    /**
     * 截至时间
     */
    private Date stopTime;

    /**
     * 站点名称
     */
    private String stationName;

    /**
     * 库位
     */
    private String storageName;

    /**
     * 货物编码
     */
    private String cargoCode;

    /**
     * 货物名称
     */
    private String cargoName;

    /**
     * 规格--100ml/盒
     */
    private String standardName;

    /**
     * 原料名称
     */
    private String materialName;

    /**
     * 原料编码
     */
    private String materialCode;

    /**
     * 数量--600ml
     */
    private String number;

    /**
     * 变化量 -50ml
     */
    private String changeNumber;

    /**
     * 单据类型
     */
    private String billType;

    /**
     * 单号
     */
    private String billCode;

    /**
     * 变化原因
     */
    private String changeMemo;

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
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

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getChangeNumber() {
        return changeNumber;
    }

    public void setChangeNumber(String changeNumber) {
        this.changeNumber = changeNumber;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getChangeMemo() {
        return changeMemo;
    }

    public void setChangeMemo(String changeMemo) {
        this.changeMemo = changeMemo;
    }

    @Override
    public String toString() {
        return "StorageQueryDTO{" +
                "stopTime=" + stopTime +
                ", stationName='" + stationName + '\'' +
                ", storageName='" + storageName + '\'' +
                ", cargoCode='" + cargoCode + '\'' +
                ", cargoName='" + cargoName + '\'' +
                ", standardName='" + standardName + '\'' +
                ", materialName='" + materialName + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", number='" + number + '\'' +
                ", changeNumber='" + changeNumber + '\'' +
                ", billType='" + billType + '\'' +
                ", billCode='" + billCode + '\'' +
                ", changeMemo='" + changeMemo + '\'' +
                '}';
    }
}
