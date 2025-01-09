package com.studere.studerejava.framework.services;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.*;
import com.studere.studerejava.framework.core.exceptions.OpenAIAPIError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAIService {

    private final OpenAIClient client;

    private final String model;
    private final double temperature;
    private final int maxTokens;


    public OpenAIService(
            @Value("${openai.api.key}") String apiKey,
            @Value("${openai.model:gpt-4o}") String model,
            @Value("${openai.temperature:0.7}") double temperature,
            @Value("${openai.maxTokens:1000}") int maxTokens
    ) {
        this.model = model;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    private ChatCompletionCreateParams createPrompt(String prompt) {
        ChatCompletionUserMessageParam userMessage = ChatCompletionUserMessageParam.builder()
                .role(ChatCompletionUserMessageParam.Role.USER)
                .content(ChatCompletionUserMessageParam.Content.ofTextContent(prompt))
                .build();

        ChatCompletionMessageParam messageParam = ChatCompletionMessageParam.ofChatCompletionUserMessageParam(userMessage);

        return ChatCompletionCreateParams.builder()
                .messages(List.of(messageParam))
                .model(ChatModel.of(model))
                .temperature(temperature)
                .maxTokens(maxTokens)
                .build();
    }


    public String getResponse(String prompt) {
        ChatCompletionCreateParams params = createPrompt(prompt);
        try {
            ChatCompletion chatCompletion = client.chat().completions().create(params).validate();
            // Assuming the first choice is desired
            return chatCompletion.choices().get(0).message().content().orElse("").trim();
        } catch (Exception e) {
            throw new OpenAIAPIError("OpenAI API error: " + e.getMessage(), e);
        }
    }
}