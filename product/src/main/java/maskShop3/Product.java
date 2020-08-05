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

        System.out.println("#### PRODUCT    onPostPersist ==================================");
        maskShop3.external.Inventory inventory = new maskShop3.external.Inventory();
        inventory.setProductId((getProductId()));
        inventory.setInvQty(getInvQty());

        ProductApplication.applicationContext.getBean(maskShop3.external.InventoryService.class).register(inventory);

        Registerd registerd = new Registerd();
        BeanUtils.copyProperties(this, registerd);

        registerd.publishAfterCommit();

    }

    @PostUpdate
    public void onPostUpdate(){
/*
        if(getType().equals("cancel")) {
            System.out.println("onPostUpdate:cancel ==================================");
            OrderCanceled orderCanceled = new OrderCanceled();
            BeanUtils.copyProperties(this, orderCanceled);
            orderCanceled.publishAfterCommit();
        }
 */
        System.out.println("#### PRODUCT    PostUpdate ==================================");

        ProductModified productModified = new ProductModified();
        BeanUtils.copyProperties(this, productModified);
        System.out.println("#### PRODUCT    PostUpdate ==============productModified.getProductId():"+productModified.getProductId() + "======productModified.getInvQty():"+productModified.getInvQty());
        productModified.publishAfterCommit();

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
