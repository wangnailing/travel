package cn.travel.service;

import cn.travel.domain.PageBean;
import cn.travel.domain.Route;

import java.util.List;

/**
 * 线路Service
 */
public interface RouteService {

    /**
     * 根据类别进行分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);

    /**
     * 根据id查询
     * @param rid
     * @return
     */
    public Route findOne(String rid);
    /**
     * 根据类别进行分页查询排序
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageBean<Route> pageQueryOrder(int currentPage, int pageSize, String rname);

    /**
     * 人气旅游
     * @return
     */
    List<Route> findMoreLike();

    List<Route> findNew();

    List<Route> findZhuti();

    List<Route> fingJing();

    List<Route> fingGuo();
}
