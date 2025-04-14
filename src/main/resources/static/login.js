document.addEventListener("DOMContentLoaded", function () {
    const sendUsernameButton = document.getElementById("send-username-btn");
    const inputUsername = document.getElementById("input-username");
    const usernameContainer = document.getElementById("username-container");
    const chatLayout = document.getElementById("chat-layout");
    const publicChatButton = document.getElementById("public-chat-btn");
    const chatWith = document.getElementById('chat-with');

    sendUsernameButton.addEventListener("click", function () {
        const enteredUsername = inputUsername.value.trim();
        if (enteredUsername === "") {
            alert("Username cannot be empty.");
            return;
        }
        username = enteredUsername;
        usernameContainer.style.display = "none";
        chatLayout.style.display = "flex";
        displayPublicChatHistory();
        initializeStompClient();
    });

    publicChatButton.addEventListener("click", async() => {
        if(chatName === 'public') {
            return;
        }
        chatName = 'public';
        chatWith.textContent = 'Public chat';
        displayPublicChatHistory();
        connectWithUser(username, chatName);
    });
});