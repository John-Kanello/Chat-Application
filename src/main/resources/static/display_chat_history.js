function displayPublicChatHistory() {
    clearMessages();
    fetch("/api/chat/history")
        .then(response => response.json())
        .then(messages => {
            messages.forEach(displayMessage);
        })
        .catch(error => console.error("Error fetching chat history:", error));
}