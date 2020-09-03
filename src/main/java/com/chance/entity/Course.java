package com.chance.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Course extends Model<Course> {

    private static final long serialVersionUID = 1L;

    @TableId("c_no")
    private String cNo;

    @TableField("c_name")
    private String cName;


    @Override
    protected Serializable pkVal() {
        return this.cNo;
    }

}
