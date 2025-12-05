package com.chance.util.findcommon;

import com.chance.entity.User;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author chance
 * @date 2025/7/17 08:54
 * @since 1.0
 */
public class Utils {

    /**
     * 计算给定URL的哈希值并将其映射到指定数量的分区中。
     * 此方法使用SHA-256算法生成URL的哈希，然后通过取模运算确定分区索引。
     *
     * @param url           需要计算哈希值的字符串，必须不为空。
     * @param numPartitions 分区的数量，必须是正整数。
     * @return 返回一个非负整数，表示URL对应的分区索引。
     * @throws IllegalArgumentException 如果url为null或numPartitions小于等于0。
     * @throws NoSuchAlgorithmException 如果SHA-256算法不可用。
     */
    public static int hashUrl(String url, int numPartitions) throws NoSuchAlgorithmException {
        if (url == null || numPartitions <= 0) {
            throw new IllegalArgumentException("url must not be null and numPartitions must be positive");
        }

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // 更安全的哈希算法
            byte[] digest = md.digest(url.getBytes(StandardCharsets.UTF_8)); // 明确指定字符集
            long hash = ((long) (digest[0] & 0xFF) << 24)
                    | ((long) (digest[1] & 0xFF) << 16)
                    | ((long) (digest[2] & 0xFF) << 8)
                    | ((long) (digest[3] & 0xFF));
            return (int) ((hash & Long.MAX_VALUE) % numPartitions); // 避免负数问题
        } catch (NoSuchAlgorithmException e) {
            // 记录异常日志
            throw new RuntimeException("Failed to compute hash for URL: " + url, e);
        }
    }

    /**
     * 如果指定的目录不存在，则创建它。如果目录已存在，则不会执行任何操作。
     * 尝试以特定权限创建目录，若因文件系统不支持权限设置而失败，则回退至默认行为。
     *
     * @param dirPath 要检查和创建的目录路径。
     * @throws IOException 如果创建目录时发生I/O错误。
     */
    public static void createDirectoryIfNotExists(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            try {
                // 显式设置目录权限为 rwx------
                Files.createDirectories(path, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------")));
            } catch (FileAlreadyExistsException ignored) {
                // 并发情况下目录可能已被其他线程创建，忽略此异常
            } catch (UnsupportedOperationException e) {
                // 当前文件系统不支持设置权限，回退到默认行为
                Files.createDirectories(path);
            }
        }
    }

    public static void main(String[] args) {
        // 打印虚拟机版本信息
        System.out.println(VM.current().details());
        // 创建一个空的对象
        User u = new User();
        u.setUsername("chance");
        // 获取并打印该对象的类布局信息
        System.out.println(ClassLayout.parseInstance(u).toPrintable());
    }

    public static boolean isAllUpperCase(String str) {
        // 检查字符串是否为null或者空串，或者是仅包含非字母字符的情况
        if (str == null || str.isEmpty() || !str.matches("^[^A-Za-z]*$")) {
            return false;
        }
        // 将字符串转换成大写，并与原字符串比较
        return str.equals(str.toUpperCase());
    }
}
