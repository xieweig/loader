package cn.sisyphe.coffee.stock.domain.pending;

import cn.sisyphe.coffee.stock.domain.shared.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by heyong on 2018/1/5 11:48
 * Description: 待冲减单据
 *
 * @author heyong
 */
@Entity
@Table(name = "pending_bill")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class PendingBill extends BaseEntity{

    /**
     * 单号
     */
    @Id
    @Column(updatable = false)
    private String billCode;

    /**
     * 原单号
     */
    @Column(updatable = false)
    private String sourceCode;

    /**
     * 是否冲减
     */
    private Boolean isOffset;

    /**
     * 待冲减的项
     */
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @org.hibernate.annotations.ForeignKey(name = "none")
    @JoinColumn(name = "billCode", referencedColumnName = "billCode")
    private List<PendingBillItem> pendingBillItemList = new ArrayList<>();

    public Boolean getOffset() {
        return isOffset;
    }

    public void setOffset(Boolean offset) {
        isOffset = offset;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public List<PendingBillItem> getPendingBillItemList() {
        return pendingBillItemList;
    }

    public void setPendingBillItemList(List<PendingBillItem> pendingBillItemList) {
        this.pendingBillItemList = pendingBillItemList;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public void addPendingBillItem(PendingBillItem pendingBillItem) {
        this.pendingBillItemList.add(pendingBillItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PendingBill that = (PendingBill) o;
        return Objects.equals(billCode, that.billCode) &&
                Objects.equals(sourceCode, that.sourceCode) &&
                Objects.equals(isOffset, that.isOffset) &&
                Objects.equals(pendingBillItemList, that.pendingBillItemList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(billCode, sourceCode, isOffset, pendingBillItemList);
    }

    @Override
    public String toString() {
        return "PendingBill{" +
                "billCode='" + billCode + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                ", isOffset=" + isOffset +
                "} " + super.toString();
    }
}
