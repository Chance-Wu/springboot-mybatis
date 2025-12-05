package com.chance.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chance
 * @date 2025/9/12 15:09
 * @since 1.0
 */
public class CardNoUtils {

    public static void main(String[] args) {
        genarate(50000, 10000);
    }

    public static void genarate(int total, int pageSize) {
        int index = (total + pageSize - 1) / pageSize;
        System.out.println(index);
    }

    /**
     * 批量生成卡号
     *
     * @param batchPrefix   4位批次前缀（如 "0001"）
     * @param tenantCode    4位租户编码（如 "0001"）
     * @param startSequence 起始流水号
     * @param count         生成数量
     * @return 卡号列表
     */
    public static List<String> generateBatchCardNumbers(
            String batchPrefix, String tenantCode, long startSequence, int count) {
        if (batchPrefix.length() != 4) {
            throw new IllegalArgumentException("批次前缀必须为4位");
        }
        if (tenantCode.length() != 4) {
            throw new IllegalArgumentException("租户编码必须为4位");
        }
        if (startSequence < 0 || count <= 0) {
            throw new IllegalArgumentException("起始流水号必须 >= 0，生成数量必须 > 0");
        }

        List<String> cardNumbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            long sequence = startSequence + i;
            String sequenceStr = String.format("%012d", sequence);
            String cardNumber = batchPrefix + tenantCode + sequenceStr;
            cardNumbers.add(cardNumber);
        }
        return cardNumbers;
    }


}
