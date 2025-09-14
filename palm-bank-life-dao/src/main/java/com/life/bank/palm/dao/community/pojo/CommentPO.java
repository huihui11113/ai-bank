package com.life.bank.palm.dao.community.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName comment
 */
@Data
public class CommentPO implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 评论用户ID
     */
    private Integer commentUserId;

    /**
     * 评论实体ID
     */
    private Long commentEntityId;

    /**
     * 评论实体类型
     */
    private Integer commentEntityType;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CommentPO other = (CommentPO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCommentUserId() == null ? other.getCommentUserId() == null : this.getCommentUserId().equals(other.getCommentUserId()))
            && (this.getCommentEntityId() == null ? other.getCommentEntityId() == null : this.getCommentEntityId().equals(other.getCommentEntityId()))
            && (this.getCommentEntityType() == null ? other.getCommentEntityType() == null : this.getCommentEntityType().equals(other.getCommentEntityType()))
            && (this.getCommentContent() == null ? other.getCommentContent() == null : this.getCommentContent().equals(other.getCommentContent()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCommentUserId() == null) ? 0 : getCommentUserId().hashCode());
        result = prime * result + ((getCommentEntityId() == null) ? 0 : getCommentEntityId().hashCode());
        result = prime * result + ((getCommentEntityType() == null) ? 0 : getCommentEntityType().hashCode());
        result = prime * result + ((getCommentContent() == null) ? 0 : getCommentContent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", commentUserId=").append(commentUserId);
        sb.append(", commentEntityId=").append(commentEntityId);
        sb.append(", commentEntityType=").append(commentEntityType);
        sb.append(", commentContent=").append(commentContent);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}