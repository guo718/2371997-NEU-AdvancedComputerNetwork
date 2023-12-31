package upLoads;

import BEAN.SongInfo;
import login.logup;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "uploadSong", value = "/upLoads/uploadSong")
public class uploadSong extends HttpServlet {

    private Connection con=null;
    private PreparedStatement stmt=null;
    private ResultSet rs = null;
    private String sql = "INSERT INTO song_info VALUES(?,?,?,?,?)";
    private String songId="";
    private String songName="";
    private Blob songContent=null;
    private Blob songLyric=null;
    private int songDuration=0;
    public uploadSong(){
        super();

        logup tempdb=new logup();               //调用现有数据库函数

        try {
            this.con=tempdb.Dao();               //连接数据库
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stmt= con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        SongInfo songInfo=new SongInfo();                                          //创建bean类实例
        request.setAttribute("SongInfo",songInfo);                              //request中设置bean
        /*System.out.println(songInfo.getSearchContent());*/
        String tempLyric="";//临时存放歌词信息

        DiskFileItemFactory factory = new DiskFileItemFactory();                    //使用第三方组件
        factory.setSizeThreshold(1024*1024);                                        //设置缓冲区大小
        ServletFileUpload upload = new ServletFileUpload(factory);                  //建立文件上传对象
        File temp = new File(this.getServletContext().getRealPath("temp"));      //临时文件位置
        factory.setRepository(temp);                                                //使用自定义临时文件位置
        List<FileItem> fList = null;
        try {
            fList = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        InputStream in=null;
        String fileName="";

        for (FileItem fileItem : fList) {
            if (fileItem.isFormField()) {
                                                                                    //不是上传文件
                String name = fileItem.getFieldName();
                tempLyric = fileItem.getString();                                   //获取歌词的内容
                System.out.println("普通form项:" + name + "---" + tempLyric);
            } else {
                                                                                    //是上传文件
                songName = fileItem.getName();
                                                                                    //解决文件路径问题
                if (songName.contains("\\")) {
                    songName = songName.substring(songName.lastIndexOf("\\") + 1);
                }
                 in = fileItem.getInputStream();                                    //得到的是文件内容
                System.out.println("文件上传项：" + songName);
            }
        }
        File tempMP3=new File(request.getRequestURI()+"/tempMP3.mp3");
        FileUtils.copyInputStreamToFile(in, tempMP3);                               //将文件内容copy给自定义临时文件
        byte tempCon[]=IOUtils.toByteArray(new FileInputStream(request.getRequestURI()+"/tempMP3.mp3"));
         System.out.println(request.getRequestURI()+"/tempMP3");

        try {
            AudioFile audioFile = AudioFileIO.read(new File(request.getRequestURI()+"/tempMP3.mp3"));
            songDuration = audioFile.getAudioHeader().getTrackLength();             //获取媒体文件的时长
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("文件时长："+songDuration);

        byte lyricByte[]=tempLyric.getBytes(StandardCharsets.ISO_8859_1);//将内容转码
        String lyricString=new String(lyricByte,"UTF-8");
        tempLyric=lyricString;
        try {
            songContent = new SerialBlob(tempCon);                                                                  //将临时文件内容转为Blob类型方便存储
            songLyric=new SerialBlob(tempLyric.getBytes(StandardCharsets.UTF_8));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long tempId=System.currentTimeMillis();
        songId="S_"+tempId;

        int m=0;
        try{
            stmt.setString(1,songId);
            stmt.setString(2,songName);
            stmt.setBlob(3,songContent);
            stmt.setBlob(4,songLyric);
            stmt.setInt(5,songDuration);
        }catch (Exception e){
            System.out.println("属性设置有误");
        }
        try {
            m=stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("执行有误");
        }
        System.out.println("插入返回值m:"+m);

        if(m==1){
            response.sendRedirect("/upLoads/displaySong");
        }
    }
    public void getAllInfo() throws Exception{
        Map<String,String> allSongInfo=new HashMap<>();
        String sql="SELECT * from song_info";

        stmt=con.prepareStatement(sql);
        rs=stmt.executeQuery();
    }
}