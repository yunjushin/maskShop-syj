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
public class AdminPageViewHandler {


    @Autowired
    private AdminPageRepository adminPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenInvRegisterd_then_CREATE_1 (@Payload InvRegisterd invRegisterd) {
        try {
            if (invRegisterd.isMe()) {
                // view 객체 생성
                AdminPage adminPage = new AdminPage();
                // view 객체에 이벤트의 Value 를 set 함
                adminPage.setProductId(invRegisterd.getProductId());
                adminPage.setInvQty(invRegisterd.getInvQty());
                // view 레파지 토리에 save
                adminPageRepository.save(adminPage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenInvUpdated_then_UPDATE_1(@Payload InvUpdated invUpdated) {
        try {
            if (invUpdated.isMe()) {
                // view 객체 조회
                List<AdminPage> adminPageList = adminPageRepository.findByProductId(invUpdated.getProductId());
                for(AdminPage adminPage : adminPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    adminPage.setInvQty(invUpdated.getInvQty());
                    // view 레파지 토리에 save
                    adminPageRepository.save(adminPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}