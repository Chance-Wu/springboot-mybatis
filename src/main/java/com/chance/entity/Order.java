package com.chance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chance
 * @date 2025/12/8 09:11
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order implements Serializable {

    private long id;
    private double amount;
    private boolean isActive;
}
