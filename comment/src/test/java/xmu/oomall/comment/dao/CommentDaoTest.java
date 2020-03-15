package xmu.oomall.comment.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.comment.CommentApplication;
import xmu.oomall.comment.domain.Comment;
import xmu.oomall.comment.domain.CommentPo;

import java.time.LocalDateTime;

@SpringBootTest(classes = CommentApplication.class)
@Transactional
class CommentDaoTest {
    @Autowired
    private CommentDao commentDao;

    @Test
    @Rollback
    void getCommentsTest() {
        CommentPo commentPo = commentDao.findCommentById(5623412);

        System.out.println("Fount comment: " + commentPo);
    }

    @Test
    @Rollback
    void deleteTest() {
        System.out.println("Before: ");
        System.out.println(commentDao.findCommentById(5623412));

        commentDao.deleteComment(5623412);

        System.out.println("After: ");
        System.out.println(commentDao.findCommentById(5623412));
    }


    @Test
    @Rollback
    void saveCommentTest() {
        if (commentDao.findCommentById(32) != null)
            System.out.println("评论已存在！");

        CommentPo commentPo = new CommentPo();
        commentPo.setUserId(123);
        commentPo.setStar((short) 3);
        commentPo.setContent("Ok stuff!");
        commentPo.setProductId(533);
        commentPo.setStatusCode((short) 0);

        Comment comment = new Comment(commentPo);
        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setGmtCreate(localDateTime);
        comment.setGmtModified(localDateTime);

        System.out.println("commentPo: " + commentPo);
        System.out.println("comment: " + comment);
        commentDao.saveComment(comment);


        System.out.println("new comment: " + commentDao.findCommentById(32));
    }

    @Test
    @Rollback
    void showCommentByProductIdTest() {
        System.out.println(commentDao.showCommentsByProduct(5422, 0, 3));
    }

}