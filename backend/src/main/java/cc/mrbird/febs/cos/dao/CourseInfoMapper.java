package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.CourseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface CourseInfoMapper extends BaseMapper<CourseInfo> {

    /**
     * 分页查询课程信息
     *
     * @param page
     * @param courseInfo
     * @return
     */
    IPage<LinkedHashMap<String, Object>> queryCoursePage(Page<CourseInfo> page, @Param("courseInfo") CourseInfo courseInfo);
}
