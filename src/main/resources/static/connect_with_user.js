function connectWithUser(currentUser, userToConnectWith) {
    const baseUrl = "/api/container/connect";
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
    .catch(error => console.error("Error connecting user with:", userToConnectWith));
}