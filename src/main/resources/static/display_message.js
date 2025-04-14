function displayMessage(messageData) {
    const messagesContainer = document.getElementById("messages");

    const messageDiv = document.createElement("div");
    messageDiv.classList.add("message-container");

    messageDiv.innerHTML = `
        <div class="sender">${messageData.sender}</div>
        <div class="date">${messageData.timestamp}</div>
        <div class="message">${messageData.content}</div>
    `;

    messagesContainer.appendChild(messageDiv);
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}
