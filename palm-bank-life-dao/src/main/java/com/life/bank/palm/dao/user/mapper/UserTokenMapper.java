package com.life.bank.palm.dao.user.mapper;

import com.life.bank.palm.dao.user.pojo.UserTokenPO;
import org.apache.ibatis.annotations.Param;

/**
* @author liuxinxinxin
* @description 针对表【user_token(登录token表)】的数据库操作Mapper
* @createDate 2024-04-05 23:11:44
* @Entity com.sspnote.palmbanklife.dao.user.pojo.UserTokenPO
*/
public interface UserTokenMapper {


    int updateIsDeleteByUserIdAndPlatform(@Param("isDelete") Integer isDelete,
                                          @Param("userId") Integer userId,
                                          @Param("platform") Integer platform);

    int insertSelective(UserTokenPO userTokenPO);

    int updateIsDeleteByToken(@Param("isDelete") Integer isDelete,
                              @Param("token") String token);

    UserTokenPO selectOneByTokenAndPlatformAndIsDelete(@Param("token") String token,
                                                                @Param("platform") Integer platform,
                                                                @Param("isDelete") Integer isDelete);

}




