package com.life.bank.palm.web.controller.comunity.convertor;

import com.life.bank.palm.dao.community.pojo.CommunityPostPO;
import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.service.community.bo.CollectBO;
import com.life.bank.palm.service.community.bo.CommentBO;
import com.life.bank.palm.service.community.bo.ReplyBO;
import com.life.bank.palm.service.community.enums.EntityTypeEnum;
import com.life.bank.palm.web.controller.comunity.vo.CommentVO;
import com.life.bank.palm.web.controller.comunity.vo.CommunityPostVo;
import com.life.bank.palm.web.controller.comunity.vo.ReplyVO;
import com.life.bank.palm.web.controller.user.vo.UserVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-08T11:44:02+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_271 (Oracle Corporation)"
)
public class CommunityPostVoConvertorImpl implements CommunityPostVoConvertor {

    @Override
    public CommunityPostVo toVO(CommunityPostPO po) {
        if ( po == null ) {
            return null;
        }

        CommunityPostVo communityPostVo = new CommunityPostVo();

        if ( po.getId() != null ) {
            communityPostVo.setId( String.valueOf( po.getId() ) );
        }
        communityPostVo.setTitle( po.getTitle() );
        communityPostVo.setContent( po.getContent() );
        communityPostVo.setRichContent( po.getRichContent() );
        communityPostVo.setZanCnt( po.getZanCnt() );
        communityPostVo.setCollectCnt( po.getCollectCnt() );
        communityPostVo.setViewCnt( po.getViewCnt() );
        communityPostVo.setCreateTime( po.getCreateTime() );

        return communityPostVo;
    }

    @Override
    public CommunityPostPO toVO(CommunityPostVo po) {
        if ( po == null ) {
            return null;
        }

        CommunityPostPO communityPostPO = new CommunityPostPO();

        if ( po.getId() != null ) {
            communityPostPO.setId( Long.parseLong( po.getId() ) );
        }
        communityPostPO.setTitle( po.getTitle() );
        communityPostPO.setContent( po.getContent() );
        communityPostPO.setRichContent( po.getRichContent() );
        communityPostPO.setZanCnt( po.getZanCnt() );
        communityPostPO.setCollectCnt( po.getCollectCnt() );
        communityPostPO.setViewCnt( po.getViewCnt() );
        communityPostPO.setCreateTime( po.getCreateTime() );

        return communityPostPO;
    }

    @Override
    public List<CommunityPostVo> toRecords(List<CommunityPostPO> pos) {
        if ( pos == null ) {
            return null;
        }

        List<CommunityPostVo> list = new ArrayList<CommunityPostVo>( pos.size() );
        for ( CommunityPostPO communityPostPO : pos ) {
            list.add( toVO( communityPostPO ) );
        }

        return list;
    }

    @Override
    public List<CommunityPostVo> toCollects(List<CollectBO> bos) {
        if ( bos == null ) {
            return null;
        }

        List<CommunityPostVo> list = new ArrayList<CommunityPostVo>( bos.size() );
        for ( CollectBO collectBO : bos ) {
            list.add( collectBOToCommunityPostVo( collectBO ) );
        }

        return list;
    }

    @Override
    public List<CommentVO> toCommentVOs(List<CommentBO> bos) {
        if ( bos == null ) {
            return null;
        }

        List<CommentVO> list = new ArrayList<CommentVO>( bos.size() );
        for ( CommentBO commentBO : bos ) {
            list.add( commentBOToCommentVO( commentBO ) );
        }

        return list;
    }

    @Override
    public List<ReplyVO> toReplyVOs(List<ReplyBO> bos) {
        if ( bos == null ) {
            return null;
        }

        List<ReplyVO> list = new ArrayList<ReplyVO>( bos.size() );
        for ( ReplyBO replyBO : bos ) {
            list.add( replyBOToReplyVO( replyBO ) );
        }

        return list;
    }

    protected CommunityPostVo collectBOToCommunityPostVo(CollectBO collectBO) {
        if ( collectBO == null ) {
            return null;
        }

        CommunityPostVo communityPostVo = new CommunityPostVo();

        communityPostVo.setId( collectBO.getId() );
        communityPostVo.setTitle( collectBO.getTitle() );
        communityPostVo.setContent( collectBO.getContent() );
        communityPostVo.setRichContent( collectBO.getRichContent() );
        communityPostVo.setZanCnt( collectBO.getZanCnt() );
        communityPostVo.setCollectCnt( collectBO.getCollectCnt() );
        communityPostVo.setViewCnt( collectBO.getViewCnt() );
        communityPostVo.setCreateTime( collectBO.getCreateTime() );
        communityPostVo.setCollectId( collectBO.getCollectId() );

        return communityPostVo;
    }

    protected UserVO userPOToUserVO(UserPO userPO) {
        if ( userPO == null ) {
            return null;
        }

        UserVO userVO = new UserVO();

        userVO.setId( userPO.getId() );
        userVO.setNickname( userPO.getNickname() );
        userVO.setSchoolName( userPO.getSchoolName() );
        userVO.setGender( userPO.getGender() );
        userVO.setPhone( userPO.getPhone() );
        userVO.setEmail( userPO.getEmail() );
        userVO.setLogo( userPO.getLogo() );
        userVO.setCardId( userPO.getCardId() );
        userVO.setBalance( userPO.getBalance() );

        return userVO;
    }

    protected CommentVO commentBOToCommentVO(CommentBO commentBO) {
        if ( commentBO == null ) {
            return null;
        }

        CommentVO commentVO = new CommentVO();

        commentVO.setId( commentBO.getId() );
        commentVO.setCommentUser( userPOToUserVO( commentBO.getCommentUser() ) );
        commentVO.setIsMyComment( commentBO.getIsMyComment() );
        commentVO.setCommentContent( commentBO.getCommentContent() );
        commentVO.setCreateTime( commentBO.getCreateTime() );

        return commentVO;
    }

    protected ReplyVO replyBOToReplyVO(ReplyBO replyBO) {
        if ( replyBO == null ) {
            return null;
        }

        ReplyVO replyVO = new ReplyVO();

        replyVO.setId( replyBO.getId() );
        replyVO.setCommentId( replyBO.getCommentId() );
        replyVO.setReplyUser( userPOToUserVO( replyBO.getReplyUser() ) );
        replyVO.setIsMyReply( replyBO.getIsMyReply() );
        replyVO.setContent( replyBO.getContent() );
        replyVO.setCreateTime( replyBO.getCreateTime() );
        replyVO.setRepliedUser( userPOToUserVO( replyBO.getRepliedUser() ) );
        replyVO.setRepliedId( replyBO.getRepliedId() );

        return replyVO;
    }
}
