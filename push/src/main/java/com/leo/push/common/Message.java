package com.leo.push.common;


import com.leo.push.utils.Target;

/**
 * 将消息整合成Message model
 * Created by LEO
 * on 2017/7/3.
 */
public final class Message {
    private int notifyID;  //这个字段用于通知的消息类型，在透传中都是默认0
    private String messageID;
    private String title;
    private String message;
    private String extra;

    @Target
    private int target;

    public int getNotifyID() {
        return notifyID;
    }

    public void setNotifyID(int notifyID) {
        this.notifyID = notifyID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public int getTarget() {
        return target;
    }

    /**
     * @param target A {@link Target}.
     */
    public void setTarget(@Target int target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "Message{" +
                "notifyID=" + notifyID +
                ", messageID='" + messageID + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", extra='" + extra + '\'' +
                ", target=" + target +
                '}';
    }
}
