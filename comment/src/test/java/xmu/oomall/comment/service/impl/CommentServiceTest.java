//package xmu.oomall.comment.service.impl;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import xmu.oomall.comment.CommentApplication;
//import xmu.oomall.comment.dao.CommentDao;
//import xmu.oomall.comment.domain.Comment;
//import xmu.oomall.comment.domain.CommentPo;
//import xmu.oomall.comment.service.CommentService;
//
//
//@SpringBootTest(classes = CommentApplication.class)
//class CommentServiceTest {
//
//    @Autowired
//    private CommentService commentService;
//    @Autowired
//    private CommentDao commentDao;
//
//
//    @Test
//    @Rollback
//    void addComments() {
//
//        for (int i = 0; i < 10; i++) {
//            CommentPo commentPo = new CommentPo();
//            commentPo.setUserId(233);
//            commentPo.setContent("默认好评");
//            commentPo.setProductId(5421+i);
//            commentPo.setStar((short) 5);
//            commentPo.setStatusCode((short) 0);
//
//            Comment comment = new Comment(commentPo);
//
//            commentService.makeComment(comment);
//        }
//
//    }
//
//    @Test
//    @Rollback
//    void reviewTest() {
//        CommentPo commentPo = commentDao.findCommentById(21);
//        System.out.println("Before: " + commentPo);
//
//        commentPo.setStatusCode((short) 1);
//        Comment comment = new Comment(commentPo);
//
//        commentService.editComment(comment);
//
//        System.out.println("After: ");
//        System.out.println(commentDao.findCommentById(21));
//    }
//}