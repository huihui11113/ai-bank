package com.life.bank.palm.web.controller.user.convertor;

import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.web.controller.user.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/05 23:32
 */
@Mapper
public interface UserVOConvertor {

    UserVOConvertor INSTANCE = Mappers.getMapper(UserVOConvertor.class);

    UserVO toVO(UserPO po);

    UserPO toPO(UserVO vo);

    List<UserVO> toVOS(List<UserPO> pos);
}
