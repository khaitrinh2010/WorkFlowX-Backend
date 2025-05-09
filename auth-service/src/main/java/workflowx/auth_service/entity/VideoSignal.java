package workflowx.auth_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoSignal {
    private String sender;
    private String receiver;
    private String sdp;
}
