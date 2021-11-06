package cn.travel.dao.impl;

import cn.travel.dao.FavoriteDao;
import cn.travel.domain.Favorite;
import cn.travel.domain.Route;
import cn.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;


public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = " select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid = ?";

         return template.queryForObject(sql,Integer.class,rid);

    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";

        template.update(sql,rid,new Date(),uid);
    }

    @Override
    public int findTotalCount(int uid) {
        String sql = " select count(*) from tab_favorite where uid = ?";
        int count = template.queryForObject(sql, Integer.class, uid);
        System.out.println(count);
        return count;
    }

@Override
public List<Route> findByPage(int start, int pageSize, int uid) {
        String sql = " select * from tab_favorite t,tab_route r where t.rid = r.rid and t.uid = ? limit ? , ?";

        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),uid,start,pageSize);
        }

        }
