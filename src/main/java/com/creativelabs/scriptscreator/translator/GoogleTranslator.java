package com.creativelabs.scriptscreator.translator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

class GoogleTranslator {
    public static String translate(String text, String sourceLanguage, String targetLanguage) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://translation.googleapis.com/language/translate/v2";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("token");
        headers.set("x-goog-user-project", "translations-446913");
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("q", new String[] {
                text
        });
        requestBody.put("source", sourceLanguage);
        requestBody.put("target", targetLanguage);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode translatedTextNode = rootNode
                    .path("data")
                    .path("translations")
                    .get(0)
                    .path("translatedText");

            if (translatedTextNode.isTextual()) {
                return translatedTextNode.asText().replace("&#39;", "'");
            } else {
                System.out.println("Translation not found in response.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(translate("polski tekst.", "pl", "en-GB"));
    }
}

