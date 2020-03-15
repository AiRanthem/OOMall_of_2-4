package xmu.oomall.comment.service;

import org.springframework.stereotype.Service;
import xmu.oomall.comment.domain.Comment;
import xmu.oomall.comment.domain.CommentPo;

import java.util.List;

/**
 * 评论服务
 *
 * @author: byl
 * @date: Created in 21:34 2019/12/05
 **/
@Service
public interface CommentService {

    /**
     * 生成评论
     *
     * @param comment 评论的id
     * @return 更改结果
     * 0：saved successfully
     * 1：not saved
     * -1：error
     */
    Integer makeComment(Comment comment);

    /**
     * 更改评论
     *
     * @param comment 评论的
     * @return 更改结果
     * 0：edited successfully
     * 1：not edited
     * -1：error
     */
    Integer editComment(Comment comment);

    /**
     * 根据id删除评论
     *
     * @param id 评论的id
     * @return 更改结果
     * 0：edited successfully
     * 1：not edited
     * -1：error
     */
    Integer deleteComment(Integer id);

    /**
     * 根据id找到评论
     *
     * @param id 评论id
     * @return 评论对象
     */
    CommentPo findCommentById(Integer id);

    /**
     * 根据userId获得所有评论
     *
     * @param offSet    地址偏移量
     * @param limit     选择条数
     * @param userId    用户id
     * @param productId 货品id
     * @return 货品的评论列表
     */
    List<CommentPo> findComments(Integer userId, Integer productId, Integer offSet, Integer limit);

    /**
     * 根据productId获得所有评论
     *
     * @param offSet    地址偏移量
     * @param limit     选择条数
     * @param productId 货品id
     * @return 货品的评论列表
     */
    List<CommentPo> findCommentsByProduct(Integer productId, Integer offSet, Integer limit);


}
