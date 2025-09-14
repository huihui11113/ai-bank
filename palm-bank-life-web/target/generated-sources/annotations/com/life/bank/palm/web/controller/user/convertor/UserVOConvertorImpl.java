package com.life.bank.palm.web.controller.user.convertor;

import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.web.controller.user.vo.UserVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-08T11:44:02+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_271 (Oracle Corporation)"
)
public class UserVOConvertorImpl implements UserVOConvertor {

    @Override
    public UserVO toVO(UserPO po) {
        if ( po == null ) {
            return null;
        }

        UserVO userVO = new UserVO();

        userVO.setId( po.getId() );
        userVO.setNickname( po.getNickname() );
        userVO.setSchoolName( po.getSchoolName() );
        userVO.setGender( po.getGender() );
        userVO.setPhone( po.getPhone() );
        userVO.setEmail( po.getEmail() );
        userVO.setLogo( po.getLogo() );
        userVO.setCardId( po.getCardId() );
        userVO.setBalance( po.getBalance() );

        return userVO;
    }

    @Override
    public UserPO toPO(UserVO vo) {
        if ( vo == null ) {
            return null;
        }

        UserPO userPO = new UserPO();

        userPO.setId( vo.getId() );
        userPO.setNickname( vo.getNickname() );
        userPO.setSchoolName( vo.getSchoolName() );
        userPO.setGender( vo.getGender() );
        userPO.setPhone( vo.getPhone() );
        userPO.setEmail( vo.getEmail() );
        userPO.setLogo( vo.getLogo() );
        userPO.setCardId( vo.getCardId() );
        userPO.setBalance( vo.getBalance() );

        return userPO;
    }

    @Override
    public List<UserVO> toVOS(List<UserPO> pos) {
        if ( pos == null ) {
            return null;
        }

        List<UserVO> list = new ArrayList<UserVO>( pos.size() );
        for ( UserPO userPO : pos ) {
            list.add( toVO( userPO ) );
        }

        return list;
    }
}
