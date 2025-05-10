package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 获取用户信息
     *
     * @param userInfo 用户信息
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectUserList(@Param("userInfo") UserInfo userInfo);

    /**
     * 分页查询用户信息
     *
     * @param page     分页对象
     * @param userInfo 参数
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectUserPage(Page<UserInfo> page, @Param("userInfo")UserInfo userInfo);
}
