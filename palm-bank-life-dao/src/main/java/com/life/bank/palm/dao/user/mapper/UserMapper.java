package com.life.bank.palm.dao.user.mapper;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.life.bank.palm.dao.user.pojo.UserPO;

/**
* @author liuxinxinxin
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-04-05 23:11:15
* @Entity com.sspnote.palmbanklife.dao.user.pojo.UserPO
*/
public interface UserMapper {

    UserPO selectOneByPhoneAndIsDelete(@Param("phone") String phone,
                                       @Param("isDelete") Integer isDelete);

    int insertSelective(UserPO userPO);

    UserPO selectOneByIdAndIsDelete(@Param("id") Integer id,
                                    @Param("isDelete") Integer isDelete);

    int updateSelective(UserPO userPO);

    List<UserPO> selectAllByNicknameLikeOrCardIdLike(@Param("keyWords") String keyWords,
                                                     @Param("isDelete") Integer isDelete);

    List<UserPO> search(@Param("keywords") String keywords,
                        @Param("offset") Long offset,
                        @Param("limit") Integer limit);

    int searchCount(@Param("keywords") String keywords);


    List<UserPO> selectAllByIsDelete(@Param("isDelete") Integer isDelete);


    List<UserPO> selectUsers(@Param("idList") Collection<Integer> idList,
                             @Param("isDelete") Integer isDelete);

}




