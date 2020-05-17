package com.yy.yychat.pojo;

import java.util.Date;
import javax.persistence.*;

public class Message {
    @Id
    private Integer id;

    @Column(name = "sender_id")
    private Integer senderId;

    @Column(name = "accpter_id")
    private Integer accpterId;

    private String msg;

    @Column(name = "is_read")
    private Integer isRead;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return sender_id
     */
    public Integer getSenderId() {
        return senderId;
    }

    /**
     * @param senderId
     */
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    /**
     * @return accpter_id
     */
    public Integer getAccpterId() {
        return accpterId;
    }

    /**
     * @param accpterId
     */
    public void setAccpterId(Integer accpterId) {
        this.accpterId = accpterId;
    }

    /**
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return is_read
     */
    public Integer getIsRead() {
        return isRead;
    }

    /**
     * @param isRead
     */
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}