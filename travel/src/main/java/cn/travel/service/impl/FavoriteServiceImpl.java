package cn.travel.service.impl;

import cn.travel.dao.FavoriteDao;
import cn.travel.dao.impl.FavoriteDaoImpl;
import cn.travel.domain.Favorite;
import cn.travel.domain.PageBean;
import cn.travel.domain.Route;
import cn.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {

        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null;//如果对象有值，则为true，反之，则为false
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }

    @Override
    public PageBean<Route> pageQueryFav(int currentPage, int pageSize, int uid){
        //封装PageBean
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount = favoriteDao.findTotalCount(uid);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        System.out.println("totalCount-----------------"+totalCount);
        int start = (currentPage - 1) * pageSize;//开始的记录数
        System.out.println("start"+start);
        List<Route> list = favoriteDao.findByPage(start,pageSize,uid);
        pb.setList(list);
        System.out.println("数"+ list.get(1).getRname());
        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :(totalCount / pageSize) + 1 ;
        pb.setTotalPage(totalPage);
        System.out.println("totalPage-----------------"+totalPage);


        return pb;
    }

}
