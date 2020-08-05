package maskShop3;

import maskShop3.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AdminpageViewHandler {

    @Autowired
    private AdminpageRepository adminpageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRegisterd_then_CREATE_1 (@Payload Registerd registerd) {
        try {
            if (registerd.isMe()) {
                // view 객체 생성
                Adminpage adminpage = new Adminpage();
                // view 객체에 이벤트의 Value 를 set 함
                adminpage.setProductId(registerd.getProductId());
                adminpage.setProductName(registerd.getProductName());
                adminpage.setInvQty(registerd.getInvQty());

                System.out.println("========adminpage whenRegisterd_then_CREATE_1 ======registerd.getInvQty() : "+registerd.getInvQty());
                System.out.println("========adminpage whenRegisterd_then_CREATE_1 ======registerd.toJson() : "+registerd.toJson());
                // view 레파지 토리에 save
                adminpageRepository.save(adminpage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenModified_then_UPDATE_1(@Payload Modified modified) {
        try {
            if (modified.isMe()) {
                // view 객체 조회
                List<Adminpage> adminpageList = adminpageRepository.findByProductId(modified.getProductId());
                for(Adminpage adminpage : adminpageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    System.out.println("========adminpage view ======"+modified.getQty());
                    adminpage.setInvQty(modified.getQty());
                    // view 레파지 토리에 save
                    adminpageRepository.save(adminpage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
