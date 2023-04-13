package com.psyduck.bot.gpt.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * GPT工具类
 * @Author psyduck
 * @Date 2023/4/13 14.48
 * @link <a href="https://github.com/yjrguxing/openai-java">openai-gpt-java-client github doc</a>
 */
public class GPTUtils {

    /**
     * OpenAI API Key
     */
    private final String OPENAI_KEY;

    public GPTUtils(@Value("${openai.api-key}")String openaiKey) {
        OPENAI_KEY = openaiKey;
    }

}
