package Datarelated;

public class User {
    private String id;
    private String pw;
    private String nickName;


    public User(String id, String pw, String nickName) {
        setId(id);
        setPw(pw);
        setNickName(nickName);
    }
    public User(String id) {
        setId(id);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof User)) {
            return false;
        }
        User temp = (User)o;

        return id.equals(temp.getId());
    }

    @Override
    public String toString() {
        String info = "Id: " + id + "\n";
        info += "Pw: " + pw + "\n";
        info += "NickName: " + nickName + "\n";
        return info;
    }
}