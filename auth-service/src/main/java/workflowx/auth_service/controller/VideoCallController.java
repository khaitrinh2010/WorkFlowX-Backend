package workflowx.auth_service.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import workflowx.auth_service.entity.VideoSignal;

/**
 * A user starts a call by sending an offer to the app/video-offer endpoint.
 * Receiving user receives the offer and sends back an answer to the app/video-answer endpoint.
 */
@Controller
public class VideoCallController {

    @MessageMapping("/video-offer")
    @SendTo("/video/offer")
    public VideoSignal handleVideoOffer(VideoSignal offer) {
        return offer; // Broadcasts the offer to the receiving user
    }

    @MessageMapping("/video-answer")
    @SendTo("/video/answer")
    public VideoSignal handleVideoAnswer(VideoSignal answer) {
        return answer; // Broadcasts the answer back to the caller
    }
}
