package com.life.bank.palm.web.controller.comunity;

import com.github.pagehelper.PageInfo;
import com.life.bank.palm.common.result.CommonResponse;
import com.life.bank.palm.common.result.PageResult;
import com.life.bank.palm.common.utils.CheckUtil;
import com.life.bank.palm.service.community.CommunityPostService;
import com.life.bank.palm.service.community.bo.CollectBO;
import com.life.bank.palm.service.community.bo.CommentBO;
import com.life.bank.palm.service.community.bo.ReplyBO;
import com.life.bank.palm.service.community.enums.CommentTypeEnum;
import com.life.bank.palm.web.anotations.LoginRequired;
import com.life.bank.palm.web.controller.comunity.convertor.CommunityPostVoConvertor;
import com.life.bank.palm.web.controller.comunity.vo.CommentVO;
import com.life.bank.palm.web.controller.comunity.vo.CommunityPostVo;
import com.life.bank.palm.web.controller.comunity.vo.OperationReqVO;
import com.life.bank.palm.web.controller.comunity.vo.ReplyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：麻薯哥搞offer
 * @description ：转账部分核心接口
 * @date ：2024/04/14 00:09
 */
@RestController
@RequestMapping("community")
@Api(tags = "社区核心接口汇总")
public class CommunityController {

    @Autowired
    private CommunityPostService communityPostService;

    @PostMapping("post/create-one")
    @ApiOperation("新创建一个篇帖子")
    public CommonResponse<Void> createOne(@RequestBody @ApiParam("输入社区实体") CommunityPostVo communityPostVo) {
        CheckUtil.Biz.INSTANCE
                .noNull(communityPostVo, "入参不合法")
                .strNotBlank(communityPostVo.getTitle(), "标题不能为空")
                .strNotBlank(communityPostVo.getRichContent(), "内容不能为空");
        communityPostService.createOne(CommunityPostVoConvertor.INSTANCE.toVO(communityPostVo));
        return CommonResponse.buildSuccess();
    }


    @GetMapping("post/index-list")
    @ApiOperation("查询帖子的list列表")
    public CommonResponse<PageResult> indexList(@RequestParam(defaultValue = "1") @ApiParam("页码") Integer pageNo,
                                                @RequestParam(defaultValue = "10") @ApiParam("每页数") Integer pageSize) {
        PageResult index = communityPostService.index(pageNo, pageSize);
        index.setResult(CommunityPostVoConvertor.INSTANCE.toRecords(index.getResult()));
        return CommonResponse.buildSuccess(index);
    }

    @GetMapping("post/hot-list")
    @ApiOperation("社区热门帖子")
    public CommonResponse<PageResult> hotList() {
        PageResult index = communityPostService.index(1, 10);
        index.setResult(CommunityPostVoConvertor.INSTANCE.toRecords(index.getResult()));
        return CommonResponse.buildSuccess(index);
    }


    // 点赞接口
    @PostMapping("zan-one-post")
    @ApiOperation("点赞帖子")
    @LoginRequired
    public CommonResponse<Boolean> zanOne(@RequestBody @ApiParam("点赞帖子入参body") OperationReqVO reqVO) {
        CheckUtil.Biz.INSTANCE
                .noNull(reqVO, "入参不能为空")
                .isTrue(StringUtils.isNumeric(reqVO.getPostId()), "帖子id不合法");
        communityPostService.likeOnePost(Long.parseLong(reqVO.getPostId()));

        return CommonResponse.buildSuccess(Boolean.TRUE);
    }

    @PostMapping("un-zan-one-post")
    @ApiOperation("社区热门帖子")
    @LoginRequired
    public CommonResponse<Boolean> unZanOne(@RequestBody @ApiParam("取消点赞帖子入参body") OperationReqVO reqVO) {
        CheckUtil.Biz.INSTANCE
                .noNull(reqVO, "入参不能为空")
                .isTrue(StringUtils.isNumeric(reqVO.getPostId()), "帖子id不合法");
        communityPostService.unLikeOnePost(Long.parseLong(reqVO.getPostId()));
        return CommonResponse.buildSuccess(Boolean.TRUE);
    }

    // 收藏接口
    @PostMapping("collect-one-post")
    @ApiOperation("收藏帖子")
    @LoginRequired
    public CommonResponse<Integer> collectOne(@RequestBody @ApiParam("收藏帖子入参body") OperationReqVO reqVO) {
        CheckUtil.Biz.INSTANCE
                .noNull(reqVO, "入参不能为空")
                .isTrue(StringUtils.isNumeric(reqVO.getPostId()), "帖子id不合法");

        Integer collectId = communityPostService.collectOnePost(Long.parseLong(reqVO.getPostId()));

        return CommonResponse.buildSuccess(collectId);
    }

    @PostMapping("un-collect-one-post/{collectId}")
    @ApiOperation("取消收藏帖子")
    @LoginRequired
    public CommonResponse<Boolean> unCollectOne(@PathVariable @ApiParam("取消收藏收藏记录id") Integer collectId, @RequestBody @ApiParam("取消收藏帖子入参body") OperationReqVO reqVO) {
        CheckUtil.Biz.INSTANCE
                .noNull(reqVO, "入参不能为空")
                .isTrue(StringUtils.isNumeric(reqVO.getPostId()), "帖子id不合法");
        communityPostService.unCollectOnePost(collectId, Long.parseLong(reqVO.getPostId()));

        return CommonResponse.buildSuccess(Boolean.TRUE);
    }

