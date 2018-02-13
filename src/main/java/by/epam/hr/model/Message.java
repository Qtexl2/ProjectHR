package by.epam.hr.model;

import java.sql.Timestamp;

/**
 * The Class Message.
 */
public class Message extends Entity{

    /** The message id. */
    private Long messageId;

    /** The message text. */
    private String messageText;

    /** The message time. */
    private Timestamp messageTime;

    /** The profile sender id. */
    private Long profileSenderId;

    /** The profile reception id. */
    private Long profileReceptionId;

    /**
     * Instantiates a new message.
     */
    public Message(){
    }

    /**
     * Instantiates a new message.
     *
     * @param messageText the message text
     * @param profileSenderId the profile sender id
     * @param profileReceptionId the profile reception id
     */
    public Message(String messageText, Long profileSenderId, Long profileReceptionId) {
        this.messageText = messageText;
        this.profileSenderId = profileSenderId;
        this.profileReceptionId = profileReceptionId;
    }

    /**
     * Gets the message id.
     *
     * @return the message id
     */
    public Long getMessageId() {
        return messageId;
    }

    /**
     * Sets the message id.
     *
     * @param messageId the new message id
     */
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    /**
     * Gets the message text.
     *
     * @return the message text
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * Sets the message text.
     *
     * @param messageText the new message text
     */
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     * Gets the message time.
     *
     * @return the message time
     */
    public Timestamp getMessageTime() {
        return messageTime;
    }

    /**
     * Sets the message time.
     *
     * @param messageTime the new message time
     */
    public void setMessageTime(Timestamp messageTime) {
        this.messageTime = messageTime;
    }

    /**
     * Gets the profile sender id.
     *
     * @return the profile sender id
     */
    public Long getProfileSenderId() {
        return profileSenderId;
    }

    /**
     * Sets the profile sender id.
     *
     * @param profileSenderId the new profile sender id
     */
    public void setProfileSenderId(Long profileSenderId) {
        this.profileSenderId = profileSenderId;
    }

    /**
     * Gets the profile reception id.
     *
     * @return the profile reception id
     */
    public Long getProfileReceptionId() {
        return profileReceptionId;
    }

    /**
     * Sets the profile reception id.
     *
     * @param profileReceptionId the new profile reception id
     */
    public void setProfileReceptionId(Long profileReceptionId) {
        this.profileReceptionId = profileReceptionId;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
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

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = messageId != null ? messageId.hashCode() : 0;
        result = 31 * result + (messageText != null ? messageText.hashCode() : 0);
        result = 31 * result + (profileSenderId != null ? profileSenderId.hashCode() : 0);
        result = 31 * result + (profileReceptionId != null ? profileReceptionId.hashCode() : 0);
        return result;
    }

    /**
     * @see java.lang.Object#toString()
     */
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
