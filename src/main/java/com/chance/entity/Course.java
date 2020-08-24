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

    @TableId("Cno")
    private String Cno;

    @TableField("Cname")
    private String Cname;


    @Override
    protected Serializable pkVal() {
        return this.Cno;
    }

}
