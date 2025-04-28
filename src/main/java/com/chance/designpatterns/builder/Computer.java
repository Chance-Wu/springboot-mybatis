package com.chance.designpatterns.builder;

/**
 * @author chance
 * @date 2024/12/30 14:50
 * @since 1.0
 */
public class Computer {

    private String cpu;
    private String memory;
    private String hardDisk;
    private String display;

    /**
     * 私有构造函数，防止直接创建对象
     *
     * @param builder
     */
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.memory = builder.memory;
        this.hardDisk = builder.hardDisk;
        this.display = builder.display;
    }

    /**
     * Getter 方法
     *
     * @return
     */
    public String getCpu() {
        return cpu;
    }

    public String getMemory() {
        return memory;
    }

    public String getHardDisk() {
        return hardDisk;
    }

    public String getDisplay() {
        return display;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", memory='" + memory + '\'' +
                ", hardDisk='" + hardDisk + '\'' +
                ", display='" + display + '\'' +
                '}';
    }

    /**
     * 内部静态类作为构建者
     */
    public static class Builder {
        private String cpu;
        private String memory;
        private String hardDisk;
        private String display;

        public Builder cpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder memory(String memory) {
            this.memory = memory;
            return this;
        }

        public Builder hardDisk(String hardDisk) {
            this.hardDisk = hardDisk;
            return this;
        }

        public Builder display(String display) {
            this.display = display;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}
