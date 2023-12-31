<%--
  Created by IntelliJ IDEA.
  User: 11849
  Date: 2021/11/21
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="login.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.File" %>
<%@ page import="org.apache.commons.io.FileUtils" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<html>
    <head>
        <title>曲小查</title>
        <link rel="icon" href="/img/logo.ico" type="image/x-icon"/>
        <link rel="stylesheet" type="text/css" href="/css/display.css">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <%
            String UserName = (String)session.getAttribute("loginUser");
            String basepath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+"upLoads/displaySong";
        %>
    </head>
    <body>
        <div class="top_bar">
            <div class="logo_area" style="cursor: pointer">
                <img src="/img/logo.jpg" class="logo" title="退出登录"/>
            </div>
            <div class="top_choice">
                <ul class="selects">
                    <li>
                        <a href="/index.jsp">音乐</a>
                    </li>
                    <li>现场</li>
                    <li>
                        <a href=<%=basepath%>>小曲库</a>
                    </li>
                    <li>探索计划</li>
                </ul>
            </div>
            <div class="search_area">
                <form method="post" action=<%=basepath%> name="search_form">
                    <input placeholder="搜索歌曲、歌手、MV" class="input" name="search" id="searchContent"/>
                    <img src="/img/searchico.png" width="15px" height="15px" style="position: absolute;margin-top: 12px;margin-left: -30px;border-radius: 100%;" onclick="SearchSong()"/>
                    <%--//<input type="submit" style="display: none;">--%>
                </form>

            </div>
            <img src="/img/Avatar.png" width="40px" height="40px" style="margin-left: 140px;border-radius: 100%;" title="您好，<%=UserName%>"/>
        </div>
        <div style="padding-top: 4.375rem;">
            <%
                String id="";
                ResultSet rs=(ResultSet) session.getAttribute("rs");
                String dirPath="E:/TempFiles/TomcatTemp"+"/tempMP3";
                try {
                    while(rs.next()) {
                        id=rs.getString("song_id");

                        InputStream tempInput1=rs.getBlob("song_content").getBinaryStream();
                        InputStream tempInput2=rs.getBlob("song_content").getBinaryStream();
                        File tempFile =new File(dirPath+rs.getString("song_id")+".rar");
                        File playFile=new File(dirPath+rs.getString("song_id")+".mp3");
                        FileUtils.copyInputStreamToFile(tempInput1, tempFile);
                        FileUtils.copyInputStreamToFile(tempInput2, playFile);

                        System.out.println(id);
                        out.print("<div id=SongArea"+id+" class='SongItem'>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td><a href='http://172.17.16.1:8081/tempMP3"+id+".rar' download='"+rs.getString("song_name")+".mp3'>"+rs.getString("song_name")+"</a></td>");
                        out.print("<td>时长："+rs.getInt("song_duration")+"秒</td>");
                        out.print("<td><a href='http://172.17.16.1:8081/tempMP3"+id+".mp3'><img src='/img/display.png' onclick='downloadSong("+id+")'"+ "id='"+id+"' name='formImg'></a></td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</div>");
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
            %>
        </div>
        <script type="text/javascript">
            function DisplaySong(song_id){
                var id=song_id.getAttribute("id");
                var form=document.getElementsByName("downloadForm");
                //form.submit();
                console.log("点击id："+id);
            }
            function SearchSong(){
                var form=document.getElementsByName("search_form");
                form.submit();
            }
            document.onkeydown=function (e){                                        //监听全局按键
                if(e.keyCode==13|e.which ==13){
                    console.log("按下回车");
                    var form=document.getElementsByName("search_form");
                    form.submit();
                }
            }
            $('.logo').click(function (e){
                <%
                    session.setAttribute("loginUser",null);
                    UserName=null;
                %>
                location.reload();
            })
        </script>
    </body>
</html>
