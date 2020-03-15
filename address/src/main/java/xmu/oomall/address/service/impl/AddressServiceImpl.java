package xmu.oomall.address.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import xmu.oomall.address.dao.AddressDao;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Region;
import xmu.oomall.address.service.AddressService;
import xmu.oomall.address.util.Config;

import java.util.List;

/**
 * @author 陆俊伟
 * @description
 * @date Created in 17:58 2019/12/05
 **/

@Repository
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressDao addressDao;

    @Autowired
    Config config;

    @Override
    public Integer addAddress(Address address) {
        return addressDao.addAddress(address);
    }

    @Override
    public Integer updateAddress(Address address) {
        return addressDao.updateAddress(address);
    }

    @Override
    public AddressPo findAddressById(Integer id) {
        return addressDao.getAddressById(id);
    }

    @Override
    public Integer clearAddress(Integer id) {
        return addressDao.deleteAddress(id);
    }

    @Override
    public Region findRegionById(Integer id) {
        return addressDao.getRegionById(id);
    }

    @Override
    public List<Region> findRegionsByPid(Integer pid) {
        return addressDao.getRegionsByPid(pid);
    }

    @Override
    public List<AddressPo> findAllAddress(Integer userId, Integer offSet, Integer limit) {
        return addressDao.getAllAddress(userId, offSet, limit);
    }
}
