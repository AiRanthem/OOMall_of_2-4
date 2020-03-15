package xmu.oomall.address.util;


import java.util.HashMap;
import java.util.Map;

/**
 * 响应操作结果
 * <pre>
 *  {
 *      errno： 错误码，
 *      errmsg：错误消息，
 *      data：  响应数据
 *  }
 * </pre>
 *
 * <p>
 * 错误码：
 * <ul>
 * <li> 0，成功；
 * <li> 4xx，前端错误，说明前端开发者需要重新了解后端接口使用规范：
 * <ul>
 * <li> 401，参数错误，即前端没有传递后端需要的参数；
 * <li> 402，参数值错误，即前端传递的参数值不符合后端接收范围。
 * </ul>
 * <li> 5xx，后端错误，除501外，说明后端开发者应该继续优化代码，尽量避免返回后端错误码：
 * <ul>
 * <li> 501，验证失败，即后端要求用户登录；
 * <li> 502，系统内部错误，即没有合适命名的后端内部错误；
 * <li> 503，业务不支持，即后端虽然定义了接口，但是还没有实现功能；
 * <li> 504，更新数据失效，即后端采用了乐观锁更新，而并发更新时存在数据更新失效；
 * <li> 505，更新数据失败，即后端数据库更新失败（正常情况应该更新成功）。
 * </ul>
 * <li> 6xx，小商城后端业务错误码，
 * 具体见litemall-admin-api模块的AdminResponseCode。
 * <li> 7xx，管理后台后端业务错误码，
 * 具体见litemall-wx-api模块的WxResponseCode。
 * </ul>
 */
public class ResponseUtil {
    public static Object ok() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        return obj;
    }

    public static Object ok(Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        obj.put("data", data);
        return obj;
    }

    public static Object fail() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", -1);
        obj.put("errmsg", "错误");
        return obj;
    }

    public static Object fail(int errno, String errmsg) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", errno);
        obj.put("errmsg", errmsg);
        return obj;
    }

    public static Object badArgument() {
        return fail(401, "参数不对");
    }

    public static Object badArgumentValue() {
        return fail(402, "参数值不对");
    }

    public static Object unlogin() {
        return fail(501, "请登录");
    }

    public static Object serious() {
        return fail(502, "系统内部错误");
    }

    public static Object unsupport() {
        return fail(503, "业务不支持");
    }

    public static Object updatedDateExpired() {
        return fail(504, "更新数据已经失效");
    }

    public static Object updatedDataFailed() {
        return fail(505, "更新数据失败");
    }

    public static Object unauthz() {
        return fail(506, "无操作权限");
    }



    public static Object invalidDefaultFreight() {
        return fail(630, "该默认计重运费规则是无效运费规则（不在数据库里或者逻辑删除）");
    }

    public static Object updateDefaultFreightFailed() {
        return fail(631, "默认计重运费规则更新失败");
    }

    public static Object insertDefaultFreightFailed() {
        return fail(632, "默认计重运费规则添加失败");
    }

    public static Object deleteDefaultFreightFailed() {
        return fail(633, "默认计重运费规则删除失败");
    }

    public static Object invalidDefaultPieceFreight() {
        return fail(634, "该默认计件运费规则是无效运费规则（不在数据库里或者逻辑删除）");
    }

    public static Object updateDefaultPieceFreightFailed() {
        return fail(635, "默认计件运费规则更新失败");
    }

    public static Object insertDefaultPieceFreightFailed() {
        return fail(636, "默认计件运费规则添加失败");
    }

    public static Object deleteDefaultPieceFreightFailed() {
        return fail(637, "默认计件运费规则删除失败");
    }

    public static Object invalidSpecialFreight() {
        return fail(638, "该默认特殊运费规则是无效运费规则（不在数据库里或者逻辑删除）");
    }

    public static Object updateSpecialFreightFailed() {
        return fail(639, "默认特殊运费规则更新失败");
    }

    public static Object insertSpecialFreightFailed() {
        return fail(640, "默认特殊运费规则添加失败");
    }

    public static Object deleteSpecialFreightFailed() {
        return fail(641, "默认特殊运费规则删除失败");
    }

    public static Object unLogin() {
        return fail(660, "用户未登录");
    }

    public static Object usernameBeRegistered() {
        return fail(661, "用户名已被注册");
    }

    public static Object phoneBeRegistered() {
        return fail(662, "手机号已被注册");
    }

    public static Object usernameNotFound() {
        return fail(663, "用户名不存在");
    }

    public static Object errPassword() {
        return fail(664, "登陆密码错误");
    }

    public static Object updateUserFailed() {
        return fail(665, "修改用户信息失败");
    }

    public static Object userNoPermission() {
        return fail(666, "用户无操作权限");
    }

    public static Object errCaptcha() {
        return fail(667, "验证码错误");
    }

    public static Object findUserInfoFailed() {
        return fail(668, "获取用户信息失败");
    }

    public static Object adminUnLogin() {
        return fail(669, "管理员未登录");
    }

    public static Object adminErrPassword() {
        return fail(670, "管理员密码错误");
    }

    public static Object adminNameNotFound() {
        return fail(671, "管理员名不存在");
    }

    public static Object insertAdminFailed() {
        return fail(672, "新增管理员失败");
    }

    public static Object deleteAdminFailed() {
        return fail(673, "删除管理员失败");
    }

    public static Object updateAdminFailed() {
        return fail(674, "更新管理员失败");
    }

    public static Object adminNoPermission() {
        return fail(675, "管理员无操作权限");
    }

    public static Object operaterFailed() {
        return fail(676, "管理员操作失败");
    }

    public static Object findAddressFailed() {
        return fail(720, "获取地址失败");
    }

    public static Object updateAddressFailed() {
        return fail(721, "更新地址失败");
    }

    public static Object roleExist() {
        return fail(750, "角色已存在");
    }

    public static Object deleteRoleFailed() {
        return fail(751, "删除角色失败");
    }

    public static Object updateRoleFailed() {
        return fail(752, "更新角色失败");
    }

    public static Object findRoleFailed() {
        return fail(753, "获取角色失败");
    }

    public static Object findCommentFailed() {
        return fail(902, "获取评论失败");
    }

    public static Object insertCommentFailed() {
        return fail(903, "创建评论失败");
    }

    public static Object updateCommentFailed() {
        return fail(904, "修改评论失败");
    }

    public static Object deleteCommentFailed() {
        return fail(905, "删除评论失败");
    }


}

