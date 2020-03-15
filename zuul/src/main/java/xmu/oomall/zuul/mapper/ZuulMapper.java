package xmu.oomall.zuul.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.zuul.domain.Privilege;

import java.util.List;
import java.util.Map;

@Mapper
public interface ZuulMapper {
    List<Privilege> getAllPrivilegeByMethod(String method);
}
