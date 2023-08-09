package com.chance.util;


import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

/**
 * <p> SftpUtils </p>
 *
 * @author chance
 * @date 2023/5/27 15:53
 * @since 1.0
 */
public class SftpUtils {

    private static final Logger logger = LoggerFactory.getLogger(SftpUtils.class);

    private SftpUtils() {
    }

    private static Session sshSession = null;

    /**
     * 获取ChannelSftp
     */
    public static ChannelSftp getConnectIP(String host, String sOnlineSftpPort, String username, String password) {
        int port = 0;
        if (!("".equals(sOnlineSftpPort)) && null != sOnlineSftpPort) {
            port = Integer.parseInt(sOnlineSftpPort);
        }
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            logger.error("", e);
        }
        return sftp;
    }

    /**
     * 上传
     */
    public static void upload(String directory, String uploadFile, ChannelSftp sftp) {
        File file = new File(uploadFile);
        try(FileInputStream io = new FileInputStream(file)) {
            sftp.cd(directory);
            sftp.put(io, file.getName());
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (sftp.isConnected()) {
                sshSession.disconnect();
                sftp.disconnect();
            }

        }
    }

    static boolean deleteDirFiles(String newsFile, ChannelSftp sftp) {
        try {
            sftp.cd(newsFile);
            ListIterator a = sftp.ls(newsFile).listIterator();
            while (a.hasNext()) {
                ChannelSftp.LsEntry oj = (ChannelSftp.LsEntry) a.next();
                logger.info(oj.getFilename());
                SftpUtils.delete(newsFile, oj.getFilename(), sftp);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 上传本地文件到sftp指定的服务器，
     *
     * @param directory      目标文件夹
     * @param uploadFile     本地文件夹
     * @param sftp           sftp地址
     * @param remoteFileName 重命名的文件名字
     * @param isRemote       是否需要重命名  是true 就引用remoteFileName 是false就用默认的文件名字
     */
    public static void upload(String directory, String uploadFile, ChannelSftp sftp, String remoteFileName, boolean isRemote) {
        File file = new File(uploadFile);
        try(FileInputStream io = new FileInputStream(file)) {
            boolean isExist = false;
            try {
                SftpATTRS sftpATTRS = sftp.lstat(directory);
                isExist = true;
                isExist = sftpATTRS.isDir();
            } catch (Exception e) {
                if ("no such file".equalsIgnoreCase(e.getMessage())) {
                    isExist = false;
                }
            }
            if (!isExist) {
                boolean existDir = SftpUtils.isExistDir(directory, sftp);
                if (!existDir) {
                    String[] pathArray = directory.split("/");
                    StringBuilder path = new StringBuilder("/");
                    for (String p : pathArray) {
                        if (p.equals("")) {
                            continue;
                        }
                        path.append(p + "/");
                        if (!SftpUtils.isExistDir(path + "", sftp)) {
                            // 建立目录
                            sftp.mkdir(path.toString());
                            // 进入并设置为当前目录
                        }
                        sftp.cd(path.toString());
                    }
                }
            }
            sftp.cd(directory);
            if (isRemote) {
                sftp.put(io, remoteFileName);
            } else {
                sftp.put(io, file.getName());
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (sftp.isConnected()) {
                sshSession.disconnect();
                sftp.disconnect();
            }

        }
    }

    public static boolean isExistDir(String path, ChannelSftp sftp) {
        boolean isExist = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(path);
            isExist = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if ("no such file".equalsIgnoreCase(e.getMessage())) {
                isExist = false;
            }
        }
        return isExist;

    }

    /**
     * 上传
     */
    public static List<String> uploadZip(String directory, String uploadFile, ChannelSftp sftp, List<String> filePath, String remoteName) {
        try {
            List<String> list = new ArrayList<>();
            boolean existDir = SftpUtils.isExistDir(directory, sftp);
            if (!existDir) {
                sftp.mkdir(directory);
            }
            sftp.cd(directory);
            int i = 1;
            for (String newPath : filePath) {
                File file = new File(uploadFile + newPath);
                try(FileInputStream io = new FileInputStream(file)) {
                    sftp.put(io, remoteName + "-" + i + ".jpg");
                    list.add(remoteName + "-" + i + ".jpg");
                    i++;
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
            return list;
        } catch (SftpException e) {
            logger.error("", e);
            return null;
        } finally {
            if (sftp.isConnected()) {
                sshSession.disconnect();
                sftp.disconnect();
            }
        }
    }

    /**
     * 下载
     */
    public static void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (sftp.isConnected()) {
                sshSession.disconnect();
                sftp.disconnect();
            }
        }
    }

    /**
     * 查看
     */
    public static List<String> check(String directory, ChannelSftp sftp) {
        List<String> fileList = new ArrayList<>();
        try {
            sftp.cd(directory);
            ListIterator a = sftp.ls(directory).listIterator();
            while (a.hasNext()) {
                ChannelSftp.LsEntry oj = (ChannelSftp.LsEntry) a.next();
                logger.info(oj.getFilename());
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (sftp.isConnected()) {
                sshSession.disconnect();
                sftp.disconnect();
            }
        }
        return fileList;
    }

    /**
     * 删除
     */
    public static void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
