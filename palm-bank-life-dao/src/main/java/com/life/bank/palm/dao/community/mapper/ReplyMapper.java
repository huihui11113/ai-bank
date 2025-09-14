package com.life.bank.palm.dao.community.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.life.bank.palm.dao.community.pojo.ReplyPO;

/**
* @author liuxinxinxin
* @description 针对表【reply】的数据库操作Mapper
* @createDate 2024-08-31 23:34:33
* @Entity com.life.bank.palm.dao.community.pojo.ReplyPO
*/
public interface ReplyMapper {

    ReplyPO selectOneById(@Param("id") Integer id);

    int insertSelective(ReplyPO replyPO);


    int deleteById(@Param("id") Integer id);

    List<ReplyPO> pageSearchReply(@Param("commentId") Integer commentId,
                                  @Param("isDelete") Integer isDelete);
}




