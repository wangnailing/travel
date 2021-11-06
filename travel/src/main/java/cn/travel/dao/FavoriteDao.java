package cn.travel.dao;

import cn.travel.domain.Favorite;
import cn.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据rid 查询收藏次数
     * @param rid
     * @return
     */
    public int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param i
     * @param uid
     */
    void add(int i, int uid);

    /**
     * 我的收藏
     * @param uid
     */
    int findTotalCount(int uid);

    List<Route> findByPage(int start, int pageSize, int uid);
}
