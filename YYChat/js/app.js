window.app = {
	
	nettyServerUrl: 'ws://10.0.0.59:9999/chat',
	
	serverUrl: 'http://10.0.0.59:8888/',
	
	isNotNull: function(str) {
		if (str != null && str != "" && str != undefined) {
			return true;
		}
		return false;
	},

	showToast: function(msg, type) {
		plus.nativeUI.toast(msg, 
			{icon: "image/" + type + ".png", verticalAlign: "center"})
	},
	
	setUserGlobalInfo: function(user) {
		let userInfoStr = JSON.stringify(user);
		plus.storage.setItem("userInfo", userInfoStr);
	},

	getUserGlobalInfo: function() {
		let userInfoStr = plus.storage.getItem("userInfo");
		return JSON.parse(userInfoStr);
	},
	
	userLogout: function() {
		plus.storage.removeItem("userInfo");
	},
	
	setContactList: function(contactList) {
		let contactListStr = JSON.stringify(contactList);
		plus.storage.setItem("contactList", contactListStr);
	},
	
	getContactList: function() {
		let contactListStr = plus.storage.getItem("contactList");
		if (!this.isNotNull(contactListStr)) {
			return [];
		}
		return JSON.parse(contactListStr);
	},

	getFriendFromContactList: function(friendId) {
		let contactListStr = plus.storage.getItem("contactList");
		if (this.isNotNull(contactListStr)) {
			let contactList = JSON.parse(contactListStr);
			for (let i = 0 ; i < contactList.length ; i ++) {
				let friend = contactList[i];
				if (friend.friendUserId == friendId) {
					return friend;
					break;
				}
			}
		} else {
			return null;
		}
	},


	saveUserChatHistory: function(myId, friendId, msg, flag) {
		var me = this;
		var chatKey = "chat-" + myId + "-" + friendId;
		var chatHistoryListStr = plus.storage.getItem(chatKey);
		var chatHistoryList;
		if (me.isNotNull(chatHistoryListStr)) {
			chatHistoryList = JSON.parse(chatHistoryListStr);
		} else {
			chatHistoryList = [];
		}

		var singleMsg = new me.ChatHistory(myId, friendId, msg, flag);

		chatHistoryList.push(singleMsg);
		
		plus.storage.setItem(chatKey, JSON.stringify(chatHistoryList));
	},

	getUserChatHistory: function(myId, friendId) {
		var me = this;
		var chatKey = "chat-" + myId + "-" + friendId;
		var chatHistoryListStr = plus.storage.getItem(chatKey);
		var chatHistoryList;
		if (me.isNotNull(chatHistoryListStr)) {
			chatHistoryList = JSON.parse(chatHistoryListStr);
		} else {
			chatHistoryList = [];
		}
		
		return chatHistoryList;
	},

	deleteUserChatHistory: function(myId, friendId) {
		var chatKey = "chat-" + myId + "-" + friendId;
		plus.storage.removeItem(chatKey);
	},

	saveUserChatSnapshot: function(myId, friendId, msg, isRead) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;

		var chatSnapshotListStr = plus.storage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
			for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
				if (chatSnapshotList[i].friendId == friendId) {
					chatSnapshotList.splice(i, 1);
					break;
				}
			}
		} else {
			chatSnapshotList = [];
		}

		var singleMsg = new me.ChatSnapshot(myId, friendId, msg, isRead);

		chatSnapshotList.unshift(singleMsg);
		
		plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList));
	},

	getUserChatSnapshot: function(myId) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		var chatSnapshotListStr = plus.storage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
		} else {
			chatSnapshotList = [];
		}
		
		return chatSnapshotList;
	},

	deleteUserChatSnapshot: function(myId, friendId) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;

		var chatSnapshotListStr = plus.storage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
			for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
				if (chatSnapshotList[i].friendId == friendId) {
					chatSnapshotList.splice(i, 1);
					break;
				}
			}
		} else {
			return;
		}
		
		plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList));
	},

	readUserChatSnapshot: function(myId, friendId) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		var chatSnapshotListStr = plus.storage.getItem(chatKey);
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			chatSnapshotList = JSON.parse(chatSnapshotListStr);
			for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
				var item = chatSnapshotList[i];
				if (item.friendId == friendId) {
					item.isRead = true;
					chatSnapshotList.splice(i, 1, item);
					break;
				}
			}
			plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList));
		} else {
			return;
		}
	},


	CONNECT: 1,
	CHAT: 2,
	SIGNED: 3,
	KEEPALIVE: 4,
	PULL_FRIEND:5,
	

	ChatMsg: function(senderId, receiverId, msg, msgId){
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.msg = msg;
		this.msgId = msgId;
	},

	DataContent: function(action, chatMsg, extend){
		this.action = action;
		this.chatMsg = chatMsg;
		this.extend = extend;
	},


	ChatHistory: function(myId, friendId, msg, flag){
		this.myId = myId;
		this.friendId = friendId;
		this.msg = msg;
		this.flag = flag;
	},
	

	ChatSnapshot: function(myId, friendId, msg, isRead){
		this.myId = myId;
		this.friendId = friendId;
		this.msg = msg;
		this.isRead = isRead;
	}
	
}
