package cn.travel.dao.impl;


import cn.travel.dao.RouteDao;
import cn.travel.domain.Route;
import cn.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid,String rname) {
        //String sql = "select count(*) from tab_route where cid = ?";
        //1.定义sql模板
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");

            params.add(cid);//添加？对应的值
        }

        if(rname != null && rname.length() > 0 && !"null".equals(rname)){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }

        sql = sb.toString();
        System.out.println(sql);

        int count = template.queryForObject(sql, Integer.class, params.toArray());
        System.out.println(count);
        return count;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        //String sql = "select * from tab_route where cid = ? and rname like ?  limit ? , ?";
        String sql = " select * from tab_route where 1 = 1 ";
        //1.定义sql模板
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");

            params.add(cid);//添加？对应的值
        }

        if(rname != null && rname.length() > 0 && !"null".equals(rname)){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件

        sql = sb.toString();
        System.out.println(sql);

        params.add(start);
        params.add(pageSize);


        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    @Override
    public int findTotalOrderCount(String rname) {
        //1.定义sql模板
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        System.out.println("rname-------------"+rname);
        if(rname != null && rname.length() > 0 && !"null".equals(rname)){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }
        sb.append("    ORDER BY COUNT DESC  ");
        sql = sb.toString();
        System.out.println("findTotalOrderCount-----"+sql);
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findOrderByPage(int start, int pageSize, String rname) {
        String sql = " select * from tab_route where 1 = 1 ";
        //1.定义sql模板
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(rname != null && rname.length() > 0 && !"null".equals(rname) ){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }
        sb.append("    ORDER BY COUNT DESC  ");

        sb.append(" limit ? , ? ");//分页条件
        sql = sb.toString();
        System.out.println(sql);
        params.add(start);
        params.add(pageSize);


        List<Route> query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        System.out.println(query);
        return query;
    }

    @Override
    public List<Route> findMoreLike() {
        String sql="SELECT * FROM tab_route ORDER BY COUNT DESC LIMIT 0,4";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }

    @Override
    public List<Route> findZhuti() {
        String sql="SELECT * FROM tab_route ORDER BY rid DESC LIMIT 0,4";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));    }

    @Override
    public List<Route> findNew() {
        String sql="SELECT * FROM tab_route ORDER BY rdate DESC  LIMIT 0,4";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }

    @Override
    public List<Route> fingJing() {
        String sql="SELECT * FROM tab_route WHERE cid = 4   LIMIT 0,6";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }

    @Override
    public List<Route> fingGuo() {
        String sql="SELECT * FROM tab_route WHERE cid = 5 LIMIT 0,6";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }

}
