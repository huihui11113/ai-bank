package com.life.bank.palm.service.community;

import cn.hutool.core.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.life.bank.palm.common.annotations.LogAspectAnnotation;
import com.life.bank.palm.common.result.PageResult;
import com.life.bank.palm.common.utils.CheckUtil;
import com.life.bank.palm.dao.community.mapper.*;
import com.life.bank.palm.dao.community.pojo.*;
import com.life.bank.palm.dao.user.mapper.UserMapper;
import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.service.community.bo.CollectBO;
import com.life.bank.palm.service.community.bo.CommentBO;
import com.life.bank.palm.service.community.bo.ReplyBO;
import com.life.bank.palm.service.community.enums.CommentTypeEnum;
import com.life.bank.palm.service.community.enums.EntityTypeEnum;
import com.life.bank.palm.service.community.factory.CommunityFactory;
import com.life.bank.palm.service.snowflake.SnowflakeIdUtils;
import com.life.bank.palm.service.user.utils.RequestContextUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * author:薯条哥搞offer
 * 社区
 */
@Service
public class CommunityPostService {
    @Autowired
    private CommunityPostMapper communityPostMapper;

    @Autowired
    private ZanDataMapper zanDataMapper;
    @Autowired
    private CollectDataMapper collectDataMapper;
    @Autowired
    private CommentMapper commentDataMapper;
    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 发布接口
     */
    public void createOne(CommunityPostPO communityPostPO) {
        communityPostPO.setId(SnowflakeIdUtils.nextId());
        Integer currentUserId = RequestContextUtil.getCurrentUserId();
        communityPostPO.setUserId(currentUserId);
        communityPostPO.setCreateTime(new Date());
        communityPostMapper.insertSelective(communityPostPO);
    }

    /**
     * 列表接口
     * 这个翻页还有另外一种写法，后面可以给大家当做作业进行使用
     * @return
     */
    public PageResult index(int pageNo, int pageSize) {
        long offset = PageUtil.getStart(pageNo - 1, pageSize);
        int limit = pageSize;
        List<CommunityPostPO> communityPostPOList =
                communityPostMapper.search(null, offset, limit);
        int total = communityPostMapper.searchCount(null);
        return PageResult.buildByResult(total, pageNo, pageSize, communityPostPOList);
    }


    public List<CommunityPostPO> search(String keywords, long offset, int limit) {
        List<CommunityPostPO> communityPostPOList =
                communityPostMapper.getByTitleLikeOrderByIdDesc(keywords, offset, limit);
        return communityPostPOList;
    }

    /**
     * 更新文章信息，包含更新ces字段：评论、点赞、收藏
     *
     * @param communityPostPO
     * @return
     */
    public int updateOne(CommunityPostPO communityPostPO) {
        return communityPostMapper.updateByPrimaryKeySelective(communityPostPO);
    }


    @Transactional(rollbackFor = Exception.class)
    @LogAspectAnnotation
    public void likeOnePost(long postId) {

        CommunityPostPO postPO = communityPostMapper.selectByPrimaryKey(postId);
        CheckUtil.Biz.INSTANCE
                .noNull(postPO, "文章不存在");
        Integer currentUserId = RequestContextUtil.getCurrentUserId();
        ZanDataPO dataPO = CommunityFactory.initLikePo(currentUserId, postId, EntityTypeEnum.COMMUNITY_POST);
        zanDataMapper.insertSelective(dataPO);
        communityPostMapper.updateZanCntById(1, postId);
    }

    @Transactional(rollbackFor = Exception.class)
    @LogAspectAnnotation
    public void unLikeOnePost(long postId) {
        CommunityPostPO postPO = communityPostMapper.selectByPrimaryKey(postId);
        CheckUtil.Biz.INSTANCE
                .noNull(postPO, "文章不存在");
        Integer currentUserId = RequestContextUtil.getCurrentUserId();
        zanDataMapper.deleteLikeInfo(currentUserId, postId, EntityTypeEnum.COMMUNITY_POST.getCode());
        communityPostMapper.updateZanCntById(-1, postId);
    }

