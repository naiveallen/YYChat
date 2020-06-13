package com.yy.yychat.dao;

import com.yy.yychat.pojo.Message;

import java.util.List;

public interface MsgDao {

    public int insertMsg(Message msg);

    public void batchUpdateMsgRead(List<Integer> msgIds);


}
