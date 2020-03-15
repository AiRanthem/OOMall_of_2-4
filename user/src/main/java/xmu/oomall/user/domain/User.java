package xmu.oomall.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: 数据库与对象模型标准组
 * @Description: 用户信息
 * @Date: Created in 16:00 2019/11/29
 * @Modified By:
 **/

public class User {

    private Integer id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 0：男， 1：女
     */
    private Integer gender;
    /**
     * 生日
     */
    private LocalDate birthday;
    /**
     * 用户电话
     */
    private String mobile;
    /**
     * 用户积分
     */
    private Integer rebate;
    /**
     * 用户头像图片
     */
    private String avatar;
    /**
     * 最后一次登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 最后一次登录IP
     */
    private String lastLoginIp;
    /**
     * 用户级别
     */
    private Integer userLevel;
    /**
     * 微信Open ID
     */
    private String wxOpenId;
    /**
     * 微信会话Key
     */
    private String sessionKey;
    /**
     * 用户权限id
     */
    private Integer roleId;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;

    public User(Integer id, String name, String nickname, String password, Integer gender, LocalDate birthday, String mobile, Integer rebate, String avatar, LocalDateTime lastLoginTime, String lastLoginIp, Integer userLevel, String wxOpenId, String sessionKey, Integer roleId, LocalDateTime gmtCreate, LocalDateTime gmtModified, Boolean beDeleted) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.mobile = mobile;
        this.rebate = rebate;
        this.avatar = avatar;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginIp = lastLoginIp;
        this.userLevel = userLevel;
        this.wxOpenId = wxOpenId;
        this.sessionKey = sessionKey;
        this.roleId = roleId;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.beDeleted = beDeleted;
    }

    public User(UserPo userPo) {
        this.id = userPo.getId();
        this.name = userPo.getName();
        this.nickname = userPo.getNickname();
        this.password = userPo.getPassword();
        this.gender = userPo.getGender();
        this.birthday = userPo.getBirthday();
        this.mobile = userPo.getMobile();
        this.rebate = userPo.getRebate();
        this.avatar = userPo.getAvatar();
        this.lastLoginTime = userPo.getLastLoginTime();
        this.lastLoginIp = userPo.getLastLoginIp();
        this.userLevel = userPo.getUserLevel();
        this.wxOpenId = userPo.getWxOpenId();
        this.sessionKey = userPo.getSessionKey();
        this.roleId = userPo.getRoleId();
        this.gmtCreate = null;
        this.gmtModified = null;
        this.beDeleted = false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", mobile='" + mobile + '\'' +
                ", rebate=" + rebate +
                ", avatar='" + avatar + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", userLevel=" + userLevel +
                ", wxOpenId='" + wxOpenId + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", roleId=" + roleId +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", beDeleted=" + beDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getRebate() {
        return rebate;
    }

    public void setRebate(Integer rebate) {
        this.rebate = rebate;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getBeDeleted() {
        return beDeleted;
    }

    public void setBeDeleted(Boolean beDeleted) {
        this.beDeleted = beDeleted;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
