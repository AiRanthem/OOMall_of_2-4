package xmu.oomall.comment.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.comment.domain.Comment;
import xmu.oomall.comment.domain.CommentPo;
import xmu.oomall.comment.mapper.CommentMapper;
import xmu.oomall.comment.service.impl.CommentServiceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author: byl
 * @date: Created in 22:00 2019/12/5
 **/

@Repository
public class CommentDao {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 保存新的评论，包括评论明细
     *
     * @param comment 评论
     * @return 结果
     */
    public Integer saveComment(Comment comment) {
        logger.debug("CommentDao: trying to insert a comment with commentMapper");

        try {
            Integer success = commentMapper.insertComment(comment);
            logger.debug("comment对象保存成功：" + success);
            return success;
        } catch (Exception e) {
            System.out.println("数据插入数据库失败！");
            return 0;
        }
    }

    /**
     * 更新评论审核
     *
     * @param comment
     * @return 更新结果
     */
    public Integer updateComment(Comment comment) {

        logger.debug("Trying to update a comment: " + comment.getId() + " with commentMapper");

        try {
            Integer success = commentMapper.updateComment(comment);
            logger.debug("comment对象修改成功：" + success);
            return success;
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return 0;
        }
    }

    /**
     * 根据id删除评论
     *
     * @param id 评论id
     * @return 更新结果
     */
    public Integer deleteComment(Integer id) {
        logger.debug("Trying to delete a comment : " + id + " with commentMapper");

        LocalDateTime localDateTime = LocalDateTime.now();
        Map<String, Object> data = new HashMap<>(2);
        data.put("id", id);
        data.put("gmtModified", localDateTime);
        try {
            Integer success = commentMapper.deleteComment(data);
            logger.debug("comment对象删除成功：" + success);
            return success;
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return 0;
        }
    }

    /**
     * 根据id返回评论对象
     *
     * @param id 评论id
     * @return 评论对象，带评论明细
     */
    public CommentPo findCommentById(Integer id) {

        logger.debug("Trying to find a comment : " + id + " with commentMapper");

        try {
            CommentPo commentPo = commentMapper.findCommentById(id);
            logger.debug("获得的comment对象：" + commentPo);
            return commentPo;
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return null;
        }
    }

    /**
     * 根据userId返回评论
     * 本来被生成id方法调用的
     *
     * @param userId 用户id
     * @return 某个用户评论
     */
    public List<CommentPo> findComments(Integer userId, Integer productId, Integer offSet, Integer limit) {

        logger.debug("Trying to find all comments by user id " + userId + " with commentMapper");

        Map<String, Integer> data = new HashMap<>(4);
        data.put("userId", userId);
        data.put("productId", productId);
        data.put("offSet", offSet);
        data.put("limit", limit);

        try {
            List<CommentPo> commentPoList = commentMapper.findComments(data);
            logger.debug("获得的comment对象：" + commentPoList);
            return commentPoList;
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return null;
        }
    }

    /**
     * 根据productId返回评论
     * 本来被生成id方法调用的
     *
     * @param productId 用户id
     * @return 某个货品所有评论
     */
    public List<CommentPo> showCommentsByProduct(Integer productId, Integer offSet, Integer limit) {
        logger.debug("Trying to find all comments by product id " + productId + " with commentMapper");

        Map<String, Integer> data = new HashMap<>();
        data.put("productId", productId);
        data.put("offSet", offSet);
        data.put("limit", limit);

        try {
            List<CommentPo> commentPoList = commentMapper.findCommentsByProduct(data);
            logger.debug("获得的comment对象：" + commentPoList);
            return commentPoList;
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return null;
        }
    }
}
