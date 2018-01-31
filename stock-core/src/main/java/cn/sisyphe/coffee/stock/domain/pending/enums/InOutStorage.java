package cn.sisyphe.coffee.stock.domain.pending.enums;

/**
 * 单据作用
 *
 * @author heyong
 */

public enum InOutStorage {

    /**
     * 出库
     */
    OUT_STORAGE(-1),

    /**
     * 入库
     */
    IN_STORAGE(1),

    /**
     * 移库
     */
    MOVE_STORAGE(0);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    InOutStorage(int i) {
        this.value = i;
    }

    public static InOutStorage valueOf(int value) {
        for (InOutStorage inoutStorage : InOutStorage.values()) {
            if (inoutStorage.value == value) {
                return inoutStorage;
            }
        }

        return null;
    }
}
