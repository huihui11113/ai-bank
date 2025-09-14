package com.life.bank.palm.dao.community.mapper;
import org.apache.ibatis.annotations.Param;

import com.life.bank.palm.dao.community.pojo.ZanDataPO;

/**
* @author Mr.peng
* @description 针对表【zan_data】的数据库操作Mapper
* @createDate 2024-09-04 22:49:19
* @Entity com.life.bank.palm.dao.community.pojo.ZanDataPO
*/
public interface ZanDataMapper {

    int insertSelective(ZanDataPO zanDataPO);

    int deleteLikeInfo(@Param("zanUserId") Integer zanUserId,
                       @Param("zanEntityId") Long zanEntityId,
                       @Param("zanEntityType") Integer zanEntityType);

}




