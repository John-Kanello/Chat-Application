async function fetchOnlineUsers() {
    try {
        const queryParams = {
            username: username
        }
        const baseUrl = "/api/container/all";
        const fetchUserUrl = `${baseUrl}?${new URLSearchParams(queryParams)}`;
        const response = await fetch(fetchUserUrl);
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const text = await response.text();
        const data = text ? JSON.parse(text) : [];
        return data;
    } catch (error) {
        console.error('Error fetching online users:', error);
        return [];
    }
}