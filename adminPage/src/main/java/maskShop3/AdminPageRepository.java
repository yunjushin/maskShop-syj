package maskShop3;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminPageRepository extends CrudRepository<AdminPage, Long> {

    List<AdminPage> findByProductId(Long productId);

}