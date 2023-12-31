package login;

import BEAN.logIn_log;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "logIn", urlPatterns = "/login/logIn")
public class logIn extends HttpServlet {
    String message = "";
    String url = "jdbc:mysql://localhost:3306/searchsong?";
    String user = "root";
    String password = "020630";
    String loginUser = "";
    String loginPasswd = "";
    String loginButton = "";
    boolean isLogin = false;
    boolean isLogup = false;
    boolean hasUser = true;
    Connection con = null;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ConnectDB();                                /*连接数据库*/

        byte user[] = req.getParameter("loginUser").getBytes("iso-8859-1");
        loginUser = new String(user, "UTF-8");
        byte passwd[] = req.getParameter("loginPasswd").getBytes("iso-8859-1");
        loginPasswd = new String(passwd, "UTF-8");
        try{
            byte button[] = req.getParameter("button").getBytes("iso-8859-1");
            loginButton = new String(button, "UTF-8");
        }catch (Exception e){
            System.out.println("button为空");
        }

        /*设置编码方式*/
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if (loginButton.equals("注册")) {         //需要注册
            //System.out.println(loginButton);
            resp.sendRedirect("/logup.jsp");
            return;                             //需要清除缓冲区数据，详情讲解：https://qa.1r1g.com/sf/ask/148646011/
        }

        logIn_log log = new logIn_log();              /*实例化bean类*/

        getUser();                                  /*判断用户身份*/

        HttpSession session = req.getSession();     /*获取当前session*/

        if (!hasUser || !isLogup) {                            //用户不存在，前往注册
            log.setHasError(true);
            log.setMessage("用户不存在，点击注册");
            log.setButtonValue("注册");
                                                        /*将bean类放入request中*/
            req.setAttribute("logs", log);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
        if (isLogup) {//身份验证通过
            if (!isLogin) {
                log.setHasError(true);
                log.setMessage("密码错误");

                System.out.println("msg:" + log.getMessage());

                req.setAttribute("logs", log);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            } else {
                //System.out.println("enter else");

                session.setAttribute("loginUser",loginUser);
                resp.sendRedirect("/index.jsp");
            }
        }

    }

    public void destroy() {

    }


    public void init(ServletConfig config) throws ServletException {

    }


    public void ConnectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("链接成功");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("链接失败");
        }
    }

    public void UnConnectDB() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUser() {
        ConnectDB();
        String sql = "SELECT * FROM user_info";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        System.out.println("登录名" + loginUser + " 登录密码" + loginPasswd);
        try {
            stmt = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("查询信息失败");
        }
        try {
            rs = stmt.executeQuery();
            while (rs.next()) {
                //out.println(rs.getString("user_name")+"  "+rs.getString("user_password"));
                if (loginUser.equals(rs.getString("user_name"))) {
                    isLogup = true;//已经注册过了
                    hasUser = true;
                    if (loginPasswd.equals(rs.getString("user_password"))) {
                        isLogin = true;//可以登录
                        //System.out.print("登录");
                        message = "进入曲小查";
                        break;
                    }
                    if (!loginPasswd.equals(rs.getString("user_password"))) {
                        //System.out.print("登录错误");
                        message = "密码错误";
                        break;
                    }
                }
                //System.out.println("未找到");
            }
            if (!hasUser) {
                message = "用户不存在";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("查询信息失败");
        }
        UnConnectDB();
        return user;
    }
}