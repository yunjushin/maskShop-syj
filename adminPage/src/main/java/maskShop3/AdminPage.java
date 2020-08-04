package maskShop3;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="AdminPage_table")
public class AdminPage {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
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
        public Integer getInvQty() {
            return invQty;
        }

        public void setInvQty(Integer invQty) {
            this.invQty = invQty;
        }

}
