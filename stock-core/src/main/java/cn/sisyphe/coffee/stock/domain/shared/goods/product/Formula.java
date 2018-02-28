package cn.sisyphe.coffee.stock.domain.shared.goods.product;

/**
 * @Date 2018/2/27 15:28
 * @description
 */
public class Formula {

    private String rawMaterialCode;

    private Integer amount;

    private String formulaType;

    public String getRawMaterialCode() {
        return rawMaterialCode;
    }

    public void setRawMaterialCode(String rawMaterialCode) {
        this.rawMaterialCode = rawMaterialCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(String formulaType) {
        this.formulaType = formulaType;
    }
}
