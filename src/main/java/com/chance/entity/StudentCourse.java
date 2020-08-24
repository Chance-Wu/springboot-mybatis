package com.chance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class StudentCourse extends Model<StudentCourse> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("Sno")
    private String Sno;

    @TableField("Cno")
    private String Cno;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
