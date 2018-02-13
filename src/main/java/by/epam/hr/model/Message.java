package by.epam.hr.model;

import java.sql.Timestamp;

public class Message extends Entity{
    private Long messageId;
    private String messageText;
    private Timestamp messageTime;
    private Long profileSenderId;
    private Long profileReceptionId;

    public Message(){
    }

    public Message(String messageText, Long profileSenderId, Long profileReceptionId) {
        this.messageText = messageText;
        this.profileSenderId = profileSenderId;
        this.profileReceptionId = profileReceptionId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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

    public Long getProfileSenderId() {
        return profileSenderId;
    }

    public void setProfileSenderId(Long profileSenderId) {
        this.profileSenderId = profileSenderId;
    }

    public Long getProfileReceptionId() {
        return profileReceptionId;
    }

    public void setProfileReceptionId(Long profileReceptionId) {
        this.profileReceptionId = profileReceptionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (messageId != null ? !messageId.equals(message.messageId) : message.messageId != null) return false;
        if (messageText != null ? !messageText.equals(message.messageText) : message.messageText != null) return false;
        if (profileSenderId != null ? !profileSenderId.equals(message.profileSenderId) : message.profileSenderId != null)
            return false;
        return profileReceptionId != null ? profileReceptionId.equals(message.profileReceptionId) : message.profileReceptionId == null;
    }

    @Override
    public int hashCode() {
        int result = messageId != null ? messageId.hashCode() : 0;
        result = 31 * result + (messageText != null ? messageText.hashCode() : 0);
        result = 31 * result + (profileSenderId != null ? profileSenderId.hashCode() : 0);
        result = 31 * result + (profileReceptionId != null ? profileReceptionId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", messageText='" + messageText + '\'' +
                ", messageTime=" + messageTime +
                ", profileSenderId=" + profileSenderId +
                ", profileReceptionId=" + profileReceptionId +
                '}';
    }
}
