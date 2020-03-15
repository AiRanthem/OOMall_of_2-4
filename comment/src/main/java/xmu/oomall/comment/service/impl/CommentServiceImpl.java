package xmu.oomall.comment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.comment.dao.CommentDao;
import xmu.oomall.comment.domain.Comment;
import xmu.oomall.comment.domain.CommentPo;
import xmu.oomall.comment.service.CommentService;

import java.util.List;

/**
 * @author: byl
 * @description:
 * @date: Created in 21:56 2019/12/05
 * @modified By:
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Integer makeComment(Comment comment) {
        return commentDao.saveComment(comment);
    }

    @Override
    public Integer editComment(Comment comment) {
        return commentDao.updateComment(comment);
    }

    @Override
    public Integer deleteComment(Integer id) {
        return commentDao.deleteComment(id);
    }

    @Override
    public CommentPo findCommentById(Integer id) {
        return commentDao.findCommentById(id);
    }

    @Override
    public List<CommentPo> findComments(Integer userId, Integer productId, Integer offSet, Integer limit) {
        return commentDao.findComments(userId, productId, offSet, limit);
    }

    @Override
    public List<CommentPo> findCommentsByProduct(Integer productId, Integer offSet, Integer limit) {
        return commentDao.showCommentsByProduct(productId, offSet, limit);
    }
}
