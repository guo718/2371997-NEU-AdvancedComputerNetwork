package login;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "logUp",urlPatterns = "/login/logUp")
public class logup extends HttpServlet {
    String message="";
    String url="jdbc:mysql://localhost:3306/searchsong";
    String user="root";
    String password="020630";
    String userId="";
    String loginUser="";
    String loginPasswd="";
    Connection con=null;
    PreparedStatement mysql;
    Statement stmt;
    ResultSet rs=null;
    boolean isSuccess=false;

    public void destroy() {
        super.destroy();
    }


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            con=Dao();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接错误");
        }

        long temp=System.currentTimeMillis();
        userId=temp+"";

        byte user[]=req.getParameter("loginUser").getBytes("iso-8859-1");
        loginUser=new String(user,"UTF-8");
        byte passwd[]=req.getParameter("loginPasswd").getBytes("iso-8859-1");
        loginPasswd=new String(passwd,"UTF-8");

        String sql = "INSERT INTO user_info VALUES(?,?,?)";

        try {
            mysql = con.prepareStatement(sql);
            mysql.setString(1,userId);
            mysql.setString(2,loginUser);
            mysql.setString(3,loginPasswd);
            int m = mysql.executeUpdate();
            System.out.println("执行返回值：" + m);
            con.close();
            System.out.println("插入信息成功");
            isSuccess=true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("插入信息连接错误");
        }

        if(isSuccess){
            HttpSession session=req.getSession();
            session.setAttribute("loginUser",loginUser);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
    }

    public Connection Dao() throws Exception{                              //连接数据库
        Connection con;
        Class.forName("com.mysql.cj.jdbc.Driver");
        con= DriverManager.getConnection(url,user,password);
        System.out.println("链接成功");
        return con;
    }
}
