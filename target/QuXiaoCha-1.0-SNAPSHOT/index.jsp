<%@ page import="BEAN.SongInfo" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="SongInfo" class="BEAN.SongInfo" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>曲小查</title>
    <link rel="icon" href="/img/logo.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <%
        String UserName = (String)session.getAttribute("loginUser");
        System.out.print(UserName);
        request.setCharacterEncoding("UTF-8");
        String basepath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+"upLoads/uploadSong";
        String basepath1=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+"upLoads/displaySong";
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
                    <a href=<%=basepath1%>>小曲库</a>
                </li>
                <li>探索计划</li>
            </ul>
        </div>
        <div class="search_area">
            <input placeholder="搜索歌曲、歌手、MV" type="search" class="input" name="search" disabled/>
        </div>
        <img src="/img/Avatar.png" width="40px" height="40px" style="margin-left: 140px;border-radius: 100%;" title="您好，<%=UserName%>"/>
    </div>
    <div style="padding-top: 4.375rem;" class="maintain"><%--style="padding-top: 4.375rem;"--%>
        <form method="post" enctype="multipart/form-data" action=<%=basepath%>>
            <input type="file" name="songContent" value="选择歌曲" accept=".mp3" class="TrueFileSelect">
            <div class="fileSelect">
                选择歌曲
            </div>
            <div class="submitSelect">
                添加到曲库
            </div>
            <input name="songLyric" placeholder="此处键入歌词" class="LyricArea">
            <input type="submit" value="上传歌曲" class="TrueSubmitSelect">
        </form>
    </div>
    <div class="filled">
        <textarea class="showLyric" placeholder="歌词详情"></textarea>
    </div>
    <div class="filled_other">
        <strong>About</strong><br>
        曲小查是国内领先的歌曲搜索平台，支持MP3媒体文件的上传、播放及下载，更多功能期待您的发现。
    </div>
    <script>
        $(document).ready(function (e){
            $('.fileSelect').click(function (e){
                $('.TrueFileSelect').click();
            })
        })
        $(document).ready(function (e){
            $('.submitSelect').click(function (e){
                $('.TrueSubmitSelect').click();
            })
        })
        $(".LyricArea").change(function(){
            var vue = $(this).val();
            $(".showLyric").val(vue);
        })
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


