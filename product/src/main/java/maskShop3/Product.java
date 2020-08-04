package maskShop3;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Product_table")
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long productId;
    private String productName;
    private Integer invQty;

    @PostPersist
    public void onPostPersist(){
        Registerd registerd = new Registerd();
        BeanUtils.copyProperties(this, registerd);
        registerd.publishAfterCommit();

        maskShop3.external.Inventory inventory = new maskShop3.external.Inventory();
        // mappings goes here
        ProductApplication.applicationContext.getBean(maskShop3.external.InventoryService.class).register(inventory);

    }


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

    public Integer getInvQty() {
        return invQty;
    }

    public void setInvQty(Integer invQty) {
        this.invQty = invQty;
    }

}
