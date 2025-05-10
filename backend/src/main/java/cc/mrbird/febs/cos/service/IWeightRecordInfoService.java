package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.WeightRecordInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IWeightRecordInfoService extends IService<WeightRecordInfo> {

    /**
     * 分页查询健康记录信息
     *
     * @param page             分页对象
     * @param weightRecordInfo 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryWeightPage(Page<WeightRecordInfo> page, WeightRecordInfo weightRecordInfo);

    /**
     * 获取数据统计
     *
     * @param userId 用户ID
     * @return 结果
     */
    LinkedHashMap<String, Object> selectRateWithDays(Integer userId);
}
