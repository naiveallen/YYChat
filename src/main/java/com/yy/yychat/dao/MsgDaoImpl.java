package com.yy.yychat.dao;

import com.yy.yychat.mapper.CustomMapper;
import com.yy.yychat.mapper.MessageMapper;
import com.yy.yychat.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MsgDaoImpl implements MsgDao{

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private CustomMapper customMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertMsg(Message msg) {
        int id = messageMapper.insert(msg);
        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void batchUpdateMsgRead(List<Integer> msgIds) {
        customMapper.batchUpdateMsgSigned(msgIds);
    }


}
