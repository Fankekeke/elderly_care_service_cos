package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ChatRecordInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 聊天记录 service层
 *
 * @author FanK
 */
public interface IChatRecordInfoService extends IService<ChatRecordInfo> {

    /**
     * 分页获取聊天记录
     *
     * @param page           分页对象
     * @param chatRecordInfo 聊天记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectChatRecordPage(Page<ChatRecordInfo> page, ChatRecordInfo chatRecordInfo);

    /**
     * 查询消息信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> messageListById(Integer userId);

    /**
     * 查找聊天记录
     *
     * @param takeUser 发送者
     * @param sendUser 接收人
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> getMessageDetail(Integer takeUser, Integer sendUser);
}
