package maskShop3;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeliveryRepository extends PagingAndSortingRepository<Delivery, Long>{
    Optional<Delivery> findByOrderId(@Param("order_id") Long aLong);
}