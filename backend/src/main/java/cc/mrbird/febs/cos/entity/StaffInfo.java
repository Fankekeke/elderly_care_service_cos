package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 会员管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StaffInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 会员编号
     */
    private String code;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 籍贯
     */
    private String nativeAddress;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 头像
     */
    private String images;

    /**
     * 学历
     */
    private String diploma;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 邮箱地址
     */
    private String mail;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 删除标识
     */
    @TableLogic
    private String delFlag;

    /**
     * 所属部门
     */
    private Integer deptId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 所属会员
     */
    private Integer enterpriseId;

    /**
     * 岗位管理
     */
    private Integer positionId;

    private BigDecimal integral;

    @TableField(exist = false)
    private String levelName;


}
