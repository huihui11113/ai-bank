package com.life.bank.palm.service.community.factory;

import com.life.bank.palm.dao.community.pojo.*;
import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.service.community.bo.CollectBO;
import com.life.bank.palm.service.community.bo.CommentBO;
import com.life.bank.palm.service.community.bo.ReplyBO;
import com.life.bank.palm.service.community.enums.EntityTypeEnum;
import com.life.bank.palm.service.user.utils.RequestContextUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/08/31 22:43
 */
public class CommunityFactory {

    public static ZanDataPO initLikePo(Integer userId, Long entityId, EntityTypeEnum typeEnum) {
        ZanDataPO zanDataPO = new ZanDataPO();
        zanDataPO.setZanUserId(userId);
        zanDataPO.setZanEntityId(entityId);
        zanDataPO.setZanEntityType(typeEnum.getCode());
        return zanDataPO;
    }

    public static CollectDataPO initCollect(Integer userId, Long entityId, EntityTypeEnum typeEnum) {
        CollectDataPO collectData = new CollectDataPO();
        collectData.setCollectUserId(userId);
        collectData.setCollectEntityId(entityId);
        collectData.setCollectEntityType(typeEnum.getCode());
        return collectData;
    }

    public static CommentPO initComment(Integer userId, Long entityId, EntityTypeEnum typeEnum, String content) {
        CommentPO collectData = new CommentPO();
        collectData.setCommentUserId(userId);
        collectData.setCommentEntityId(entityId);
        collectData.setCommentEntityType(typeEnum.getCode());
        collectData.setCommentContent(content);
        return collectData;
    }

    public static ReplyPO initReply(Integer userId, Integer commentId, String content, Integer repliedUserId, Integer repliedId) {
        ReplyPO reply = new ReplyPO();
        reply.setCommentId(commentId);
        reply.setReplierId(userId);
        reply.setContent(content);
        reply.setRepliedUserId(repliedUserId);
        reply.setRepliedUserId(repliedId);
        return reply;
    }

    public static CollectBO toBO(CollectDataPO collectDataPO, CommunityPostPO postPO) {
        if (Objects.isNull(collectDataPO) || Objects.isNull(postPO)) {
            return null;
        }
        CollectBO collectBO = new CollectBO();
        collectBO.setId(String.valueOf(postPO.getId()));
        collectBO.setCollectId(collectDataPO.getId());

        collectBO.setTitle(postPO.getTitle());
        collectBO.setContent(postPO.getContent());
        collectBO.setRichContent(postPO.getRichContent());
        collectBO.setViewCnt(postPO.getViewCnt());
        collectBO.setZanCnt(postPO.getZanCnt());
        collectBO.setCollectCnt(postPO.getCollectCnt());
        return collectBO;
    }

    public static CommentBO toBO(CommentPO commentPO, UserPO userPO) {
        if (Objects.isNull(commentPO) || Objects.isNull(userPO)) {
            return null;
        }
        CommentBO commentBO = new CommentBO();
        commentBO.setId(commentPO.getId());
        commentBO.setCommentContent(commentPO.getCommentContent());
        commentBO.setCreateTime(commentPO.getCreateTime());
        commentBO.setIsMyComment(commentPO.getCommentUserId().equals(RequestContextUtil.getCurrentUserId()));
        commentBO.setCommentUser(userPO);
        return commentBO;
    }

    public static ReplyBO toBO(ReplyPO replyPO, Map<Integer, UserPO> userPO) {
        if (Objects.isNull(replyPO) || Objects.isNull(userPO)) {
            return null;
        }
        ReplyBO replyBO = new ReplyBO();
        replyBO.setId(replyPO.getId());
        replyBO.setCommentId(replyPO.getCommentId());

        replyBO.setReplyUser(userPO.get(replyPO.getReplierId()));
        replyBO.setIsMyReply(replyPO.getReplierId().equals(RequestContextUtil.getCurrentUserId()));

        replyBO.setContent(replyPO.getContent());

        replyBO.setCreateTime(replyPO.getCreateTime());

        replyBO.setRepliedUser(userPO.get(replyPO.getRepliedUserId()));
        replyBO.setRepliedId(replyPO.getRepliedId());
        return replyBO;
    }
}
