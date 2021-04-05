package ultratude.com.mzizi.eventbus;

public class MessageBackChat {

    private String chatMessage;

    public MessageBackChat(String chatMessage){
        this.chatMessage = chatMessage;
    }

    public String getChatMessage() {
        return chatMessage;
    }
}
