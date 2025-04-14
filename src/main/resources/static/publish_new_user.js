function publishUser(stompClient) {
    const userData = {
        sender: username,
        receiver: chatName
    };
    stompClient.publish({
        destination: "/app/user/new",
        body: JSON.stringify(userData)
    });
}