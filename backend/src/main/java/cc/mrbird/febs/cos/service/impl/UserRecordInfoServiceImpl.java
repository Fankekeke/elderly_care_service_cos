package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.UserRecordInfoMapper;
import cc.mrbird.febs.cos.entity.UserRecordInfo;
import cc.mrbird.febs.cos.service.IUserRecordInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 用户访问历史 实现层
 *
 * @author FanK
 */
@Service
public class UserRecordInfoServiceImpl extends ServiceImpl<UserRecordInfoMapper, UserRecordInfo> implements IUserRecordInfoService {

    /**
     * 分页获取用户访问历史
     *
     * @param page           分页对象
     * @param userRecordInfo 用户访问历史
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<UserRecordInfo> page, UserRecordInfo userRecordInfo) {
        return baseMapper.selectRecordPage(page, userRecordInfo);
    }

    /**
     * 根据用户ID获取历史访问记录
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryHistoryByUserId(Integer userId) {
        return baseMapper.queryHistoryByUserId(userId);
    }
}
