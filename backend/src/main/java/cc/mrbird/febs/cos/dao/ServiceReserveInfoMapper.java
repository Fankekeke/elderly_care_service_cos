package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ServiceReserveInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface ServiceReserveInfoMapper extends BaseMapper<ServiceReserveInfo> {

    /**
     * 分页获取服务预约信息
     *
     * @param page               分页对象
     * @param serviceReserveInfo 服务预约信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> querySerciceReservePage(Page<ServiceReserveInfo> page, @Param("serviceReserveInfo") ServiceReserveInfo serviceReserveInfo);

    /**
     * 获取服务预约详情
     *
     * @param id ID
     * @return 结果
     */
    LinkedHashMap<String, Object> getDetail(@Param("id") Integer id);

    /**
     * 分页获取服务预约信息
     *
     * @param page               分页对象
     * @param serviceReserveInfo 服务预约信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryOwnerServicePage(Page<ServiceReserveInfo> page, @Param("serviceReserveInfo") ServiceReserveInfo serviceReserveInfo);

    /**
     * 分页获取服务预约信息
     *
     * @param page               分页对象
     * @param serviceReserveInfo 服务预约信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryWorkerServicePage(Page<ServiceReserveInfo> page, @Param("serviceReserveInfo") ServiceReserveInfo serviceReserveInfo);

    /**
     * 获取未接单的订单
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryNotCheckOrder(@Param("userId") Integer userId, @Param("key") String key);
}
