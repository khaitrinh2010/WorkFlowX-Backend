package workflowx.auth_service.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import workflowx.auth_service.entity.VideoSignal;
import workflowx.auth_service.entity.IceCandidate;

@Controller
public class VideoCallController {

    @MessageMapping("/video-offer")
    @SendTo("/video/offer")
    public VideoSignal handleVideoOffer(VideoSignal offer) {
        System.out.println("Received Video Offer: " + offer);
        return offer;
    }

    @MessageMapping("/video-answer")
    @SendTo("/video/answer")
    public VideoSignal handleVideoAnswer(VideoSignal answer) {
        System.out.println("Received Video Answer: " + answer);
        return answer;
    }

    @MessageMapping("/video-candidate")
    @SendTo("/video/candidate")
    public IceCandidate handleIceCandidate(IceCandidate candidate) {
        System.out.println("Received ICE Candidate: " + candidate);
        return candidate;
    }
}
