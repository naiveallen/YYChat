package com.yy.yychat.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "friends_request")
public class FriendsRequest {
    @Id
    private Integer id;

    @Column(name = "sender_id")
    private Integer senderId;

    @Column(name = "accepter_id")
    private Integer accepterId;

    @Column(name = "request_time")
    private Date requestTime;

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
     * @return accepter_id
     */
    public Integer getAccepterId() {
        return accepterId;
    }

    /**
     * @param accepterId
     */
    public void setAccepterId(Integer accepterId) {
        this.accepterId = accepterId;
    }

    /**
     * @return request_time
     */
    public Date getRequestTime() {
        return requestTime;
    }

    /**
     * @param requestTime
     */
    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
}