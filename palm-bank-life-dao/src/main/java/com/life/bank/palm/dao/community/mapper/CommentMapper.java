package com.life.bank.palm.dao.community.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.life.bank.palm.dao.community.pojo.CommentPO;

/**
* @author 薯条哥搞offer
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2024-05-05 15:25:01
* @Entity com.life.bank.palm.dao.community.pojo.CommentPO
*/
public interface CommentMapper {


    int insertSelective(CommentPO commentPO);


    CommentPO selectOneById(@Param("id") Integer id);

    int deleteById(@Param("id") Integer id);


    List<CommentPO> pageSearchComment(@Param("commentEntityId") Long commentEntityId,
                                      @Param("commentEntityType") Integer commentEntityType);


}
