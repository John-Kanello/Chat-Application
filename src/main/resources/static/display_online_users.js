function updateOnlineUsersUI(onlineUsers) {
    const usersContainer = document.getElementById('users');
    usersContainer.innerHTML = '';
    onlineUsers.forEach(unreadMessage => {
        const container = document.createElement('div');
        container.classList.add('user-container');
        const userElement = document.createElement('div');
        userElement.classList.add('user');
        userElement.textContent = unreadMessage.username;
        container.appendChild(userElement);
        const counterElement = document.createElement('div');
        counterElement.classList.add('new-message-counter');
        if (unreadMessage.unreadMessageCount > 0) {
            counterElement.textContent = unreadMessage.unreadMessageCount;
            counterElement.style.display = 'inline-block';
        } else {
            counterElement.style.display = 'none';
        }
        container.appendChild(userElement);
        container.appendChild(counterElement);
        container.addEventListener('click', () => {
            chatName = unreadMessage.username;
            document.getElementById('chat-with').textContent = unreadMessage.username;
            displayPrivateChatHistory();
            updatedConnectedChatName(username, chatName);
        })
        usersContainer.appendChild(container);
    });
}

function updatedConnectedChatName(currentUser, userToConnectWith) {
    const baseUrl = "/api/container/reset";
    const queryParams = {
        currentUser: currentUser,
        userToConnectWith: userToConnectWith
    }
    const resetChatUrl = `${baseUrl}?${new URLSearchParams(queryParams)}`;
    fetch(resetChatUrl, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(response => response.json())
    .then(unreadMessages => {
        updateOnlineUsersUI(unreadMessages)
    })
    .catch(error => console.error("Error fetching chat history:", error));
}