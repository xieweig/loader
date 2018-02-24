package cn.sisyphe.coffee.stock.viewmodel;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by XiongJing on 2018/1/29.
 * remark：库存查询条件
 * version: 1.0
 *
 * @author XiongJing
 */
public class ConditionQueryStorage implements Serializable {

    /**
     * 截止时间开始
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stopTimeStart;

    /**
     * 截止时间结束
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stopTimeEnd;

    /**
     * 根据货物还是原料查询
     */
    private String cargoOrMaterial;

    /**
     * 站点编码
     */
    private List<String> stationCodeArray;

    /**
     * 查询库位
     */
    private List<String> storageCodeArray;

    /**
     * 货物编码
     */
    private String cargoCode;

    /**
     * 货物名称
     */
    private List<String> cargoCodeArray;

    /**
     * 原料编码
     */
    private String materialCode;

    /**
     * 原料编码集合
     */
    private List<String> materialCodeArray;

    /**
     * 原料分类
     */
    private List<Long> materialTypeArray;

    /**
     * 根据原料分类查询出来的原料编码
     */
    private List<String> materialCodes;

    /**
     * Created by Amy on 2017/12/12 19:11
     * describe 导出excel的唯一标识
     */
    private String excelUniqueIdentification;

    /**
     * 页码
     */
    private int page;

    /**
     * 每页大小
     */
    private int pageSize;

    public Date getStopTimeStart() {
        return stopTimeStart;
    }

    public void setStopTimeStart(Date stopTimeStart) {
        this.stopTimeStart = stopTimeStart;
    }

    public Date getStopTimeEnd() {
        return stopTimeEnd;
    }

    public void setStopTimeEnd(Date stopTimeEnd) {
        this.stopTimeEnd = stopTimeEnd;
    }

    public List<String> getStationCodeArray() {
        return stationCodeArray;
    }

    public void setStationCodeArray(List<String> stationCodeArray) {
        this.stationCodeArray = stationCodeArray;
    }

    public List<String> getStorageCodeArray() {
        return storageCodeArray;
    }

    public void setStorageCodeArray(List<String> storageCodeArray) {
        this.storageCodeArray = storageCodeArray;
    }

    public String getCargoCode() {
        return cargoCode;
    }

    public void setCargoCode(String cargoCode) {
        this.cargoCode = cargoCode;
    }

    public List<String> getCargoCodeArray() {
        return cargoCodeArray;
    }

    public void setCargoCodeArray(List<String> cargoCodeArray) {
        this.cargoCodeArray = cargoCodeArray;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public List<String> getMaterialCodeArray() {
        return materialCodeArray;
    }

    public void setMaterialCodeArray(List<String> materialCodeArray) {
        this.materialCodeArray = materialCodeArray;
    }

    public List<Long> getMaterialTypeArray() {
        return materialTypeArray;
    }

    public void setMaterialTypeArray(List<Long> materialTypeArray) {
        this.materialTypeArray = materialTypeArray;
    }

    public List<String> getMaterialCodes() {
        return materialCodes;
    }

    public void setMaterialCodes(List<String> materialCodes) {
        this.materialCodes = materialCodes;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCargoOrMaterial() {
        return cargoOrMaterial;
    }

    public void setCargoOrMaterial(String cargoOrMaterial) {
        this.cargoOrMaterial = cargoOrMaterial;
    }

    public String getExcelUniqueIdentification() {
        return excelUniqueIdentification;
    }

    public void setExcelUniqueIdentification(String excelUniqueIdentification) {
        this.excelUniqueIdentification = excelUniqueIdentification;
    }

    @Override
    public String toString() {
        return "ConditionQueryStorage{" +
                "stopTimeStart=" + stopTimeStart +
                ", stopTimeEnd=" + stopTimeEnd +
                ", cargoOrMaterial='" + cargoOrMaterial + '\'' +
                ", stationCodeArray=" + stationCodeArray +
                ", storageCodeArray=" + storageCodeArray +
                ", cargoCode='" + cargoCode + '\'' +
                ", cargoCodeArray=" + cargoCodeArray +
                ", materialCode='" + materialCode + '\'' +
                ", materialCodeArray=" + materialCodeArray +
                ", materialTypeArray=" + materialTypeArray +
                ", materialCodes=" + materialCodes +
                ", excelUniqueIdentification='" + excelUniqueIdentification + '\'' +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
