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
     * 出库站点
     */
    private String outStationName;

    /**
     * 出库库位
     */
    private String putStorageCode;

    /**
     * 入库站点
     */
    private String inStationName;

    /**
     * 入库库位
     */
    private String inStorageName;


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

    public String getOutStationName() {
        return outStationName;
    }

    public void setOutStationName(String outStationName) {
        this.outStationName = outStationName;
    }

    public String getPutStorageCode() {
        return putStorageCode;
    }

    public void setPutStorageCode(String putStorageCode) {
        this.putStorageCode = putStorageCode;
    }

    public String getInStationName() {
        return inStationName;
    }

    public void setInStationName(String inStationName) {
        this.inStationName = inStationName;
    }

    public String getInStorageName() {
        return inStorageName;
    }

    public void setInStorageName(String inStorageName) {
        this.inStorageName = inStorageName;
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
                ", outStationName='" + outStationName + '\'' +
                ", putStorageCode='" + putStorageCode + '\'' +
                ", inStationName='" + inStationName + '\'' +
                ", inStorageName='" + inStorageName + '\'' +
                '}';
    }
}
