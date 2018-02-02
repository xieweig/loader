package cn.sisyphe.coffee.stock.domain.shared.station;


import cn.sisyphe.coffee.stock.domain.storage.model.Storage;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.Objects;

/**
 * Created by heyong on 2017/12/21 10:11
 * Description: 站点
 *
 * @author heyong
 */
@Embeddable
public class Station {

    /**
     * 站点代码
     */
    @Column(updatable = false)
    private String stationCode;

    /**
     * 站点名称
     */
    @Transient
    private String stationName;

    /**
     * 站点类型
     */
    @Transient
    private String stationType;

    /**
     * 库位号
     */
    @Column(updatable = false)
    private String storageCode;

    public Station() {
    }

    public Station(String stationCode, String storageCode) {
        this.stationCode = stationCode;
        this.storageCode = storageCode;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public Storage getStorage() {
        return new Storage(storageCode);
    }

    public void setStorage(Storage storage) {
        this.storageCode = storage.getStorageCode();
    }


    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Station station = (Station) o;
        return Objects.equals(stationCode, station.stationCode) &&
                Objects.equals(stationName, station.stationName) &&
                Objects.equals(stationType, station.stationType) &&
                Objects.equals(storageCode, station.storageCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stationCode, stationName, stationType, storageCode);
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationCode='" + stationCode + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stationType='" + stationType + '\'' +
                ", storageCode='" + storageCode + '\'' +
                '}';
    }
}
