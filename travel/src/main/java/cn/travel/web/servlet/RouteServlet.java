package cn.travel.web.servlet;

import cn.travel.domain.PageBean;
import cn.travel.domain.Route;
import cn.travel.domain.User;
import cn.travel.service.FavoriteService;
import cn.travel.service.RouteService;
import cn.travel.service.impl.FavoriteServiceImpl;
import cn.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //接受rname 线路名称
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");


        int cid = 0;//类别id
        //2.处理参数
        if(cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if(currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if(pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        //3. 调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);
        System.out.println("aaaaaaaaaaaa"+pb.getTotalPage());

        //4. 将pageBean对象序列化为json，返回
        writeValue(pb,response);

    }

    /**
     * 根据id查询一个旅游线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.接收id
        String rid = request.getParameter("rid");
        //2.调用service查询route对象
        Route route = routeService.findOne(rid);
        //3.转为json写回客户端
        writeValue(route,response);
    }

    /**
     * 判断当前登录用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取线路id
        String rid = request.getParameter("rid");

        //2. 获取当前登录的用户 user
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            uid = 0;
        }else{
            //用户已经登录
            uid = user.getUid();
        }

        //3. 调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);

        //4. 写回客户端
        writeValue(flag,response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取线路rid
        String rid = request.getParameter("rid");
        //2. 获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            return ;
        }else{
            //用户已经登录
            uid = user.getUid();
        }


        //3. 调用service添加
        favoriteService.add(rid,uid);

    }

    /**
     * 收藏列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQueryOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        //接受rname 线路名称
        String rname = request.getParameter("rname");
        if (rname != null) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }
        int cid = 0;//类别id
        //2.处理参数
        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if(currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if(pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 8;
        }

        //3. 调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQueryOrder(currentPage, pageSize,rname);
        System.out.println(pb);
        //4. 将pageBean对象序列化为json，返回
        writeValue(pb,response);

    }

    /**
     * 我的收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQueryFav(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取当前登录的用户

        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            return ;
        }else{
            //用户已经登录
            uid = user.getUid();
        }

        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        //2.处理参数
        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if(currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if(pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 12;
        }
        //2. 调用service添加
        PageBean<Route> favorite = favoriteService.pageQueryFav(currentPage,pageSize,uid);
        writeValue(favorite,response);
    }

    /**
     * 人气旅游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findMoreLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> route=routeService.findMoreLike();
        //3.转为json写回客户端
        writeValue(route,response);
    }

    /**
     * 主题旅游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findZhuti(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> route=routeService.findZhuti();
        //3.转为json写回客户端
        writeValue(route,response);
    }

    /**
     * 最新旅游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> route = routeService.findNew();
        //3.转为json写回客户端
        writeValue(route, response);
    }

    public void fingGuo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> route = routeService.fingGuo();
        //3.转为json写回客户端
        writeValue(route, response);
    }

    public void fingJing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> route = routeService.fingJing();
        //3.转为json写回客户端
        writeValue(route, response);
    }
    }

