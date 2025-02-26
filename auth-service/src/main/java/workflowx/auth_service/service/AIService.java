package workflowx.auth_service.service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;  // ✅ CORRECT!


@Service
public class AIService {
    @Value("${spring.ai.openai.api-key}")
    private String openaiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public String summarizeText(String text) {
        String prompt = "Summarize: " + text;
        return callOpenAI(prompt);
    }

    public String extractTopics(String text) {
        String prompt = "Extract key topics from the following text in 1 sentence 30 words:\n" + text;
        return callOpenAI(prompt);
    }

    public String callOpenAI(String userInput) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject request = new JSONObject();
        request.put("model", "gpt-3.5-turbo");
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", "You are an AI assistant."));
        messages.put(new JSONObject().put("role", "user").put("content", userInput));
        request.put("messages", messages);
        request.put("max_tokens", 100);
        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, entity, String.class);
        return new JSONObject(response.getBody())
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
                .trim();
    }
}
