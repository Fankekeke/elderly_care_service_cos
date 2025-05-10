package cc.mrbird.febs.cos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 聊天记录
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatRecordInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 发送人
     */
    private Integer sendUser;

    /**
     * 接收人
     */
    private Integer takeUser;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送时间
     */
    private String createDate;

    /**
     * 状态 0.未读 1.已读
     */
    private Integer taskStatus;

    @TableField(exist = false)
    private Integer sendUserName;

    @TableField(exist = false)
    private Integer takeUserName;

}
