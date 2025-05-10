package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ServiceReserveInfo;
import cc.mrbird.febs.cos.entity.AlertInfo;
import cc.mrbird.febs.cos.service.IAlertInfoService;
import cc.mrbird.febs.cos.service.IServiceReserveInfoService;
import cc.mrbird.febs.system.service.UserService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/alert-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlertInfoController {

    private final IAlertInfoService alertInfoService;

    /**
     * 分页获取报警信息
     *
     * @param page      分页对象
     * @param alertInfo 报警信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<AlertInfo> page, AlertInfo alertInfo) {
        return R.ok(alertInfoService.queryAlertPage(page, alertInfo));
    }

    /**
     * 获取报警信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(alertInfoService.list());
    }

    /**
     * 新增报警信息
     *
     * @param alertInfo 报警信息
     * @return 结果
     */
    @PostMapping
    public R save(AlertInfo alertInfo) throws Exception {
        alertInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(true);
    }

    /**
     * 修改报警信息
     *
     * @param alertInfo 报警信息
     * @return 结果
     */
    @PutMapping
    public R edit(AlertInfo alertInfo) {
        return R.ok(alertInfoService.updateById(alertInfo));
    }

    /**
     * 删除报警信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(alertInfoService.removeByIds(ids));
    }
}
