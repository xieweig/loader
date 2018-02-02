package cn.sisyphe.coffee.stock.domain.storage.model;


import cn.sisyphe.coffee.stock.domain.shared.BaseEntity;
import cn.sisyphe.coffee.stock.domain.shared.station.Station;
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

/**
 * Created by heyong on 2018/1/5 9:34
 * Description: 库存
 *
 * @author heyong
 */
@Entity
@Table(name = "storage_inventory")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class StorageInventory extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long inventoryId;

    /**
     * 站点代码
     */
    private Station station;

    /**
     * 货物代码
     */
    private Cargo cargo;

    /**
     * 原料编码
     */
    @Column(updatable = false)
    private RawMaterial rawMaterial;

    /**
     * 最小单位的总量
     */
    private Integer totalAmount;

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "StorageInventory{" +
                "inventoryId=" + inventoryId +
                ", station=" + station +
                ", cargo=" + cargo +
                ", rawMaterial=" + rawMaterial +
                ", totalAmount=" + totalAmount +
                "} " + super.toString();
    }
}
