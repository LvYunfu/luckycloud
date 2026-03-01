package org.luckycloud.ai.config;

import lombok.Data;
import lombok.Setter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lvyf
 * @description:
 * @date 2026/2/28
 */

@Configuration
@Component
@Setter
@ConfigurationProperties(prefix = "spring.ai.openai")
public class AiClientConfig {


    private String apiKey;
    private String baseUrl;

    private Chat chat;

    @Data
    public static class Chat {
        private Options options = new Options();
    }

    // 内部静态类映射 options 节点
    @Data
    public static class Options {
        private String model;
        private Double temperature;
        private Integer maxTokens;
    }

    @Bean
    public OpenAiChatModel openAiChatModel() {
        return OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder().apiKey(apiKey).baseUrl(baseUrl).completionsPath("/api/v3/chat/completions").build())
                .defaultOptions(OpenAiChatOptions.builder().model(chat.getOptions().getModel()).build())
                .build();
    }

    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel) {
        return ChatClient.create(openAiChatModel);
    }

}
