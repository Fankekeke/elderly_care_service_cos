package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CourseInfo;
import cc.mrbird.febs.cos.dao.CourseInfoMapper;
import cc.mrbird.febs.cos.service.ICourseInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoMapper, CourseInfo> implements ICourseInfoService {

    /**
     * 分页查询课程信息
     *
     * @param page
     * @param courseInfo
     * @return
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryCoursePage(Page<CourseInfo> page, CourseInfo courseInfo) {
        return baseMapper.queryCoursePage(page, courseInfo);
    }
}
