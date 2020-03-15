package xmu.oomall.address.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Region;
import xmu.oomall.address.service.AddressService;
import xmu.oomall.address.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 陆俊伟
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    /**
     * 添加地址
     *
     * @param addressPo 地址信息
     * @return 添加地址操作结果
     * //
     */
    @PostMapping("/addresses")
    public Object addAddress(@RequestBody AddressPo addressPo, HttpServletRequest request) {

        if (addressPo == null) {
            return ResponseUtil.badArgumentValue();
        }

        String userId;
        JSONObject jsonObject;
        try {
            jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            userId = jsonObject.get("userId").toString();
        } catch (Exception e) {
            return ResponseUtil.badArgumentValue();
        };

        logger.debug("address对象： " + addressPo);

        Address address = new Address(addressPo);

        address.setUserId(userId);
        addressPo.setUserId(userId);
        LocalDateTime localDateTime = LocalDateTime.now();
        address.setGmtCreate(localDateTime);
        address.setGmtModified(localDateTime);

        try {
            Integer success = addressService.addAddress(address);
            logger.debug("address添加成功：" + success);
            if (success == 0) {
                return ResponseUtil.updateAddressFailed();
            } else {
                return ResponseUtil.ok(addressPo);
            }
        } catch (Exception e) {
            return ResponseUtil.serious();
        }
    }

    /**
     * 修改地址
     *
     * @param addressPo 地址信息
     * @return 修改地址操作结果
     * //
     */
    @PutMapping("/addresses/{id}")
    public Object updateAddress(@PathVariable Integer id, @RequestBody AddressPo addressPo) {

        if (id == null || addressPo == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        logger.debug("address对象： " + addressPo);

        addressPo.setId(id);

        Address address = new Address(addressPo);
        LocalDateTime localDateTime = LocalDateTime.now();
        address.setGmtModified(localDateTime);

        try {
            Integer success = addressService.updateAddress(address);
            logger.debug("address修改成功：" + success);
            if (success == 0) {
                return ResponseUtil.updateAddressFailed();
            } else {
                return ResponseUtil.ok(addressPo);
            }
        } catch (Exception e) {
            return ResponseUtil.serious();
        }
    }

    /**
     * 用户获得所有地址
     *
     * @return 地址列表
     */
    @GetMapping("/addresses")
    public Object findAllAddress(@RequestParam Integer page, @RequestParam Integer limit, HttpServletRequest request) {

        if (page == null || limit == null) {
            return ResponseUtil.badArgument();
        } else if (page <= 0 || limit <= 0) {
            return ResponseUtil.badArgumentValue();
        }

        JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
        Integer userId;
        try {
            userId = Convert.convert(Integer.class, jsonObject.get("userId").toString());
        } catch (Exception e) {
            return ResponseUtil.badArgumentValue();
        }

        logger.debug("操作用户的id：" + userId);

        try {
            List<AddressPo> addressPoList = addressService.findAllAddress(userId, (page - 1) * limit, limit);
            if (addressPoList == null) {
                return ResponseUtil.findAddressFailed();
            } else {
                return ResponseUtil.ok(addressPoList);
            }
        } catch (Exception e) {
            return ResponseUtil.serious();
        }
    }

    /**
     * 根据id获得地址信息
     *
     * @param id 地址id
     * @return 地址对象
     */
    @GetMapping("/addresses/{id}")
    public Object findAddressById(@PathVariable Integer id) {

        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        logger.debug("需要查询的address对象id：" + id);

        try {
            AddressPo addressPo = addressService.findAddressById(id);
            if (addressPo == null) {
                return ResponseUtil.findAddressFailed();
            } else {
                return ResponseUtil.ok(addressPo);
            }
        } catch (Exception e) {
            return ResponseUtil.serious();
        }
    }

    /**
     * 删除选定地址
     *
     * @return int
     */
    @DeleteMapping("/addresses/{id}")
    public Object clearAddress(@PathVariable Integer id) {

        if (id == null) {
            return ResponseUtil.badArgument();
        }

        logger.debug("需要删除的address对象id：" + id);

        try {
            Integer success = addressService.clearAddress(id);
            logger.debug("address对象成功被删除：" + success);
            if (success == 0) {
                return ResponseUtil.updateAddressFailed();
            } else {
                return ResponseUtil.ok();
            }
        } catch (Exception e) {
            return ResponseUtil.serious();
        }
    }

    /**
     * 根据id获得地区信息
     *
     * @param id 地区id
     * @return 地区对象
     */
    @GetMapping("/region/{id}")
    public Object findRegionById(@PathVariable Integer id) {

        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        logger.debug("region对象id：" + id);

        try {
            return ResponseUtil.ok(addressService.findRegionById(id));
        } catch (Exception e) {
            return ResponseUtil.serious();
        }
    }

    /**
     * 根据父级地区id获得所有子级地区列表
     *
     * @param pid 父级地区id
     * @return 地区对象
     */
    @GetMapping("/regions/{pid}")
    public Object findRegionByPid(@PathVariable Integer pid) {

        if (pid == null) {
            return ResponseUtil.badArgument();
        } else if (pid < 0) {
            return ResponseUtil.badArgumentValue();
        }

        logger.debug("region父级对象id：" + pid);

        try {
            return ResponseUtil.ok(addressService.findRegionsByPid(pid));
        } catch (Exception e) {
            return ResponseUtil.serious();
        }
    }
}