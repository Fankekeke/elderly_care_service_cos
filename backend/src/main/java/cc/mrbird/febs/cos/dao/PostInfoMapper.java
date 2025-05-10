package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.PostInfo;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author FanK
 */
public interface PostInfoMapper extends BaseMapper<PostInfo> {

    /**
     * 分页获取系统用户信息
     * @param page
     * @param user
     * @return
     */
    IPage<LinkedHashMap<String, Object>> selectUserPage(Page page, @Param("user") User user);

    // 分页获取帖子信息
    IPage<LinkedHashMap<String, Object>> postInfoByPage(Page page, @Param("postInfo") PostInfo postInfo);

    // 获取模块下的贴子
    List<LinkedHashMap<String, Object>> getPostByTag(@Param("tagId") Integer tagId);

    // 获取贴子详细信息
    LinkedHashMap<String, Object> postDetail(@Param("postId") Integer postId);

    // 模糊查询帖子信息
    List<LinkedHashMap<String, Object>> postByKey(@Param("key") String key);

    // 推荐贴子
    List<LinkedHashMap<String, Object>> recommend(@Param("tagId") Integer tagId, @Param("collectUserIds") String collectUserIds);

    /**
     * 根据用户获取贴子信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> getPostByUser(@Param("userId") Integer userId);

    /**
     * 根据贴子ID获取回复信息
     *
     * @param postId 贴子ID
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> replyListByPostId(@Param("postId") Integer postId);

    /**
     * 根据贴子编号获取详细信息
     *
     * @param postId 帖子ID
     * @return 结果
     */
    LinkedHashMap<String, Object> getPostInfoById(@Param("postId") Integer postId);

    /**
     * 获取贴子消息
     *
     * @param postInfo 贴子消息
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectPostList(@Param("postInfo") PostInfo postInfo);
}
