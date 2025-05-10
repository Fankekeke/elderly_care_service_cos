package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.ChatRecordInfoMapper;
import cc.mrbird.febs.cos.entity.ChatRecordInfo;
import cc.mrbird.febs.cos.service.IChatRecordInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 聊天记录 实现层
 *
 * @author FanK
 */
@Service
public class ChatRecordInfoServiceImpl extends ServiceImpl<ChatRecordInfoMapper, ChatRecordInfo> implements IChatRecordInfoService {

    /**
     * 分页获取聊天记录
     *
     * @param page           分页对象
     * @param chatRecordInfo 聊天记录
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectChatRecordPage(Page<ChatRecordInfo> page, ChatRecordInfo chatRecordInfo) {
        return baseMapper.selectChatRecordPage(page, chatRecordInfo);
    }

    /**
     * 查询消息信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> messageListById(Integer userId) {
        return baseMapper.messageListById(userId);
    }

    /**
     * 查找聊天记录
     *
     * @param takeUser 发送者
     * @param sendUser 接收人
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> getMessageDetail(Integer takeUser, Integer sendUser) {
        return baseMapper.getMessageDetail(takeUser, sendUser);
    }
}
