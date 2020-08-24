package com.chance.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;

    private String sNo;

    private String name;


    @Override
    protected Serializable pkVal() {
        return this.sNo;
    }

}
