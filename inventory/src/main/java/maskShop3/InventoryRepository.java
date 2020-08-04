package maskShop3;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Long>{
    Optional<Inventory> findByProductId(@Param("product_id") Long aLong);

}
