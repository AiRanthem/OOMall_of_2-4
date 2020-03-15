package xmu.oomall.comment.domain;

import java.util.Objects;

/**
 * @Author: 数据库与对象模型标准组
 * @Description: 评论
 * @Data: Created in 14:50 2019/11/29
 * @Modified By:
 **/

public class CommentPo {

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

    @Override
    public String toString() {
        return "CommentPo{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", statusCode=" + statusCode +
                ", star=" + star +
                ", productId=" + productId +
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
        CommentPo commentPo = (CommentPo) o;
        return Objects.equals(id, commentPo.id);
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

}