    // 评论/回复接口
    @PostMapping("comment")
    @ApiOperation("评论")
    @LoginRequired
    public CommonResponse<Integer> comment(@RequestBody @ApiParam("评论入参body") OperationReqVO reqVO) {
        CheckUtil.Biz.INSTANCE
                .noNull(reqVO, "入参不能为空")
                .strNotBlank(reqVO.getContent(), "评论内容不能为空")
                .noNull(CommentTypeEnum.parseType(reqVO.getCommentType()), "不支持的评论类型");

        Integer commented = communityPostService.comment(reqVO.getContent(), CommentTypeEnum.parseType(reqVO.getCommentType()), reqVO.getReplyId(), Long.parseLong(reqVO.getPostId()));

        return CommonResponse.buildSuccess(commented);
    }

    @PostMapping("comment-reply")
    @ApiOperation("回复")
    @LoginRequired
    public CommonResponse<Integer> commentReply(@RequestBody @ApiParam("回复入参body") OperationReqVO reqVO) {
        //
        CheckUtil.Biz.INSTANCE
                .noNull(reqVO, "入参不能为空")
                .strNotBlank(reqVO.getContent(), "回复内容不能为空")
                .noNull(reqVO.getCommentId(), "评论id不能为空")
                .noNull(CommentTypeEnum.parseType(reqVO.getCommentType()), "不支持的评论类型");
        Integer replayId = communityPostService.doReply(reqVO.getCommentId(), reqVO.getContent(), CommentTypeEnum.parseType(reqVO.getCommentType()), reqVO.getReplyId());


        return CommonResponse.buildSuccess(replayId);
    }

    // 删除评论
    @PostMapping("delete-comment/{deleteId}")
    @ApiOperation("删除评论")
    @LoginRequired
    public CommonResponse<Boolean> deleteComment(@PathVariable("评论id") Integer deleteId) {
        //
        communityPostService.doDeleteComment(deleteId);
        return CommonResponse.buildSuccess(Boolean.TRUE);
    }

    @PostMapping("delete-reply/{deleteId}")
    @ApiOperation("删除回复")
    @LoginRequired
    public CommonResponse<Boolean> deleteReply(@PathVariable("回复id") Integer deleteId) {
        //
        communityPostService.deleteReply(deleteId);
        return CommonResponse.buildSuccess(Boolean.TRUE);
    }

    // 收藏列表

    @GetMapping("post/collect-list")
    @ApiOperation("收藏的帖子list列表")
    @LoginRequired
    public CommonResponse<PageResult<CommunityPostVo>> collectList(@RequestParam(defaultValue = "1") @ApiParam("页码") Integer pageNo,
                                                                   @RequestParam(defaultValue = "10") @ApiParam("每页数") Integer pageSize) {

        PageInfo<CollectBO> collectBOPageResult = communityPostService.pageSearchMyCollect(pageNo, pageSize);
        // 转一道
        List<CommunityPostVo> collectVos = CommunityPostVoConvertor.INSTANCE.toCollects(collectBOPageResult.getList());
        PageResult<CommunityPostVo> tPageResult = PageResult.buildWithPageInfo(collectBOPageResult, collectVos);

        return CommonResponse.buildSuccess(tPageResult);
    }

    @GetMapping("post/comment-list/{postId}")
    @ApiOperation("查询帖子的list列表")
    @LoginRequired
    public CommonResponse<PageResult<CommentVO>> commentList(@PathVariable("帖子id") @ApiParam("帖子id") String postId,
                                                             @RequestParam(defaultValue = "1") @ApiParam("页码") Integer pageNo,
                                                             @RequestParam(defaultValue = "10") @ApiParam("每页数") Integer pageSize) {

        CheckUtil.Biz.INSTANCE
                .isTrue(StringUtils.isNumeric(postId), "帖子id不合法");


        PageInfo<CommentBO> pageInfo = communityPostService.pageSearchComment(Long.parseLong(postId), pageNo, pageSize);
        List<CommentVO> commentVOs = CommunityPostVoConvertor.INSTANCE.toCommentVOs(pageInfo.getList());
        PageResult<CommentVO> pageResult = PageResult.buildWithPageInfo(pageInfo, commentVOs);

        return CommonResponse.buildSuccess(pageResult);
    }

    @GetMapping("post/reply-list/{commentId}")
    @ApiOperation("查询帖子的list列表")
    @LoginRequired
    public CommonResponse<PageResult<ReplyVO>> replyList(@PathVariable("评论id") @ApiParam("评论id") Integer commentId,
                                                         @RequestParam(defaultValue = "1") @ApiParam("页码") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") @ApiParam("每页数") Integer pageSize) {
        PageInfo<ReplyBO> pageInfo = communityPostService.pageSearchReply(commentId, pageNo, pageSize);
        List<ReplyVO> replyVOs = CommunityPostVoConvertor.INSTANCE.toReplyVOs(pageInfo.getList());
        PageResult<ReplyVO> pageResult = PageResult.buildWithPageInfo(pageInfo, replyVOs);

        return CommonResponse.buildSuccess(pageResult);
    }

}
