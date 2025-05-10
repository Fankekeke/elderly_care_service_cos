package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.StaffInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IStaffInfoService extends IService<StaffInfo> {

    /**
     * 分页获取会员信息
     *
     * @param page          分页对象
     * @param staffInfo 会员信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectStaffPage(Page<StaffInfo> page, StaffInfo staffInfo);

    /**
     * 查询用户信息详情【公告信息】
     *
     * @param userId 主键ID
     * @return 结果
     */
    LinkedHashMap<String, Object> selectBulletinDetail(Integer userId);

    /**
     * 查询会员信息
     *
     * @param enterpriseId 会员id
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectStaffList(Integer enterpriseId);

    /**
     * 获取会员列表
     *
     * @param enterpriseId 会员ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryStaffListByStaff(Integer enterpriseId, Integer userId);

    /**
     * 根据用户id查询会员信息
     *
     * @param userId 用户id
     * @return 结果
     */
    LinkedHashMap<String, Object> queryStaffByUserId(Integer userId);

    /**
     * 查询会员信息
     *
     * @param ids ids
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectStaffListByUserIds(List<String> ids);
}
