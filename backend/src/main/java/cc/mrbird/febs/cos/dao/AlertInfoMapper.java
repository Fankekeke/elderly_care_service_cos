package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.AlertInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface AlertInfoMapper extends BaseMapper<AlertInfo> {

    /**
     * 分页获取报警信息
     *
     * @param page      分页对象
     * @param alertInfo 报警信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryAlertPage(Page<AlertInfo> page, @Param("alertInfo") AlertInfo alertInfo);
}
