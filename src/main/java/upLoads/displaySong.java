package upLoads;

import BEAN.SongInfo;
import login.logup;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "displaySong",value = "/upLoads/displaySong")
public class displaySong extends HttpServlet {
    private String SearchContent=null;
    private String CommonSQL="SELECT * FROM song_info";
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private Connection con=null;
    public displaySong(){
        logup temp=new logup();
        try {
            con=temp.Dao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SongInfo bean=new SongInfo();                                               //初始化bean
        HttpSession session =req.getSession();
        ResultSet tempRs=null;
        try{
            byte tempContent[]=req.getParameter("search").getBytes(StandardCharsets.ISO_8859_1);
            SearchContent=new String(tempContent,"UTF-8");
        }catch(Exception e){
            System.out.println("搜索框内容为空");
        }
        System.out.println("搜索框内容"+SearchContent);
        if(SearchContent!=null){
            CommonSQL="SELECT * FROM song_info WHERE song_name LIKE '%"+SearchContent+"%' OR `song_lyric` LIKE '%"+SearchContent+"%'";
            System.out.println(CommonSQL);
        }
        try {
            stmt = con.prepareStatement(CommonSQL);
            rs = stmt.executeQuery();
            //tempRs=stmt.executeQuery();
        }catch (Exception e){
            System.out.println(e);
        }
        /*Map<String,String> downLoadInfo=new HashMap<String,String>();
        String dirPath="E:/TempFiles/TomcatTemp"+"/tempMP3";
        try {
            while (tempRs.next()){
                InputStream tempInput=tempRs.getBlob("song_content").getBinaryStream();
                File tempFile =new File(dirPath+tempRs.getString("song_id")+".mp3");
                FileUtils.copyInputStreamToFile(tempInput, tempFile);
                downLoadInfo.put(tempRs.getString("song_id"),dirPath+tempRs.getString("song_id")+".mp3");
                //System.out.println(tempRs.getString("song_id"));
            }
        }catch (Exception e){
            System.out.println(e);
        }*/
        session.setAttribute("rs",rs);
        //session.setAttribute("downLoadInfo",downLoadInfo);
        resp.sendRedirect("/jsp/display.jsp");
    }

    public void destroy() {
        super.destroy();
    }


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
}
