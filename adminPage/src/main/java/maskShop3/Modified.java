package maskShop3;

public class Modified extends AbstractEvent {

    private Long id;
    private Long productId;
    private String productName;
    private Integer invQty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Integer getQty() {
        return invQty;
    }

    public void setQty(Integer invQty) {
        this.invQty = invQty;
    }
}
