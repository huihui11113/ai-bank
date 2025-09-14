package com.life.bank.palm.web.controller.comunity.convertor;

import com.life.bank.palm.dao.community.pojo.CommunityPostPO;

import com.life.bank.palm.service.community.bo.CollectBO;
import com.life.bank.palm.service.community.bo.CommentBO;
import com.life.bank.palm.service.community.bo.ReplyBO;
import com.life.bank.palm.service.community.enums.EntityTypeEnum;
import com.life.bank.palm.web.controller.comunity.vo.CommentVO;
import com.life.bank.palm.web.controller.comunity.vo.CommunityPostVo;
import com.life.bank.palm.web.controller.comunity.vo.ReplyVO;
import com.life.bank.palm.web.controller.user.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * author:薯条哥搞offer
 */
@Mapper(imports = EntityTypeEnum.class)
public interface CommunityPostVoConvertor {
    CommunityPostVoConvertor INSTANCE = Mappers.getMapper(CommunityPostVoConvertor.class);

    CommunityPostVo toVO(CommunityPostPO po);

    CommunityPostPO toVO(CommunityPostVo po);


    List<CommunityPostVo> toRecords(List<CommunityPostPO> pos);


    List<CommunityPostVo> toCollects(List<CollectBO> bos);

    List<CommentVO> toCommentVOs(List<CommentBO> bos);

    List<ReplyVO> toReplyVOs(List<ReplyBO> bos);

}
