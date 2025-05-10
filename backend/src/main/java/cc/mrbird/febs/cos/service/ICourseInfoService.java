package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.CourseInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ICourseInfoService extends IService<CourseInfo> {

    /**
     * 分页查询课程信息
     *
     * @param page
     * @param courseInfo
     * @return
     */
    IPage<LinkedHashMap<String, Object>> queryCoursePage(Page<CourseInfo> page, CourseInfo courseInfo);
}
