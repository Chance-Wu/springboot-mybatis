package com.chance.entity.dto;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-09-16
 */
public class CarDto {

    private String name;
    private int seatCount;
    private String type;

    public CarDto() {
    }

    public CarDto(String name, int seatCount, String type) {
        this.name = name;
        this.seatCount = seatCount;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CarDto{" +
                "name='" + name + '\'' +
                ", seatCount=" + seatCount +
                ", type='" + type + '\'' +
                '}';
    }
}
