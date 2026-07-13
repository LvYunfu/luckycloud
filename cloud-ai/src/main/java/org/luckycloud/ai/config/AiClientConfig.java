package org.luckycloud.ai.config;

import lombok.Data;
import lombok.Setter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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

    private Image image;

    @Data
    public static class Chat {
        private Options options = new Options();
    }

    @Data
    public static class Image {
        private ImageOptions options = new ImageOptions();
    }

    // 内部静态类映射 chat.options 节点
    @Data
    public static class Options {
        private String model;
        private Double temperature;
        private Integer maxTokens;
    }

    // 内部静态类映射 image.options 节点
    @Data
    public static class ImageOptions {
        private String model;
        private Integer width;
        private Integer height;
        private String quality;
        private String style;
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

    @Bean
    public ImageModel imageModel() {
        // 构建带认证的 RestClient
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor((request, body, execution) -> {
                    request.getHeaders().setBearerAuth(apiKey);
                    return execution.execute(request, body);
                })
                .build();
        OpenAiImageApi imageApi = new OpenAiImageApi(restClient, "/api/v3/images/generations");
        return new OpenAiImageModel(imageApi);
    }

}
