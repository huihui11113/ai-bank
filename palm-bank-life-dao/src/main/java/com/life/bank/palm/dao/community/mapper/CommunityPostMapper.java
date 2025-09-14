package com.life.bank.palm.dao.community.mapper;
import java.util.Collection;

import com.life.bank.palm.dao.community.pojo.CommunityPostPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Mr.peng
* @description 针对表【community_post】的数据库操作Mapper
* @createDate 2024-09-04 22:45:44
* @Entity com.life.bank.palm.dao/community.pojo.CommunityPostPO
*/
public interface CommunityPostMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CommunityPostPO record);

    int insertSelective(CommunityPostPO record);

    CommunityPostPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommunityPostPO record);

    int updateByPrimaryKey(CommunityPostPO record);

    List<CommunityPostPO> getByUserId(@Param("userId") Integer userId,
                                      @Param("offset") Long offset,
                                      @Param("limit") Integer limit);


    List<CommunityPostPO> getByTitleLikeOrderByIdDesc(@Param("title") String title,
                                                      @Param("offset") Long offset,
                                                      @Param("limit") Integer limit);

    int getByTitleLikeCount(@Param("title") String title);

    List<CommunityPostPO> search(@Param("keywords") String keywords,
                                                      @Param("offset") Long offset,
                                                      @Param("limit") Integer limit);

    int searchCount(@Param("keywords") String keywords);

    int updateZanCntById(@Param("zanDelta") Integer zanDelta, @Param("id") Long id);

    int updateCollectCntById(@Param("collectDelta") Integer collectDelta, @Param("id") Long id);

    List<CommunityPostPO> selectByIdIn(@Param("idList") Collection<Long> idList);

}
