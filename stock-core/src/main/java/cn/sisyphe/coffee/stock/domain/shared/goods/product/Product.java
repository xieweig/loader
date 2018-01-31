package cn.sisyphe.coffee.stock.domain.shared.goods.product;

/**
 * Created by heyong on 2018/1/5 11:23
 * Description: 产品
 *
 * @author heyong
 */
public class Product{

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
