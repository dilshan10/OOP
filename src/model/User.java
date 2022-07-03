package model;

public class User {
    private String User_ID;
    private String User_Name;
    private String User_PassWord;

    public User(String user_id) {}

    public User(String user_ID, String user_Name, String user_PassWord) {
        User_ID = user_ID;
        User_Name = user_Name;
        User_PassWord = user_PassWord;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_PassWord() {
        return User_PassWord;
    }

    public void setUser_PassWord(String user_PassWord) {
        User_PassWord = user_PassWord;
    }

    @Override
    public String toString() {
        return "User{" +
                "User_ID='" + User_ID + '\'' +
                ", User_Name='" + User_Name + '\'' +
                ", User_PassWord='" + User_PassWord + '\'' +
                '}';
    }
}
