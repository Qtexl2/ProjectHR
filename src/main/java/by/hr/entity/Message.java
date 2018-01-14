package by.hr.entity;

import java.sql.Timestamp;

public class Message extends Entity{
    private Long messageID;
    private String messageText;
    private Timestamp messageTime;
    private Long profileSenderID;
    private Long profileReceptionID;

    public Message(){
    }

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Timestamp getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Timestamp messageTime) {
        this.messageTime = messageTime;
    }

    public Long getProfileSenderID() {
        return profileSenderID;
    }

    public void setProfileSenderID(Long profileSenderID) {
        this.profileSenderID = profileSenderID;
    }

    public Long getProfileReceptionID() {
        return profileReceptionID;
    }

    public void setProfileReceptionID(Long profileReceptionID) {
        this.profileReceptionID = profileReceptionID;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageID=" + messageID +
                ", messageText='" + messageText + '\'' +
                ", messageTime=" + messageTime +
                ", profileSenderID=" + profileSenderID +
                ", profileReceptionID=" + profileReceptionID +
                '}';
    }
}
