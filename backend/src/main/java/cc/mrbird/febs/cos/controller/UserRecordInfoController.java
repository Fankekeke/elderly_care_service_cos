package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.UserRecordInfo;
import cc.mrbird.febs.cos.service.IUserRecordInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户访问历史 控制层
 *
 * @author FanK
 */
@RestController
@RequestMapping("/cos/user-record-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRecordInfoController {

    private final IUserRecordInfoService userRecordInfoService;

    /**
     * 分页获取用户访问历史
     *
     * @param page           分页对象
     * @param userRecordInfo 用户访问历史
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<UserRecordInfo> page, UserRecordInfo userRecordInfo) {
        return R.ok(userRecordInfoService.selectRecordPage(page, userRecordInfo));
    }

    /**
     * 根据用户ID获取历史访问记录
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/queryHistoryByUserId/{userId}")
    public R queryHistoryByUserId(@PathVariable("userId") Integer userId) {
        return R.ok(userRecordInfoService.queryHistoryByUserId(userId));
    }

    /**
     * 查询用户访问历史 详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(userRecordInfoService.getById(id));
    }

    /**
     * 查询用户访问历史 列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(userRecordInfoService.list());
    }

    /**
     * 新增用户访问历史
     *
     * @param userRecordInfo 用户访问历史
     * @return 结果
     */
    @PostMapping
    public R save(UserRecordInfo userRecordInfo) {
        return R.ok(userRecordInfoService.save(userRecordInfo));
    }

    /**
     * 修改用户访问历史
     *
     * @param userRecordInfo 用户访问历史
     * @return 结果
     */
    @PutMapping
    public R edit(UserRecordInfo userRecordInfo) {
        return R.ok(userRecordInfoService.updateById(userRecordInfo));
    }

    /**
     * 删除用户访问历史
     *
     * @param ids ids
     * @return 用户访问历史
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(userRecordInfoService.removeByIds(ids));
    }
}
