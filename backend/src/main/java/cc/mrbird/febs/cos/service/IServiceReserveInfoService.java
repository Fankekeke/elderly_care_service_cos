package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ServiceReserveInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IServiceReserveInfoService extends IService<ServiceReserveInfo> {

    /**
     * 分页获取服务预约信息
     *
     * @param page               分页对象
     * @param serviceReserveInfo 服务预约信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> querySerciceReservePage(Page<ServiceReserveInfo> page, ServiceReserveInfo serviceReserveInfo);

    /**
     * 获取服务预约详情
     *
     * @param id ID
     * @return 结果
     */
    LinkedHashMap<String, Object> getDetail(Integer id);

    /**
     * 分页获取服务预约信息
     *
     * @param page               分页对象
     * @param serviceReserveInfo 服务预约信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryOwnerServicePage(Page<ServiceReserveInfo> page, ServiceReserveInfo serviceReserveInfo);

    /**
     * 分页获取服务预约信息
     *
     * @param page               分页对象
     * @param serviceReserveInfo 服务预约信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryWorkerServicePage(Page<ServiceReserveInfo> page, ServiceReserveInfo serviceReserveInfo);

    /**
     * 获取未接单的订单
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryNotCheckOrder(Integer userId, String key);

    /**
     * 作业人员接单
     *
     * @param workId  作业人员ID
     * @param orderId 订单ID
     * @return 结果
     */
    boolean workOrderCheck(Integer workId, Integer orderId);
}
