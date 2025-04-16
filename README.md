# 💬 Real-Time Chat Application

A web-based real-time chat application built using **Java (Spring Boot)** and **WebSockets (STOMP)**. Users can log in, and communicate via public and private chats. The app features real-time updates, message history, online user tracking, and unread message counters.

---

## 🚀 Features

- **Public Chat**: Accessible to all users with message history support.
- **Private Messaging**: One-on-one conversations with stored history.
- **Online User List**: Real-time updates of online users.
- **Unread Message Indicators**: Shows unread private messages per user.
- **Chat Switching**: Switch between public and private chats with preserved message history.
- **Responsive UI**: Clean layout with message container, chat switch, and user list.

---

```bash

## 📡 WebSocket Endpoints

- **Public Chat**
  - `@MessageMapping("/message/public")`
  - Broadcasts to `/topic/messages`

- **Private Chat**
  - `@MessageMapping("/message/private")`
  - Sends to `/user/{username}/queue/messages` and `/user/{username}/queue/container`
 
- **User Joining/Leaving**
  - `@MessageMapping("/user/new")`
  - Sends to `/topic/user/{username}/new` for each user in the application
 
  - Session Disconnect Event
  - Sends to `/topic/user/{username}/leave` for each user in the application

---

## 💻 Frontend Functionality

- Built using **vanilla JavaScript + HTML/CSS**
- Uses `stomp.js` and `sockjs-client` for real-time communication.
- Dynamic DOM manipulation for message rendering and user tracking.
- Handles private chat history and message separation.

---

## 🧪 Example UI Behavior

- ✅ Clicking a username opens a private chat.
- ✅ Messages are cleared and reloaded when switching chats.
- ✅ New private messages from other users increment a visible badge.
- ✅ A public chat button toggles back to global chat.

---

## ⚙️ How to Run

### Prerequisites

- Java 23
- Maven
- H2 Database (auto-configured)

### Steps

```bash
# Clone the repository
git clone https://github.com/your-repo/chat-app.git
cd chat-app

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

## 📊 APIs

- `GET /api/user`: Fetches online users.
- `GET /api/chat/history`: Returns public chat history.
- `GET /api/chat/private/history?sender=X&receiver=Y`: Returns private chat history between two users.
- `GET /api/container/all?username=X`: Returns the online-user container for a particular user.
- `PUT /api/container/reset?currentUser=X&userToConnectWith=Y`: Resets the unread message count of current user to for user to connect with.
- `PUT /api/container/reset?currentUser=X&userToConnectWith=Y`: Sets the connected chat for the current user to userToConnectWith.
  
---

## 📌 Tech Stack

- Java 23
- Spring Boot (Web, WebSocket, Data JPA)
- H2 Database
- STOMP over SockJS
- HTML/CSS/JavaScript
