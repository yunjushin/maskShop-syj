package maskShop3;

import javax.persistence.*;

import maskShop3.external.Delivery;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer qty;
    private String type;


    @PostPersist
    public void onPostPersist(){

        // order -> delivery create
        maskShop3.external.Delivery delivery = new maskShop3.external.Delivery();
        delivery.setOrderId(getOrderId());
        delivery.setStatus("ordered");
        delivery.setProductId(getProductId());
        delivery.setInvQty(getQty());
        delivery.setId(getId()+10000);
        OrderApplication.applicationContext.getBean(maskShop3.external.DeliveryService.class).update(delivery);

        Ordered ordered = new Ordered();
        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();
    }
/*
    @PreRemove
    public void onPreRemove(){
        OrderCanceled orderCanceled = new OrderCanceled();
        BeanUtils.copyProperties(this, orderCanceled);
        orderCanceled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        maskShop3.external.Delivery delivery = new maskShop3.external.Delivery();
        delivery.setOrderId(getOrderId());
        delivery.setStatus("CANCELED");
        // mappings goes here
        OrderApplication.applicationContext.getBean(maskShop3.external.DeliveryService.class).orderCancel(delivery);

    }
*/

    @PostUpdate
    public void onPostUpdate(){

        System.out.println("onPostUpdate ==================================" + getType());

        if(getType().equals("cancel")) {
            System.out.println("onPostUpdate:cancel ==================================");

            OrderCanceled orderCanceled = new OrderCanceled();
            BeanUtils.copyProperties(this, orderCanceled);
            orderCanceled.publishAfterCommit();
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
