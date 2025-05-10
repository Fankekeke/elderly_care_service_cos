package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 报警信息
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AlertInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 详细位置
     */
    private String address;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 经度
     */
    private BigDecimal latitude;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 状态（0.未处理 1.已处理）
     */
    private String status;

    @TableField(exist = false)
    private String userName;


}
