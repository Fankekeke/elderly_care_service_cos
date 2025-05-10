package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 课程信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CourseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程编号
     */
    private String code;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 观看次数
     */
    private Integer visitNum;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 课程内容
     */
    private String content;

    /**
     * 视频地址
     */
    private String fileUrl;


}
