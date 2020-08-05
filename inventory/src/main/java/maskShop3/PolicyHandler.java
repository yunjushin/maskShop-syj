package maskShop3;

import maskShop3.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    InventoryRepository inventoryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryRegisterd_Change(@Payload DeliveryRegisterd deliveryRegisterd){

        if(deliveryRegisterd.isMe()){
            System.out.println("##### listener REGISTER Change : " + deliveryRegisterd.toJson());

            Inventory inventory = new Inventory();

            //System.out.println("##### listener INVENTORY INSERT ======================");
            //inventory.setProductId(deliveryRegisterd.getProductId());
            //inventory.setInvQty(deliveryRegisterd.getInvQty());
            //inventoryRepository.save(inventory);

            if(inventoryRepository.findByProductId(deliveryRegisterd.getProductId()).isPresent()) {
                System.out.println("##### listener INVENTORY UPDATE ======================");

                inventory = inventoryRepository.findByProductId(deliveryRegisterd.getProductId()).get();
                inventory.setInvQty(inventory.getInvQty() - deliveryRegisterd.getInvQty());
                inventoryRepository.save(inventory);
            }
             else {
                System.out.println("##### listener INVENTORY INSERT ======================");

                inventory.setProductId(deliveryRegisterd.getProductId());
                inventory.setInvQty(deliveryRegisterd.getInvQty());
                inventoryRepository.save(inventory);
            }
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCanceled_Change(@Payload DeliveryCanceled deliveryCanceled){

        System.out.println("##### listener INVENTORY CANCEL Change : " + deliveryCanceled.toJson());

        if(deliveryCanceled.isMe()){
            System.out.println("##### listener INVENTORY CANCEL IF : ");

            Inventory inventory = new Inventory();

            inventory = inventoryRepository.findByProductId(deliveryCanceled.getProductId()).get();
            inventory.setInvQty(inventory.getInvQty() + deliveryCanceled.getInvQty());
            inventoryRepository.save(inventory);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverProductModified_Change(@Payload ProductModified productModified){

        if(productModified.isMe()) {
            System.out.println("##### listener INVENTORY MODIFIED  : " + productModified.toJson());

            Inventory inventory = new Inventory();

            inventory = inventoryRepository.findByProductId(productModified.getProductId()).get();
            inventory.setInvQty(productModified.getInvQty());
            inventoryRepository.save(inventory);
        }
    }

}
