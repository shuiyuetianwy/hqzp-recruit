package com.hqzp.recruit.ai.service.impl;

import com.hqzp.recruit.ai.client.DeepSeekClient;
import com.hqzp.recruit.ai.config.DeepSeekProperties;
import com.hqzp.recruit.ai.dto.AiResult;
import com.hqzp.recruit.ai.dto.ChatMessage;
import com.hqzp.recruit.ai.dto.ChatRequest;
import com.hqzp.recruit.ai.dto.ChatResponse;
import com.hqzp.recruit.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final DeepSeekClient client;
    private final DeepSeekProperties props;

    @Override
    public AiResult chat(List<ChatMessage> messages) {
        return execute(messages);
    }

    @Override
    public AiResult analyseResume(String resumeText) {
        List<ChatMessage> messages = Arrays.asList(
                ChatMessage.system(
                        "你是一位专业的HR顾问和简历评估专家。请对候选人的简历进行全面评估，" +
                        "以JSON格式返回结果，包含以下字段：\n" +
                        "- score: 综合评分(0-100)\n" +
                        "- strengths: 优势亮点数组\n" +
                        "- weaknesses: 不足之处数组\n" +
                        "- suggestions: 改进建议数组\n" +
                        "- summary: 总体评价(100字以内)\n" +
                        "只返回JSON，不要有其他文字。"
                ),
                ChatMessage.user("请评估以下简历：\n\n" + resumeText)
        );
        return execute(messages);
    }

    @Override
    public AiResult matchJobResume(String jobDescription, String resumeText) {
        List<ChatMessage> messages = Arrays.asList(
                ChatMessage.system(
                        "你是一位专业的招聘顾问。请分析候选人简历与职位描述的匹配程度，" +
                        "以JSON格式返回结果，包含以下字段：\n" +
                        "- score: 匹配分数(0-100)\n" +
                        "- matchPoints: 匹配点数组（候选人符合要求的方面）\n" +
                        "- gapPoints: 差距点数组（候选人不符合要求的方面）\n" +
                        "- recommendation: 录用建议(强烈推荐/推荐/一般/不推荐)\n" +
                        "- reason: 推荐理由(100字以内)\n" +
                        "只返回JSON，不要有其他文字。"
                ),
                ChatMessage.user(
                        "职位描述：\n" + jobDescription +
                        "\n\n候选人简历：\n" + resumeText
                )
        );
        return execute(messages);
    }

    @Override
    public AiResult generateInterviewQuestions(String jobDescription, String resumeText, int count) {
        List<ChatMessage> messages = Arrays.asList(
                ChatMessage.system(
                        "你是一位经验丰富的面试官。请根据职位描述和候选人简历，生成有针对性的面试问题，" +
                        "以JSON格式返回，包含以下字段：\n" +
                        "- questions: 问题数组，每个问题包含：\n" +
                        "  - question: 问题内容\n" +
                        "  - type: 问题类型(技术/行为/情景/综合)\n" +
                        "  - difficulty: 难度(简单/中等/困难)\n" +
                        "  - keyPoints: 考察要点数组\n" +
                        "只返回JSON，不要有其他文字。"
                ),
                ChatMessage.user(
                        "请生成" + count + "道面试题。\n\n" +
                        "职位描述：\n" + jobDescription +
                        "\n\n候选人简历：\n" + resumeText
                )
        );
        return execute(messages);
    }

    @Override
    public AiResult careerAdvice(String resumeText, String targetJob) {
        List<ChatMessage> messages = Arrays.asList(
                ChatMessage.system(
                        "你是一位专业的职业发展顾问。请根据候选人的简历和目标职位，" +
                        "提供具体可行的职业发展建议，包括技能提升、经验积累、求职策略等方面。" +
                        "以JSON格式返回，包含：\n" +
                        "- skillGaps: 需要补充的技能数组\n" +
                        "- learningPath: 学习路径建议数组\n" +
                        "- shortTermGoals: 短期目标(3-6个月)数组\n" +
                        "- longTermGoals: 长期目标(1-3年)数组\n" +
                        "- jobSearchTips: 求职建议数组\n" +
                        "只返回JSON，不要有其他文字。"
                ),
                ChatMessage.user(
                        "目标职位：" + targetJob +
                        "\n\n我的简历：\n" + resumeText
                )
        );
        return execute(messages);
    }

    @Override
    public AiResult optimiseResumeSection(String section, String content) {
        List<ChatMessage> messages = Arrays.asList(
                ChatMessage.system(
                        "你是一位专业的简历优化顾问。请优化候选人简历中的指定部分，" +
                        "使其更加专业、有力、吸引HR注意。" +
                        "直接返回优化后的文本内容，不要有额外说明。"
                ),
                ChatMessage.user(
                        "请优化简历中的【" + section + "】部分：\n\n" + content
                )
        );
        return execute(messages);
    }

    // -------------------------------------------------------
    // Internal
    // -------------------------------------------------------

    private AiResult execute(List<ChatMessage> messages) {
        ChatRequest request = ChatRequest.builder()
                .model(props.getModel())
                .messages(messages)
                .maxTokens(props.getMaxTokens())
                .temperature(props.getTemperature())
                .stream(false)
                .build();

        long start = System.currentTimeMillis();
        ChatResponse response = client.chat(request);
        long elapsed = System.currentTimeMillis() - start;

        ChatResponse.Usage usage = response.getUsage();
        return AiResult.builder()
                .content(response.firstContent())
                .model(response.getModel())
                .promptTokens(usage != null ? usage.getPromptTokens() : 0)
                .completionTokens(usage != null ? usage.getCompletionTokens() : 0)
                .totalTokens(usage != null ? usage.getTotalTokens() : 0)
                .elapsedMs(elapsed)
                .build();
    }
}
