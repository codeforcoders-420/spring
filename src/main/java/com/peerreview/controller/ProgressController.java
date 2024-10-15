import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProgressController {

    private final SimpMessagingTemplate messagingTemplate;

    public ProgressController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendProgressUpdate(double percentage) {
        messagingTemplate.convertAndSend("/topic/progress", percentage);
    }
}
