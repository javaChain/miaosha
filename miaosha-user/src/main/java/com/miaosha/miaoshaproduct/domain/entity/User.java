package com.miaosha.miaoshaproduct.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class User {
    private Long userId;

    private String username;

    private String loginPassword;

    private BigDecimal balance;

    private Integer status;

    private Date userRegtime;

    public User(Long userId, String username, String loginPassword, BigDecimal balance, Integer status, Date userRegtime) {
        this.userId = userId;
        this.username = username;
        this.loginPassword = loginPassword;
        this.balance = balance;
        this.status = status;
        this.userRegtime = userRegtime;
    }

    public User() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUserRegtime() {
        return userRegtime;
    }

    public void setUserRegtime(Date userRegtime) {
        this.userRegtime = userRegtime;
    }
}