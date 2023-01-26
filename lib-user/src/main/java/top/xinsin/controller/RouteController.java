package top.xinsin.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xinsin.pojo.Route;
import top.xinsin.pojo.Vo.RouteVO;
import top.xinsin.pojo.Vo.RouterNameVO;
import top.xinsin.services.RouteService;
import top.xinsin.utils.RData;

import java.util.List;

/**
 * @author xinsin
 * Created On 2022/12/24 16:05
 * @version 1.0
 */
@RestController
public class RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }
    @GetMapping("/user/sys/getRoutes")
    public RData<JSONObject> getRoutes(@RequestParam("page")Integer page, @RequestParam("pageNum")Integer pageNum){return routeService.getRoutes(page,pageNum);}
    @PostMapping("/user/sys/addRoute")
    public RData<String> addRoute(@RequestBody Route route){return routeService.addRoute(route);}
    @GetMapping("/user/sys/updateStatus")
    public RData<String> updateStatus(@RequestParam("id")Integer id,@RequestParam("status")Integer status){return routeService.updateStatus(id,status);}
    @PostMapping("/user/sys/updateRoute")
    public RData<String> updateRoute(@RequestBody Route route){return routeService.updateRoute(route);}
    @GetMapping("/user/api/getRouter")
    public RData<List<RouteVO>> getRouter(@RequestParam("auth")String auth){return routeService.getRouter(auth);}
    @GetMapping("/user/sys/getRouteName")
    public RData<List<RouterNameVO>> getRouteName(){return routeService.getRouteName();}
}
