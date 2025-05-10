package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.AlertInfo;
import cc.mrbird.febs.cos.dao.AlertInfoMapper;
import cc.mrbird.febs.cos.service.IAlertInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class AlertInfoServiceImpl extends ServiceImpl<AlertInfoMapper, AlertInfo> implements IAlertInfoService {

    /**
     * 分页获取报警信息
     *
     * @param page      分页对象
     * @param alertInfo 报警信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryAlertPage(Page<AlertInfo> page, AlertInfo alertInfo) {
        return baseMapper.queryAlertPage(page, alertInfo);
    }
}
