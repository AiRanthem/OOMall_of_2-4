package xmu.oomall.comment.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import xmu.oomall.comment.CommentApplication;
import xmu.oomall.comment.dao.CommentDao;
import xmu.oomall.comment.domain.Comment;
import xmu.oomall.comment.domain.CommentPo;
import xmu.oomall.comment.service.CommentService;
import xmu.oomall.comment.util.JacksonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CommentApplication.class)
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private CommentService commentService;

    @Test
    @Rollback
    void getCommentsByUserIdTest() throws Exception {
//        Integer userId, Integer productId, Integer page, Integer limit
        String responseString = this.mockMvc.perform(get("/admin/comments").contentType("application/json;charset=UTF-8")
                .param("userId", String.valueOf(233))
                .param("productId", String.valueOf(5421))
                .param("page", String.valueOf(1))
                .param("limit", String.valueOf(12)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();


        System.out.println(responseString);
    }


    @Test
    @Rollback
    void getCommentsByProductIdTest() throws Exception {

        String responseString = this.mockMvc.perform(get("/product/5422/comments")
                .param("page", "1")
                .param("limit", "5")
                .contentType("application/json;charset=UTF-8"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(responseString);
    }


    @Test
    @Rollback
    void commentCreationTest() throws Exception {
        // in case it exists
        if (commentDao.findCommentById(31) != null)
            commentDao.deleteComment(31);

        CommentPo commentPo = new CommentPo();

        commentPo.setContent("Trying out this comment");
        commentPo.setProductId(56);
        commentPo.setStar((short) 5);
        commentPo.setStatusCode((short) 2);
        commentPo.setUserId(234);

        String jsonString = JacksonUtil.toJson(commentPo);

        System.out.println(jsonString);

        String responseString = this.mockMvc.perform(post("/product/56/comments").accept(MediaType.APPLICATION_JSON).contentType("application/json;charset=UTF-8").content(jsonString))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(commentDao.findCommentById(31));
        System.out.println(responseString);
    }


    @Test
    @Rollback
    void commentEditingTest() throws Exception {

        CommentPo commentPo = commentDao.findCommentById(21);
//        Comment commentPo = new Comment();
//        commentPo.setId(54211231);
        System.out.println("Before: " + commentPo);
        commentPo.setContent("Bad");
        commentPo.setStar((short) 1);
        commentPo.setStatusCode((short) 1);

        String jsonString = JacksonUtil.toJson(commentPo);

        String responseString = this.mockMvc.perform(put("/admin/comments/21")
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json;charset=UTF-8")
                .content(jsonString))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println("After: " + commentDao.findCommentById(21));
        System.out.println(responseString);
    }

    @Test
    @Rollback
    void commentDeletionTest() throws Exception {
        // in case it exists
        if (commentDao.findCommentById(22) == null) {
            CommentPo commentPo = new CommentPo();

            commentPo.setContent("Trying out this comment");
            commentPo.setProductId(56);
            commentPo.setStar((short) 5);
            commentPo.setStatusCode((short) 2);
            commentPo.setUserId(234);
            Comment comment = new Comment(commentPo);
            commentService.makeComment(comment);
        }

        String responseString = this.mockMvc.perform(delete("/comments/22").accept(MediaType.APPLICATION_JSON).contentType("application/json;charset=UTF-8").param("id", String.valueOf(562341)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
            System.out.println(commentDao.findCommentById(22));
    }
}
