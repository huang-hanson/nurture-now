package com.health.fitness.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LLMService {

    @Value("${llm.api-url}")
    private String apiUrl;

    @Value("${llm.api-key}")
    private String apiKey;

    @Value("${llm.model}")
    private String model;

    private RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> queryFoodCalories(String foodName) {
        String prompt = String.format(
            "请告诉我'%s'每100克的热量是多少卡路里？请只返回数字，不要其他文字。",
            foodName
        );

        try {
            String response = callLLM(prompt);
            Integer calories = parseCalories(response);

            Map<String, Object> result = new HashMap<>();
            result.put("name", foodName);
            result.put("calories", calories);
            result.put("category", "其他");
            result.put("description", "AI查询结果");
            return result;
        } catch (Exception e) {
            throw new RuntimeException("AI查询失败: " + e.getMessage());
        }
    }

    public Map<String, Object> queryExerciseCalories(String exerciseName) {
        String prompt = String.format(
            "请告诉我'%s'每小时能消耗多少卡路里？请只返回数字，不要其他文字。",
            exerciseName
        );

        try {
            String response = callLLM(prompt);
            Integer caloriesBurned = parseCalories(response);

            Map<String, Object> result = new HashMap<>();
            result.put("name", exerciseName);
            result.put("caloriesBurned", caloriesBurned);
            result.put("category", "其他");
            result.put("duration", 30);
            result.put("description", "AI查询结果");
            return result;
        } catch (Exception e) {
            throw new RuntimeException("AI查询失败: " + e.getMessage());
        }
    }

    private String callLLM(String prompt) {
        // DeepSeek/OpenAI API 格式
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);

        JSONArray messages = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);
        requestBody.put("messages", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        String endpoint = apiUrl + "/v1/chat/completions";
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toJSONString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
            endpoint,
            HttpMethod.POST,
            entity,
            String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = JSON.parseObject(response.getBody());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            if (choices != null && choices.size() > 0) {
                JSONObject firstChoice = choices.getJSONObject(0);
                JSONObject message = firstChoice.getJSONObject("message");
                if (message != null) {
                    return message.getString("content");
                }
            }
        }

        throw new RuntimeException("AI服务返回异常: " + response.getBody());
    }

    private Integer parseCalories(String response) {
        try {
            String cleaned = response.replaceAll("[^0-9]", "");
            if (!cleaned.isEmpty()) {
                return Integer.parseInt(cleaned);
            }
        } catch (Exception e) {
            // ignore
        }
        return 0;
    }
}