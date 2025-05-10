package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.AlertInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IAlertInfoService extends IService<AlertInfo> {

    /**
     * 分页获取报警信息
     *
     * @param page      分页对象
     * @param alertInfo 报警信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryAlertPage(Page<AlertInfo> page, AlertInfo alertInfo);
}
