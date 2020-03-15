package xmu.oomall.comment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xmu.oomall.comment.domain.Comment;
import xmu.oomall.comment.domain.CommentPo;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

/**
 * @author: byl
 * @date: Created in 22:02 2019/12/5
 **/
@Mapper
public interface CommentMapper {


    /**
     * 根据id返回评论对象
     * @param id 评论id
     * @return 评论对象，带评论明细
     */
    CommentPo findCommentById(Integer id);

    /**
     * 新增一个评论
     * @param comment 评论对象
     * @return 结果
     */
    Integer insertComment(Comment comment);

    /**
     * 删除一个评论
     * @param data 修改信息
     * @return 结果
     */
    Integer deleteComment(Map<String, Object> data);

    /**
     * 更新一个评论
     * @param comment 评论对象
     * @return 结果
     */
    Integer updateComment(Comment comment);

    /**
     * 根据productId获得所有评论
     *
     * @param data 货品id
     * @return 货品的评论列表
     */
    List<CommentPo> findCommentsByProduct(Map<String,Integer> data);

    /**
     * 用userId获得所有评论
     * @param data 货品id
     * @return 用户的评论列表
     */
    List<CommentPo> findComments(Map<String,Integer> data);


}
