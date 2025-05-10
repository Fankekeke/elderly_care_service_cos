package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CourseInfo;
import cc.mrbird.febs.cos.service.ICourseInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/course-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseInfoController {

    private final ICourseInfoService courseInfoService;

    /**
     * 获取所有物品类别
     *
     * @return
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(courseInfoService.list());
    }

    /**
     * 分页查询课程信息
     *
     * @param page
     * @param courseInfo
     * @return
     */
    @GetMapping("/page")
    public R page(Page<CourseInfo> page, CourseInfo courseInfo) {
        return R.ok(courseInfoService.queryCoursePage(page, courseInfo));
    }

    /**
     * 添加课程信息
     *
     * @param courseInfo
     * @return
     */
    @PostMapping
    public R save(CourseInfo courseInfo) {
        courseInfo.setCode("COURSE-" + System.currentTimeMillis());
        courseInfo.setVisitNum(0);
        courseInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(courseInfoService.save(courseInfo));
    }

    /**
     * 修改课程信息
     *
     * @param courseInfo
     * @return
     */
    @PutMapping
    public R edit(CourseInfo courseInfo) {
        return R.ok(courseInfoService.updateById(courseInfo));
    }

    /**
     * 删除课程信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(courseInfoService.removeByIds(ids));
    }

}
