package xmu.oomall.comment.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: 数据库与对象模型标准组
 * @Description: 评论
 * @Data: Created in 14:50 2019/11/29
 * @Modified By:
 **/

public class Comment {

    private Integer id;
    /**
     * 发表评论的用户的id
     */
    private Integer userId;
    /**
     * 发表评论的内容
     */
    private String content;
    /**
     * 评论的状态 0：未审核 1：审核通过 2：审核失败
     */
    private Short statusCode;

    /**
     * 发表评论的星级
     */
    private Short star;
    /**
     * 评论的产品的id
     */
    private Integer productId;


    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;

    public Comment(Integer id, Integer userId, String content, Short statusCode, Short star, Integer productId, LocalDateTime gmtCreate, LocalDateTime gmtModified, Boolean beDeleted) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.statusCode = statusCode;
        this.star = star;
        this.productId = productId;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.beDeleted = beDeleted;
    }

    public Comment(CommentPo commentPo) {
        this.id = commentPo.getId();
        this.userId = commentPo.getUserId();
        this.content = commentPo.getContent();
        this.statusCode = commentPo.getStatusCode();
        this.star = commentPo.getStar();
        this.productId = commentPo.getProductId();
        this.gmtCreate = null;
        this.gmtModified = null;
        this.beDeleted = false;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", statusCode=" + statusCode +
                ", star=" + star +
                ", productId=" + productId +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", beDeleted=" + beDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Short getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Short statusCode) {
        this.statusCode = statusCode;
    }

    public Short getStar() {
        return star;
    }

    public void setStar(Short star) {
        this.star = star;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

}
