package com.hqzp.recruit.ai.client;

import com.alibaba.fastjson2.JSON;
import com.hqzp.recruit.ai.config.DeepSeekProperties;
import com.hqzp.recruit.ai.dto.ChatRequest;
import com.hqzp.recruit.ai.dto.ChatResponse;
import com.hqzp.recruit.common.exception.BusinessException;
import com.hqzp.recruit.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Low-level HTTP client for the DeepSeek Chat Completions API.
 * All higher-level AI logic lives in the service layer.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeepSeekClient {

    private static final MediaType JSON_MEDIA_TYPE =
            MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient httpClient;
    private final DeepSeekProperties props;

    /**
     * Sends a chat completion request and returns the parsed response.
     *
     * @throws BusinessException on HTTP error or network failure
     */
    public ChatResponse chat(ChatRequest request) {
        String url = props.getBaseUrl() + "/v1/chat/completions";
        String body = JSON.toJSONString(request);

        Request httpRequest = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + props.getApiKey())
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body, JSON_MEDIA_TYPE))
                .build();

        log.debug("DeepSeek request: model={} messages={}", request.getModel(),
                request.getMessages().size());

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "";
                log.error("DeepSeek API error: status={} body={}", response.code(), errorBody);
                if (response.code() == 429) {
                    throw new BusinessException(ResultCode.AI_QUOTA_EXCEEDED);
                }
                throw new BusinessException(ResultCode.AI_SERVICE_ERROR,
                        "DeepSeek API returned " + response.code());
            }

            String responseBody = response.body() != null ? response.body().string() : "";
            log.debug("DeepSeek response: {}", responseBody);
            return JSON.parseObject(responseBody, ChatResponse.class);

        } catch (IOException e) {
            log.error("DeepSeek network error", e);
            throw new BusinessException(ResultCode.AI_SERVICE_ERROR, "AI服务网络异常: " + e.getMessage());
        }
    }
}
