package workflowx.auth_service.entity;

import lombok.Data;

@Data
public class IceCandidate {
    private String candidate;
    private String sdpMid;
    private int sdpMLineIndex;
}
