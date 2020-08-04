package maskShop3.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name="inventory", url="http://localhost:8085")
@FeignClient(name="inventory", url="http://inventory:8080")
public interface InventoryService {

    @RequestMapping(method= RequestMethod.POST, path="/inventories")
    public void register(@RequestBody Inventory invRegisterd);

}