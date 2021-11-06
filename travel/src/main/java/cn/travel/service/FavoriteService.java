package cn.travel.service;

import cn.travel.domain.PageBean;
import cn.travel.domain.Route;

public interface FavoriteService {

    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid, int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);

    /**
     * 查找我的收藏
     * @param uid
     */
    public PageBean<Route> pageQueryFav(int currentPage, int pageSize, int uid);

    }
