package com.db.witt.project_witt;

public class CustomComment {

    private String UserID;
    private String UserNickname;
    private String Content;
    private String Comment_date;

    public CustomComment(){}

    public CustomComment(String userID, String userNickname, String content, String comment_date) {
        UserID = userID;
        UserNickname = userNickname;
        Content = content;
        Comment_date = comment_date;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserNickname() {
        return UserNickname;
    }

    public void setUserNickname(String userNickname) {
        UserNickname = userNickname;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {Content = content;}

    public String getComment_date() {
        return Comment_date;
    }

    public void setComment_date(String comment_date) {Comment_date = comment_date;}
}

