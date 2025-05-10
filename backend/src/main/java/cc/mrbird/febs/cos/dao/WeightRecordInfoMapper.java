package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.WeightRecordInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface WeightRecordInfoMapper extends BaseMapper<WeightRecordInfo> {

    /**
     * 分页查询健康记录信息
     *
     * @param page             分页对象
     * @param weightRecordInfo 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryWeightPage(Page<WeightRecordInfo> page, @Param("weightRecordInfo") WeightRecordInfo weightRecordInfo);

    /**
     * 十天内卡路里摄入统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectCaloriesWithinDays(@Param("userId") Integer userId);

    /**
     * 十天内卡路里消耗统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectCaloriesWithoutDays(@Param("userId") Integer userId);

    /**
     * 十天内健康统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectWeightWithinDays(@Param("userId") Integer userId);
}
