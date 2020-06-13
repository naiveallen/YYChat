package com.yy.yychat.netty;

import com.yy.yychat.enums.MsgActionEnum;
import com.yy.yychat.service.UserService;
import com.yy.yychat.utils.JsonUtils;
import com.yy.yychat.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String content = msg.text();
        Channel currentChannel = ctx.channel();
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();

        if (action == MsgActionEnum.CONNECT.type) {
            int senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(senderId, currentChannel);

            for (Channel c : users) {
                System.out.println(c.id().asLongText());
            }
            UserChannelRel.output();
        } else if (action == MsgActionEnum.CHAT.type) {
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgText = chatMsg.getMsg();
            int receiverId = chatMsg.getReceiverId();
            int senderId = chatMsg.getSenderId();

            UserService userService = (UserService) SpringUtil.getBean("userServiceImpl");
            int msgId = userService.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);

            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMsg(chatMsg);

            Channel receiverChannel = UserChannelRel.get(receiverId);

            if (receiverChannel == null) {
                System.out.println("user is off-line.");
            } else {
                Channel findChannel = users.find(receiverChannel.id());
                if (findChannel != null) {
                    receiverChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    JsonUtils.objectToJson(dataContentMsg)));
                } else {
                    System.out.println("user is off-line.");
                }
            }
        } else if (action == MsgActionEnum.SIGNED.type) {
            UserService userService = (UserService)SpringUtil.getBean("userServiceImpl");
            String msgIdsStr = dataContent.getExtend();
            String msgIds[] = msgIdsStr.split(",");

            List<Integer> msgIdList = new ArrayList<>();
            for (String mid : msgIds) {
                if (StringUtils.isNotBlank(mid)) {
                    msgIdList.add(Integer.valueOf(mid));
                }
            }

            System.out.println(msgIdList.toString());

            if (msgIdList != null && !msgIdList.isEmpty() && msgIdList.size() > 0) {
                userService.updateMsgSigned(msgIdList);
            }

        } else if (action == MsgActionEnum.KEEPALIVE.type) {
            System.out.println("Received channel[" + currentChannel + "] heartbeat...");
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        users.add(channel);
        System.out.println(channel.id().asShortText() + "added.");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.id().asShortText() + "leave.");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
