package com.hqzp.recruit.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * DeepSeek API configuration, bound from {@code deepseek.*} properties.
 */
@Data
@Component
@ConfigurationProperties(prefix = "deepseek")
public class DeepSeekProperties {

    /** DeepSeek API key. */
    private String apiKey;

    /** API base URL, e.g. https://api.deepseek.com */
    private String baseUrl = "https://api.deepseek.com";

    /** Model name, e.g. deepseek-chat or deepseek-reasoner. */
    private String model = "deepseek-chat";

    /** HTTP read/write timeout in seconds. */
    private int timeoutSeconds = 60;

    /** Maximum tokens in the completion. */
    private int maxTokens = 4096;

    /** Temperature (0.0–2.0). */
    private double temperature = 0.7;
}
