package cc.mrbird.febs.cos.controller;

import cc.mrbird.febs.common.utils.R;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationOutput;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/cos/ai/")
public class AliAliController {

    @Resource
    private Generation generation;

    /**
     * 测试demo
     *
     * @param content 用书输入文本内容
     */
    @PostMapping(value = "aliTyqw")
    public R send(@RequestBody String content) throws NoApiKeyException, InputRequiredException {
        //用户与模型的对话历史。list中的每个元素形式为{“role”:角色, “content”: 内容}。
        Message userMessage = Message.builder()
                .role(Role.USER.getValue())
                .content(content)
                .build();

        GenerationParam param = GenerationParam.builder()
                //指定用于对话的通义千问模型名
                .model("qwen-plus")
                .messages(Arrays.asList(userMessage))
                //
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                //生成过程中核采样方法概率阈值，例如，取值为0.8时，仅保留概率加起来大于等于0.8的最可能token的最小集合作为候选集。
                // 取值范围为（0,1.0)，取值越大，生成的随机性越高；取值越低，生成的确定性越高。
                .topP(0.8)
                //阿里云控制台DASHSCOPE获取的api-key
                .apiKey("sk-fkebb4821588054a66aa1951d7f239f77c")
                //启用互联网搜索，模型会将搜索结果作为文本生成过程中的参考信息，但模型会基于其内部逻辑“自行判断”是否使用互联网搜索结果。
                .enableSearch(true)
                .build();
        GenerationResult generationResult = generation.call(param);
//        String json = generationResult.getOutput().getChoices().get(0).getMessage().getContent();
        // 获取所有 content 内容并放入 List 中
        List<String> allContents = generationResult.getOutput().getChoices().stream()
                .map(choice -> choice.getMessage().getContent())
                .collect(Collectors.toList());

        String combinedContent = String.join("\n---\n", allContents); // 使用 "---" 分隔多个回复内容
        return R.ok(combinedContent);
    }

    @PostMapping(value = "recognitionImage")
    public R recognitionImage(@RequestParam("avatar") String imageBase64) throws NoApiKeyException, InputRequiredException {
        try {
//            String imageBase64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(file.getBytes());
            MultiModalMessage userMessage = MultiModalMessage.builder().role(Role.USER.getValue())
                    .content(Arrays.asList(new HashMap<String, Object>(){{put("image", imageBase64);}},
                            new HashMap<String, Object>(){{put("text", "图中有几个人，20字以内。");}})).build();

            MultiModalConversationParam param = MultiModalConversationParam.builder()
                    .model(MultiModalConversation.Models.QWEN_VL_PLUS)
                    .message(userMessage)
                    .apiKey("sk-fkebb4821588054a66aa1951d7f239f77c")
                    .build();
            MultiModalConversation conv = new MultiModalConversation();
            MultiModalConversationResult result = conv.call(param);
            MultiModalConversationOutput.Choice ss = result.getOutput().getChoices().get(0);
            String combinedContent = ss.getMessage().getContent().get(0).get("text").toString(); // 合并多个回复内容
            return R.ok(combinedContent);

        } catch (Exception e) {
            System.err.println("API 调用失败: " + e.getMessage());
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

}