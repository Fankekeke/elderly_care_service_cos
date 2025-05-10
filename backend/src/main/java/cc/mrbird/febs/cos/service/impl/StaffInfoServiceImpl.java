package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.StaffInfoMapper;
import cc.mrbird.febs.cos.service.IBulletinInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StaffInfoServiceImpl extends ServiceImpl<StaffInfoMapper, StaffInfo> implements IStaffInfoService {

    private final StaffInfoMapper staffInfoService;

    private final IBulletinInfoService bulletinInfoService;

    /**
     * 分页获取会员信息
     *
     * @param page          分页对象
     * @param staffInfo 会员信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectStaffPage(Page<StaffInfo> page, StaffInfo staffInfo) {
        return baseMapper.selectStaffPage(page, staffInfo);
    }

    /**
     * 查询用户信息详情【公告信息】
     *
     * @param userId 主键ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectBulletinDetail(Integer userId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("user", null);
                put("bulletin", Collections.emptyList());
            }
        };
        StaffInfo userInfo = this.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, userId));
        if (userInfo == null) {
            return result;
        }
        result.put("user", userInfo);

        // 公告信息
        List<BulletinInfo> bulletinInfoList = bulletinInfoService.list();
        result.put("bulletin", bulletinInfoList);
        return result;
    }

    /**
     * 查询会员信息
     *
     * @param enterpriseId 会员id
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectStaffList(Integer enterpriseId) {
        return baseMapper.selectStaffList(enterpriseId);
    }

    /**
     * 获取会员列表
     *
     * @param enterpriseId 会员ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryStaffListByStaff(Integer enterpriseId, Integer userId) {
        return baseMapper.selectStaffListUser(enterpriseId, userId);
    }

    /**
     * 根据用户id查询会员信息
     *
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> queryStaffByUserId(Integer userId) {
        // 获取会员信息
        StaffInfo staffInfo = staffInfoService.selectOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, userId));
//        if (staffInfo.getMemberLevel() != null) {
//            LevelRuleInfo levelRuleInfo = levelRuleInfoService.getById(staffInfo.getMemberLevel());
//            staffInfo.setLevelName(levelRuleInfo.getLevelName());
//        }
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("staff", staffInfo);
            }
        };
        return result;
    }

    /**
     * 查询会员信息
     *
     * @param ids ids
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectStaffListByUserIds(List<String> ids) {
        return baseMapper.selectStaffListByUserIds(ids);
    }
}
