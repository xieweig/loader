package cn.sisyphe.coffee.stock.viewmodel;

import java.util.List;

/**
 * Created by XiongJing on 2018/1/30.
 * remark：前端多条件查询
 * version: 1.0
 *
 * @author XiongJing
 */
public class StorageDTO {

    /**
     * 总数量
     */
    private Long totalPageSize;
    /**
     * 具体数据
     */
    private List<StorageQueryDTO> content;

    public Long getTotalPageSize() {
        return totalPageSize;
    }

    public void setTotalPageSize(Long totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public List<StorageQueryDTO> getContent() {
        return content;
    }

    public void setContent(List<StorageQueryDTO> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "StorageDTO{" +
                "totalPageSize=" + totalPageSize +
//                ", content=" + content +
                '}';
    }
}
