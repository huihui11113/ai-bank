/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.life.bank.palm.web.controller.user;

import com.life.bank.palm.common.result.CommonResponse;
import com.life.bank.palm.common.utils.CheckUtil;
import com.life.bank.palm.common.utils.EnumCache;
import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.service.trade.TradeServiceReconciliationService;
import com.life.bank.palm.service.trade.enums.TradeChannelEnum;
import com.life.bank.palm.service.user.UserLoginService;
import com.life.bank.palm.service.user.utils.RequestContextUtil;
import com.life.bank.palm.web.anotations.LoginRequired;
import com.life.bank.palm.web.controller.user.convertor.UserVOConvertor;
import com.life.bank.palm.web.constant.LoginConstant;
import com.life.bank.palm.web.controller.user.vo.LoginReqVO;
import com.life.bank.palm.web.controller.user.vo.QRRespVO;
import com.life.bank.palm.web.controller.user.vo.RechargeReqVO;
import com.life.bank.palm.web.controller.user.vo.UserVO;
import com.life.bank.palm.web.interceptor.WebUserRequestContextInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("user")
@Api(tags = "用户登录个人中心等接口")
public class UserController {
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private TradeServiceReconciliationService tradeServiceReconciliationService;

    @PostMapping("login")
    @ApiOperation("登录接口")
    public CommonResponse<UserVO> login(@RequestBody @ApiParam("登录入参实体") LoginReqVO loginReqVO,
                                        HttpServletResponse response) {
        String token = userLoginService.login(loginReqVO.getPhone(), loginReqVO.getPassword());
        addCookie(response, token, 90 * 24 * 3600, "");

        UserPO currentUser = RequestContextUtil.getCurrentUser();
        UserVO vo = UserVOConvertor.INSTANCE.toVO(currentUser);
        return CommonResponse.buildSuccess(vo);
    }

    @PostMapping("register")
    @ApiOperation("注册接口")
    public CommonResponse<UserVO> register(@RequestBody @ApiParam("注册入参实体") LoginReqVO loginReqVO,
                                           HttpServletResponse response) {
        String token = userLoginService.register(loginReqVO.getPhone(), loginReqVO.getValidCode(), loginReqVO.getPassword());

        addCookie(response, token, 90 * 24 * 3600, "");

        UserPO currentUser = RequestContextUtil.getCurrentUser();
        UserVO vo = UserVOConvertor.INSTANCE.toVO(currentUser);
        return CommonResponse.buildSuccess(vo);
    }

    @PostMapping("logout")
    @ApiOperation("登出接口")
    public CommonResponse<Boolean> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = WebUserRequestContextInterceptor.parseToken(request.getCookies());
        userLoginService.logout(token);

        addCookie(response, null, 0, "");

        return CommonResponse.buildSuccess(Boolean.TRUE);
    }


    @PostMapping("update/my-info")
    @ApiOperation("修改账户信息接口")
    @LoginRequired
    public CommonResponse<UserVO> updateUserInfo(@RequestBody @ApiParam("修改账户入参实体") UserVO UserVO) {
        UserPO po = UserVOConvertor.INSTANCE.toPO(UserVO);

        UserPO userPO = userLoginService.updateAccount(po);

        return CommonResponse.buildSuccess(UserVOConvertor.INSTANCE.toVO(userPO));
    }

    @GetMapping("get-verification-code")
    @ApiOperation("获取验证码信息")
    public CommonResponse<Boolean> updateUserInfo(@RequestParam(value = "phone") @ApiParam("手机号") String phone) {

        userLoginService.sendVerificationCode(phone);

        return CommonResponse.buildSuccess(Boolean.TRUE);
    }


    @GetMapping("info/me")
    @LoginRequired
    @ApiOperation("获取当前用户信息")
    public CommonResponse<UserVO> updateUserInfo() {

        UserPO currentUser = RequestContextUtil.getCurrentUser();

        return CommonResponse.buildSuccess(UserVOConvertor.INSTANCE.toVO(currentUser));
    }

    @PostMapping("update/password")
    @ApiOperation("修改密码")
    public CommonResponse<Boolean> updatePassword(@RequestBody @ApiParam("修改密码入参实体") LoginReqVO loginReqVO,
                                                  HttpServletResponse response) {
        String token = userLoginService.doUpdateUserPassword(loginReqVO.getPhone(), loginReqVO.getValidCode(), loginReqVO.getPassword());

        addCookie(response, token, 90 * 24 * 3600, "");

        return CommonResponse.buildSuccess(Boolean.TRUE);
    }

    @PostMapping("recharge/do-recharge-amount")
    @ApiOperation("给自己的账户充值金额")
    public CommonResponse<UserVO> doRecharge(@RequestBody @ApiParam("账户充值入参") RechargeReqVO reqVO) {

        CheckUtil.Biz.INSTANCE
                .noNull(reqVO, "入参不合法")
                .isTrue(NumberUtils.isDigits(reqVO.getAmount()), "输入金额异常")
                .isTrue(NumberUtils.isDigits(reqVO.getUniqueToken()), "充值过期")
                .isTrue(TradeChannelEnum.ALI_PAY.name().equals(reqVO.getChannelCode()) || TradeChannelEnum.WEI_CHAT_PAY.name().equals(reqVO.getChannelCode()), "充值渠道不支持");

        UserPO userPO = tradeServiceReconciliationService.doRecharge(Long.valueOf(reqVO.getUniqueToken()), reqVO.getAmount(), EnumCache.findByName(TradeChannelEnum.class, reqVO.getChannelCode(), null));

        return CommonResponse.buildSuccess(UserVOConvertor.INSTANCE.toVO(userPO));
    }

    @PostMapping("recharge/withdraw")
    @ApiOperation("提现")
    public CommonResponse<UserVO> doWithdraw(@RequestBody @ApiParam("提现请求入参body") RechargeReqVO reqVO) {

        CheckUtil.Biz.INSTANCE
                .noNull(reqVO, "入参不合法")
                .isTrue(NumberUtils.isDigits(reqVO.getAmount()), "输入金额异常")
                .isTrue(NumberUtils.isDigits(reqVO.getUniqueToken()), "提现请求过期")
                .isTrue(TradeChannelEnum.ALI_PAY.name().equals(reqVO.getChannelCode()) || TradeChannelEnum.WEI_CHAT_PAY.name().equals(reqVO.getChannelCode()), "提现渠道不支持");

        UserPO userPO = tradeServiceReconciliationService.doWithdraw(Long.valueOf(reqVO.getUniqueToken()), reqVO.getAmount(), EnumCache.findByName(TradeChannelEnum.class, reqVO.getChannelCode(), null));

        return CommonResponse.buildSuccess(UserVOConvertor.INSTANCE.toVO(userPO));
    }





    private static void addCookie(HttpServletResponse response, String value, int maxAge, String domain) {
        Cookie cookie = new Cookie(LoginConstant.COOKIE_NAME, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        if (StringUtils.isNoneBlank(domain))
            cookie.setDomain(domain);
        response.addCookie(cookie);
    }
}
