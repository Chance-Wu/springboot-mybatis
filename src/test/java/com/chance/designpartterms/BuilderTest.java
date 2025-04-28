package com.chance.designpartterms;

import com.chance.designpatterns.builder.Computer;
import org.junit.Test;

/**
 * @author chance
 * @date 2024/12/30 15:03
 * @since 1.0
 */
public class BuilderTest {

    @Test
    public void test() {
        Computer computer = new Computer.Builder()
                .cpu("Intel i7")
                .memory("16GB")
                .hardDisk("1TB SSD")
                .display("27 inch")
                .build();
        System.out.println(computer);
    }
}
