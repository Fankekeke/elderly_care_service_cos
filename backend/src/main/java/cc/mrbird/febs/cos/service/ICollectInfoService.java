package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.CollectInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface ICollectInfoService extends IService<CollectInfo> {

    // 获取收藏贴子列表
    IPage<LinkedHashMap<String, Object>> collectInfoByPage(Page page, CollectInfo collectInfo);

    // 获取收藏贴子列表
    List<LinkedHashMap<String, Object>> collectInfoByUser(Integer userId);

    /**
     * 根据用户获取收藏列表
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectCollectByUser(Integer userId);

    /**
     * 收藏/取消 贴子
     *
     * @param userId 用户ID
     * @param postId 贴子ID
     * @param type   操作 1.关注 2.取关
     * @return 结果
     */
    Boolean collectPost(Integer userId, Integer postId, Integer type);
}
