package xmu.oomall.comment.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.comment.domain.Comment;
import xmu.oomall.comment.domain.CommentPo;
import xmu.oomall.comment.domain.Log;
import xmu.oomall.comment.eureka.LogService;
import xmu.oomall.comment.service.CommentService;
import xmu.oomall.comment.util.LogUtil;
import xmu.oomall.comment.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author byl
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private LogUtil logUtil;

    /**
     * 对某一product进行评论
     *
     * @param commentPo 评论对象
     * @return commentPo 对象
     */
    @PostMapping(path = "/product/{id}/comments", produces = "application/json;charset=UTF-8")
    public Object createComment(@RequestBody CommentPo commentPo, @PathVariable Integer id, HttpServletRequest request) {

        if (commentPo == null || id == null) {
            return ResponseUtil.badArgument();
        }

        JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("data"));
        try {
            Integer userId = Convert.convert(Integer.class, jsonObject.get("userId").toString());
            commentPo.setUserId(userId);
        } catch (Exception e) {
            return ResponseUtil.badArgumentValue();
        }

        commentPo.setProductId(id);
        Comment comment = new Comment(commentPo);

        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setGmtCreate(localDateTime);
        comment.setGmtModified(localDateTime);

        try {
            logger.debug("Comment: " + commentPo.toString() + " has been received in controller");
            Integer success = commentService.makeComment(comment);
            if (success == 0) {
                return ResponseUtil.insertCommentFailed();
            } else {
                return ResponseUtil.ok(commentPo);
            }
        } catch (Exception e) {
            return ResponseUtil.serious();
        }
    }

    /**
     * 更新评论，审核
     *
     * @param commentPo
     * @return commentPo
     */
    @PutMapping(path = "/admin/comments/{id}", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    public Object updateComment(@RequestBody CommentPo commentPo, @PathVariable Integer id, HttpServletRequest request) {


        if (commentPo == null || id == null) {

            return ResponseUtil.badArgument();
        } else if (commentPo.getId() == null || commentPo.getUserId() == null || commentPo.getProductId() == null || id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            Comment comment = new Comment(commentPo);
            CommentPo oldComment = commentService.findCommentById(commentPo.getId());

            if (commentService.findCommentById(commentPo.getId()) == null) {
                logger.debug("comment with id " + comment.getId() + " doesn't exist");
                return 0;
            }

            if (comment.getContent() == null) {
                comment.setContent(oldComment.getContent());
            }
            if (comment.getStatusCode() == null) {
                comment.setStatusCode(Short.parseShort(oldComment.getContent()));
            }
            if (comment.getStar() == null) {
                comment.setStar(oldComment.getStar());
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            comment.setGmtModified(localDateTime);

            try {
                logger.debug("Comment: " + commentPo.toString() + " has been received in controller");
                Integer success = commentService.editComment(comment);
                if (success == 0) {
                    logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),2,"审核",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.updateCommentFailed();
                } else {
                    logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),2,"审核",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.ok(commentPo);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),2,"审核",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 管理员按条件筛选评论
     *
     * @return commentPo 对象
     */
    @GetMapping("/admin/comments")
    public Object getCommentsByUser(@RequestParam(value = "userId", required = false) Integer userId, @RequestParam(value = "productId", required = false) Integer productId, @RequestParam("page") Integer page, @RequestParam("limit") Integer limit,HttpServletRequest request) {

        if ((userId == null && productId == null) || page == null || limit == null) {
            return ResponseUtil.badArgument();
        } else if (userId < 0 || productId < 0 || page <= 0 || limit <= 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            try {
                logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                List<CommentPo> commentPoList = commentService.findComments(userId, productId, (page - 1) * limit, limit);
                if (commentPoList == null) {
                    return ResponseUtil.findCommentFailed();
                } else {
                    return ResponseUtil.ok(commentPoList);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 获得某一product的所有评论
     *
     * @param productId 产品id
     * @return commentPo 对象
     */
    @GetMapping("/product/{productId}/comments")
    public Object getProductComments(@PathVariable Integer productId, @RequestParam("page") Integer page, @RequestParam("limit") Integer limit,HttpServletRequest request) {

        if (productId == null || page == null || limit == null) {
            return ResponseUtil.badArgument();
        } else if (productId < 0 || page <= 0 || limit <= 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));
            logger.debug("starting to show comments by productId");
            try {
                logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                List<CommentPo> commentPoList = commentService.findCommentsByProduct(productId, (page - 1) * limit, limit);
                if (commentPoList == null) {
                    return ResponseUtil.findCommentFailed();
                } else {
                    return ResponseUtil.ok(commentPoList);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 删除评论
     *
     * @param id 评论id
     */
    @DeleteMapping("/comments/{id}")
    public Object deleteComments(@PathVariable Integer id,HttpServletRequest request) {

        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));
            logger.debug("starting to show comments by productId");
            try {
                Integer success = commentService.deleteComment(id);
                if (success == 0) {
                    logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),3,"删除",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.deleteCommentFailed();
                } else {
                    logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),3,"删除",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.ok();
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),3,"删除",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }
}
