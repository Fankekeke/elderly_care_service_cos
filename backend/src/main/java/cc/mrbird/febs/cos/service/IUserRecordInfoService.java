package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.UserRecordInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 用户访问历史 service层
 *
 * @author FanK
 */
public interface IUserRecordInfoService extends IService<UserRecordInfo> {

    /**
     * 分页获取用户访问历史
     *
     * @param page           分页对象
     * @param userRecordInfo 用户访问历史
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRecordPage(Page<UserRecordInfo> page, UserRecordInfo userRecordInfo);

    /**
     * 根据用户ID获取历史访问记录
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> queryHistoryByUserId(Integer userId);
}
