package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.entity.ServiceReserveInfo;
import cc.mrbird.febs.cos.dao.ServiceReserveInfoMapper;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import cc.mrbird.febs.cos.service.IServiceReserveInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceReserveInfoServiceImpl extends ServiceImpl<ServiceReserveInfoMapper, ServiceReserveInfo> implements IServiceReserveInfoService {

    private final IUserInfoService userInfoService;

    private final IMessageInfoService messageInfoService;

    private final IStaffInfoService staffInfoService;

    /**
     * 分页获取服务预约信息
     *
     * @param page               分页对象
     * @param serviceReserveInfo 服务预约信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> querySerciceReservePage(Page<ServiceReserveInfo> page, ServiceReserveInfo serviceReserveInfo) {
        return baseMapper.querySerciceReservePage(page, serviceReserveInfo);
    }

    /**
     * 获取服务预约详情
     *
     * @param id ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> getDetail(Integer id) {
        return baseMapper.getDetail(id);
    }

    /**
     * 分页获取服务预约信息
     *
     * @param page               分页对象
     * @param serviceReserveInfo 服务预约信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryOwnerServicePage(Page<ServiceReserveInfo> page, ServiceReserveInfo serviceReserveInfo) {
        return baseMapper.queryOwnerServicePage(page, serviceReserveInfo);
    }

    /**
     * 分页获取服务预约信息
     *
     * @param page               分页对象
     * @param serviceReserveInfo 服务预约信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryWorkerServicePage(Page<ServiceReserveInfo> page, ServiceReserveInfo serviceReserveInfo) {
        return baseMapper.queryWorkerServicePage(page, serviceReserveInfo);
    }

    /**
     * 获取未接单的订单
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryNotCheckOrder(Integer userId, String key) {
        return baseMapper.queryNotCheckOrder(userId, key);
    }

    /**
     * 作业人员接单
     *
     * @param workId  作业人员ID
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    public boolean workOrderCheck(Integer workId, Integer orderId) {
        // 获取订单信息
        ServiceReserveInfo serviceReserveInfo = baseMapper.selectById(orderId);
        // 获取工作人员
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, workId));
        serviceReserveInfo.setWorkUserId(staffInfo.getId());
        // 获取用户信息
        UserInfo user = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getId, serviceReserveInfo.getUserId()));
        // 设置订单状态
        serviceReserveInfo.setStatus("2");

        // 添加用户消息通知
        String message = "您发布的服务订单“"+serviceReserveInfo.getCode()+"” 已被接单，请等待作业人员联系。";
        MessageInfo messageInfo = new MessageInfo(Long.getLong(user.getUserId().toString()), message, DateUtil.formatDateTime(new Date()), 0);
        messageInfoService.save(messageInfo);

        return this.updateById(serviceReserveInfo);
    }
}
