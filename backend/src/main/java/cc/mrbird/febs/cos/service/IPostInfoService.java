package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.PostInfo;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IPostInfoService extends IService<PostInfo> {

    /**
     * 分页获取系统用户信息
     * @param page
     * @param user
     * @return
     */
    IPage<LinkedHashMap<String, Object>> selectUserPage(Page page, User user);

    // 分页获取帖子信息
    IPage<LinkedHashMap<String, Object>> postInfoByPage(Page page, PostInfo postInfo);

    // 获取模块下的贴子
    List<LinkedHashMap<String, Object>> getPostByTag(Integer tagId);

    // 获取贴子详细信息
    LinkedHashMap<String, Object> postDetail(Integer postId);

    // 模糊查询帖子信息
    List<LinkedHashMap<String, Object>> postByKey(String key);

    // 推荐贴子
    List<LinkedHashMap<String, Object>> recommend(Integer tagId, List<Long> collectUserIds);

    /**
     * 根据用户获取贴子信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> getPostByUser(Integer userId);

    /**
     * 根据贴子编号获取详细信息
     *
     * @param postId 帖子ID
     * @return 结果
     */
    LinkedHashMap<String, Object> getPostInfoById(Integer postId);

    /**
     * 查询帖子及用户信息
     *
     * @param key 关键字
     * @return 结果
     */
    LinkedHashMap<String, Object> querySearch(String key);

    /**
     * 获取用户及贴子详细信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    LinkedHashMap<String, Object> getUserPostDetail(Integer userId);
}
