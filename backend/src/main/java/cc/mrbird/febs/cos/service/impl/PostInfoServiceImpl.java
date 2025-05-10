package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.FocusInfoMapper;
import cc.mrbird.febs.cos.dao.UserInfoMapper;
import cc.mrbird.febs.cos.entity.FocusInfo;
import cc.mrbird.febs.cos.entity.PostInfo;
import cc.mrbird.febs.cos.dao.PostInfoMapper;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IPostInfoService;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
public class PostInfoServiceImpl extends ServiceImpl<PostInfoMapper, PostInfo> implements IPostInfoService {

    private final FocusInfoMapper focusInfoMapper;
    private final UserInfoMapper userInfoMapper;

    /**
     * 分页获取系统用户信息
     * @param page
     * @param user
     * @return
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectUserPage(Page page, User user) {
        return baseMapper.selectUserPage(page, user);
    }

    @Override
    public IPage<LinkedHashMap<String, Object>> postInfoByPage(Page page, PostInfo postInfo) {
        return baseMapper.postInfoByPage(page, postInfo);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getPostByTag(Integer tagId) {
        return baseMapper.getPostByTag(tagId);
    }

    @Override
    public LinkedHashMap<String, Object> postDetail(Integer postId) {
        return baseMapper.postDetail(postId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> postByKey(String key) {
        return baseMapper.postByKey(key);
    }

    @Override
    public List<LinkedHashMap<String, Object>> recommend(Integer tagId, List<Long> collectUserIds) {
        return baseMapper.recommend(tagId, StringUtils.join(collectUserIds.toArray(), ","));
    }

    /**
     * 根据用户获取贴子信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> getPostByUser(Integer userId) {
        return baseMapper.getPostByUser(userId);
    }

    /**
     * 根据贴子编号获取详细信息
     *
     * @param postId 帖子ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> getPostInfoById(Integer postId) {
        PostInfo postInfo = this.getById(postId);
        postInfo.setPageviews(postInfo.getPageviews() + 1);
        this.updateById(postInfo);
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("postInfo", baseMapper.getPostInfoById(postId));
        result.put("replyInfo", baseMapper.replyListByPostId(postId));
        return result;
    }

    /**
     * 查询帖子及用户信息
     *
     * @param key 关键字
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> querySearch(String key) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("post", Collections.emptyList());
                put("user", Collections.emptyList());
            }
        };
        // 帖子
        PostInfo postInfo = new PostInfo();
        postInfo.setTitle(key);
        result.put("post", baseMapper.selectPostList(postInfo));
        // 用户
        UserInfo userInfo = new UserInfo();
        userInfo.setName(key);
        result.put("user", userInfoMapper.selectUserList(userInfo));
        return result;
    }

    /**
     * 获取用户及贴子详细信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> getUserPostDetail(Integer userId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("user", null);
                put("post", Collections.emptyList());
            }
        };

        // 用户信息
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        //关注与粉丝
        List<FocusInfo> focusInfoList1 = focusInfoMapper.selectList(Wrappers.<FocusInfo>lambdaQuery().eq(FocusInfo::getUserId, userId));
        List<FocusInfo> focusInfoList2 = focusInfoMapper.selectList(Wrappers.<FocusInfo>lambdaQuery().eq(FocusInfo::getCollectUserId, userId));
        userInfo.setFocusNum(CollectionUtil.isEmpty(focusInfoList1) ? 0 : focusInfoList1.size());
        userInfo.setFansNum(CollectionUtil.isEmpty(focusInfoList2) ? 0 : focusInfoList2.size());
        result.put("user", userInfo);
        // 帖子
        result.put("post", baseMapper.getPostByUser(userId));
        return result;
    }
}
