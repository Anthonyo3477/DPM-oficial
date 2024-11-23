package modules;

public class Chat {
    private String id;
    private String nickName;
    private String lastMessage;
    private String email;
    private String phoneNumber;

    // Constructor completo
    public Chat(String id, String nickName, String lastMessage, String email, String phoneNumber) {
        this.id = id;
        this.nickName = nickName;
        this.lastMessage = lastMessage;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Chat() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", lastMessage='" + lastMessage + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
