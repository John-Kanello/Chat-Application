function initializeStompClient() {
    const inputMsg = document.getElementById("input-msg");
    const sendMsgBtn = document.getElementById("send-msg-btn");

    const stompClient = new StompJs.Client({
        brokerURL: "ws://localhost:28852/chat-registry-endpoint",
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        onConnect: onConnect,
        onDisconnect: onDisconnect,
        onStompError: onStompError
    });

    stompClient.activate();

    async function onConnect() {
        stompClient.subscribe("/topic/message", function (message) {
            if(chatName !== 'public') {
                return;
            }
            const messageData = JSON.parse(message.body);
            displayMessage(messageData);
        });

        stompClient.subscribe("/user/" + username + "/queue/messages", function (message) {
           if(chatName === 'public') {
               return;
           }
           const parsedMessage = JSON.parse(message.body);
           if((parsedMessage.sender === username && parsedMessage.receiver === chatName) ||
           (parsedMessage.sender === chatName && parsedMessage.receiver === username)) {
               displayMessage(parsedMessage);
           }
       });

       stompClient.subscribe("/user/" + username + "/queue/container", function (unreadMessages) {
            const parsedUnreadMessages = JSON.parse(unreadMessages.body);
            updateOnlineUsersUI(parsedUnreadMessages);
       });

        stompClient.subscribe("/topic/user/" + username + "/new", function (userList) {
            const userListData = JSON.parse(userList.body);
            updateOnlineUsersUI(userListData);
        });

        stompClient.subscribe("/topic/user/" + username + "/leave", function (userList) {
            const userListData = JSON.parse(userList.body);
            updateOnlineUsersUI(userListData);
        });

        publishUser(stompClient);

        const onlineUsers = await fetchOnlineUsers();
        updateOnlineUsersUI(onlineUsers);
    }

    function onDisconnect() {
        console.log("Disconnected from WebSocket.");
    }

    function onStompError(frame) {
        console.error("STOMP Error: ", frame);
    }

    sendMsgBtn.addEventListener("click", sendMessage);

    function sendMessage() {
        const messageContent = inputMsg.value.trim();
        if (messageContent === "") return;

        if(chatName === 'public') {
            sendPublicMessage(messageContent);
        } else {
            sendPrivateMessage(messageContent);
        }

        inputMsg.value = "";
    }

    function sendPublicMessage(messageContent) {
        const messageData = {
            sender: username,
            timestamp: new Date().toISOString(),
            content: messageContent
        };
        stompClient.publish({
            destination: "/app/message",
            body: JSON.stringify(messageData)
        });
    }

    function sendPrivateMessage(messageContent) {
        const messageData = {
            sender: username,
            receiver: chatName,
            timestamp: new Date().toISOString(),
            content: messageContent
        };
        stompClient.publish({
            destination: "/app/message/private",
            body: JSON.stringify(messageData)
        });
    }
}