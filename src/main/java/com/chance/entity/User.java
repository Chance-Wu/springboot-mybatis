package com.chance.entity;

import javax.persistence.*;

@Table(name = "user")
public class User {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    @Column(name = "user_sex")
    private String userSex;

    @Column(name = "nick_name")
    private String nickName;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return user_sex
     */
    public String getUserSex() {
        return userSex;
    }

    /**
     * @param userSex
     */
    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }

    /**
     * @return nick_name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }
}