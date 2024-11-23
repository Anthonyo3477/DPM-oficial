package modules;

public class Chat {
    private String title;
    private String lastMessage;

    public Chat(String title, String lastMessage) {
        this.title = title;
        this.lastMessage = lastMessage;
    }

    public String getTitle() {
        return title;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Chat chat = (Chat) obj;
        return title.equals(chat.title);
    }
}
