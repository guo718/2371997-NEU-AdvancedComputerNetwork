package BEAN;


public class SongInfo {

  private String songId;
  private String songName;
  private String songContent;
  private String song_Lyric;
  private String searchContent;

  public String getSearchContent(){
    return searchContent;
  }

  public void setSearchContent(String searchContent) {
    this.searchContent = searchContent;
  }

  public String getSongId() {
    return songId;
  }

  public void setSongId(String songId) {
    this.songId = songId;
  }


  public String getSongName() {
    return songName;
  }

  public void setSongName(String songName) {
    this.songName = songName;
  }


  public String getSongContent() {
    return songContent;
  }

  public void setSongContent(String songContent) {
    this.songContent = songContent;
  }


  public String getSong_Lyric() {
    return song_Lyric;
  }

  public void setSong_Lyric(String song_Lyric) {
    this.song_Lyric = song_Lyric;
  }

}
