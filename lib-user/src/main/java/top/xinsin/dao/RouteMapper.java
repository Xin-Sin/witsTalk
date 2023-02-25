package top.xinsin.dao;

import org.apache.ibatis.annotations.Mapper;
import top.xinsin.pojo.Route;
import top.xinsin.pojo.Vo.RouteVO;
import top.xinsin.pojo.Vo.RouterNameVO;

import java.util.List;

/**
 * @author xinsin
 * Created On 2022/12/24 16:05
 * @version 1.0
 */
@Mapper
public interface RouteMapper {
    List<Route> selectRoute(Integer page, Integer pageNum);

    Integer selectCount();

    Integer insert(Route route);

    Integer updateStatusById(Integer id, Integer status);

    Integer updateRouteById(Route route);

    List<RouteVO> SelectRouteByAuth(String auth);

    List<RouterNameVO> getRouteName();

    void initTable();
}
