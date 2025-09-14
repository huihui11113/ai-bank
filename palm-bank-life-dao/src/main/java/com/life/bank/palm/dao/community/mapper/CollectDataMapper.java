package com.life.bank.palm.dao.community.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.life.bank.palm.dao.community.pojo.CollectDataPO;

/**
* @author Mr.peng
* @description 针对表【collect_data】的数据库操作Mapper
* @createDate 2024-09-04 22:47:42
* @Entity com.life.bank.palm.dao.community.pojo.CollectDataPO
*/
public interface CollectDataMapper {


    int insertSelective(CollectDataPO collectDataPO);

    List<CollectDataPO> checkHasCollect(@Param("collectUserId") Integer collectUserId,
                                        @Param("collectEntityId") Long collectEntityId,
                                        @Param("collectEntityType") Integer collectEntityType);

    CollectDataPO selectOneById(@Param("id") Integer id);


    int deleteById(@Param("id") Integer id);

    List<CollectDataPO> selectByCollectUserId(@Param("collectUserId") Integer collectUserId);


}
