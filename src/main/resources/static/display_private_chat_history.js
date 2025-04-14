function displayPrivateChatHistory() {
    clearMessages();
    const baseUrl = "/api/chat/private/history";
    const queryParams = {
        sender: username,
        receiver: chatName
    }
    const privateChatUrl = `${baseUrl}?${new URLSearchParams(queryParams)}`;
    fetch(privateChatUrl)
        .then(response => response.json())
        .then(messages => {
            messages.forEach(displayMessage);
        })
        .catch(error => console.error("Error fetching chat history:", error));
}