package cn.sisyphe.coffee.stock.domain.storage.model;


import javax.persistence.Embeddable;

/**
 * Created by heyong on 2017/12/29 15:38
 * Description: 库房
 *
 * @author heyong
 */

@Embeddable
public class Storage {

    /**
     * 库房代码
     */
    private String storageCode;


    public Storage() {
    }

    public Storage(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }
}