    @Transactional(rollbackFor = Exception.class)
    @LogAspectAnnotation
    public Integer collectOnePost(long postId) {
        CommunityPostPO postPO = communityPostMapper.selectByPrimaryKey(postId);
        CheckUtil.Biz.INSTANCE
                .noNull(postPO, "文章不存在");
        List<CollectDataPO> dataPOS = collectDataMapper.checkHasCollect(RequestContextUtil.getCurrentUserId(), postId, EntityTypeEnum.COMMUNITY_POST.getCode());
        CheckUtil.Biz.INSTANCE
                .isTrue(dataPOS.isEmpty(), "已经收藏过了");
        CollectDataPO dataPO = CommunityFactory.initCollect(RequestContextUtil.getCurrentUserId(), postId, EntityTypeEnum.COMMUNITY_POST);
        collectDataMapper.insertSelective(dataPO);
        communityPostMapper.updateCollectCntById(1, postId);

        return dataPO.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @LogAspectAnnotation
    public void unCollectOnePost(Integer collectId, Long postId) {
        CommunityPostPO postPO = communityPostMapper.selectByPrimaryKey(postId);
        CheckUtil.Biz.INSTANCE
                .noNull(postPO, "文章不存在");
        CollectDataPO dataPO = collectDataMapper.selectOneById(collectId);
        CheckUtil.Biz.INSTANCE
                .noNull(dataPO, "没有收藏过")
                .noNull(dataPO.getCollectUserId().equals(RequestContextUtil.getCurrentUserId()), "没有权限取消");
        collectDataMapper.deleteById(collectId);
        communityPostMapper.updateCollectCntById(-1, postId);

    }

    @LogAspectAnnotation
    public Integer comment(String content, CommentTypeEnum commentTypeEnum, Integer replyId, Long postId) {
        CommunityPostPO postPO = communityPostMapper.selectByPrimaryKey(postId);
        CheckUtil.Biz.INSTANCE
                .noNull(postPO, "文章不存在");
        CommentPO commentPO = CommunityFactory.initComment(RequestContextUtil.getCurrentUserId(), postId, EntityTypeEnum.COMMUNITY_POST, content);

        return commentDataMapper.insertSelective(commentPO);
    }

    public Integer doReply(Integer commentId, String content, CommentTypeEnum commentTypeEnum, Integer replyId) {
        CommentPO commentPO = commentDataMapper.selectOneById(commentId);
        CheckUtil.Biz.INSTANCE
                .noNull(commentPO, "评论不存在");

        ReplyPO replyPO = null;
        if (replyId != null) {
            replyPO = replyMapper.selectOneById(replyId);
            CheckUtil.Biz.INSTANCE
                    .noNull(replyPO, "回复不存在");
        }
        Integer replyUserId = replyPO == null ? commentPO.getCommentUserId() : replyPO.getReplierId();

        ReplyPO replyInitPO = CommunityFactory.initReply(RequestContextUtil.getCurrentUserId(), commentId, content, replyUserId, replyId);
        replyMapper.insertSelective(replyPO);
        // todo 如果评论表有回复数量的字段，需要去更新

        return replyInitPO.getId();
    }

    public void doDeleteComment(Integer deleteId) {
        CommentPO commentPO = commentDataMapper.selectOneById(deleteId);
        CheckUtil.Biz.INSTANCE
                .noNull(commentPO, "评论不存在")
                .isTrue(commentPO.getCommentUserId().equals(RequestContextUtil.getCurrentUserId()), "没有权限删除");
        commentDataMapper.deleteById(deleteId);
    }

    public void deleteReply(Integer deleteId) {
        ReplyPO replyPO = replyMapper.selectOneById(deleteId);
        CheckUtil.Biz.INSTANCE
                .noNull(replyPO, "回复不存在")
                .isTrue(replyPO.getReplierId().equals(RequestContextUtil.getCurrentUserId()), "没有权限删除");
        replyMapper.deleteById(deleteId);
    }

    public PageInfo<CollectBO> pageSearchMyCollect(Integer pageNo, Integer pageSize) {
        try (Page<CollectDataPO> ignored = PageHelper.startPage(pageNo, pageSize)) {
            List<CollectDataPO> dataPOS = collectDataMapper.selectByCollectUserId(RequestContextUtil.getCurrentUserId());

            if (CollectionUtils.isEmpty(dataPOS)) {
                return PageInfo.emptyPageInfo();
            }

            PageInfo<CollectDataPO> pageInfo = PageInfo.of(dataPOS);
            List<Long> postId = dataPOS.stream().map(CollectDataPO::getCollectEntityId).collect(Collectors.toList());
            List<CommunityPostPO> communityPostPOS = communityPostMapper.selectByIdIn(postId);
            Map<Long, CommunityPostPO> postPOMap = communityPostPOS.stream()
                    .collect(Collectors.toMap(CommunityPostPO::getId, Function.identity(), (o1, o2) -> o1));
            List<CollectBO> bos = dataPOS.stream().map(ele -> CommunityFactory.toBO(ele, postPOMap.get(ele.getCollectEntityId())))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            PageInfo<CollectBO> boPageInfo = PageInfo.of(bos);
            boPageInfo.setTotal(pageInfo.getTotal());
            boPageInfo.setPages(pageInfo.getPages());
            boPageInfo.setPageNum(pageInfo.getPageNum());
            return boPageInfo;
        }
    }

    public PageInfo<CommentBO> pageSearchComment(Long postId, Integer pageNo, Integer pageSize) {

        try (Page<CommentPO> ignored = PageHelper.startPage(pageNo, pageSize)) {
            List<CommentPO> commentPOS = commentDataMapper.pageSearchComment(postId, EntityTypeEnum.COMMUNITY_POST.getCode());
            PageInfo<CommentPO> poPageInfo = PageInfo.of(commentPOS);

            if (CollectionUtils.isEmpty(commentPOS)) {
                return PageInfo.emptyPageInfo();
            }
            List<Integer> userIds = commentPOS.stream().map(CommentPO::getCommentUserId).collect(Collectors.toList());

            List<UserPO> pos = userMapper.selectUsers(userIds, NumberUtils.INTEGER_ZERO);
            Map<Integer, UserPO> userPOMap = pos.stream()
                    .collect(Collectors.toMap(UserPO::getId, Function.identity(), (o1, o2) -> o1));
            List<CommentBO> bos = commentPOS.stream().map(ele -> CommunityFactory.toBO(ele, userPOMap.get(ele.getCommentUserId())))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            PageInfo<CommentBO> boPageInfo = PageInfo.of(bos);
            boPageInfo.setTotal(poPageInfo.getTotal());
            boPageInfo.setPages(poPageInfo.getPages());
            boPageInfo.setPageNum(poPageInfo.getPageNum());
            return boPageInfo;
        }
    }

    public PageInfo<ReplyBO> pageSearchReply(Integer commentId, Integer pageNo, Integer pageSize) {
        try (Page<ReplyPO> ignored = PageHelper.startPage(pageNo, pageSize)) {
            List<ReplyPO> replyPOS = replyMapper.pageSearchReply(commentId, NumberUtils.INTEGER_ZERO);
            PageInfo<ReplyPO> poPageInfo = PageInfo.of(replyPOS);

            if (CollectionUtils.isEmpty(replyPOS)) {
                return PageInfo.emptyPageInfo();
            }
            List<Integer> userIds = replyPOS.stream().map(ReplyPO::getReplierId).collect(Collectors.toList());
            List<Integer> repliedUserIds = replyPOS.stream().map(ReplyPO::getRepliedUserId).collect(Collectors.toList());
            userIds.addAll(repliedUserIds);

            List<UserPO> pos = userMapper.selectUsers(userIds, NumberUtils.INTEGER_ZERO);
            Map<Integer, UserPO> userPOMap = pos.stream()
                    .collect(Collectors.toMap(UserPO::getId, Function.identity(), (o1, o2) -> o1));

            List<ReplyBO> bos = replyPOS.stream().map(ele -> CommunityFactory.toBO(ele, userPOMap))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            PageInfo<ReplyBO> boPageInfo = PageInfo.of(bos);
            boPageInfo.setTotal(poPageInfo.getTotal());
            boPageInfo.setPages(poPageInfo.getPages());
            boPageInfo.setPageNum(poPageInfo.getPageNum());
            return boPageInfo;
        }
    }
}
