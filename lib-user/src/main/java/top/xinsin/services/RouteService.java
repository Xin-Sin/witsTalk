package top.xinsin.services;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xinsin.dao.RouteMapper;
import top.xinsin.pojo.Route;
import top.xinsin.pojo.Vo.RouteVO;
import top.xinsin.pojo.Vo.RouterNameVO;
import top.xinsin.utils.RData;

import java.util.List;

/**
 * @author xinsin
 * Created On 2022/12/24 16:05
 * @version 1.0
 */
@Service
@Slf4j
public class RouteService {
    private final RouteMapper routeMapper;
    @Autowired
    public RouteService(RouteMapper routeMapper) {
        this.routeMapper = routeMapper;
    }
    public RData<JSONObject> getRoutes(Integer page, Integer pageNum) {
        List<Route> routes = routeMapper.selectRoute(page,pageNum);
        Integer count = routeMapper.selectCount();
        return RData.success(new JSONObject().fluentPut("routes",routes).fluentPut("total",count));
    }

    public RData<String> addRoute(Route route) {
        Integer num = routeMapper.insert(route);
        return RData.success(num.toString());
    }

    public RData<String> updateStatus(Integer id, Integer status) {
        Integer num = routeMapper.updateStatusById(id,status);
        return RData.success(num.toString());
    }

    public RData<String> updateRoute(Route route) {
        Integer num = routeMapper.updateRouteById(route);
        return RData.success(num.toString());
    }

    public RData<List<RouteVO>> getRouter(String auth) {
        List<RouteVO> routeVOS = routeMapper.SelectRouteByAuth(auth);
        return RData.success(routeVOS);
    }

    public RData<List<RouterNameVO>> getRouteName() {
        List<RouterNameVO> routeName = routeMapper.getRouteName();
        return RData.success(routeName);
    }
}
