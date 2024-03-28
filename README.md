Spring Boot Chat Application Backend
This project is the backend part of a chat application built using Spring Boot. It leverages WebSockets for real-time communication and MongoDB for storing user and chat message data. This README provides instructions for setting up and running the project, as well as guidelines for contributing.

Features
Real-time messaging using Spring WebSocket
User status management (Online/Offline)
Storage of users and chat messages in MongoDB
Custom configuration of WebSocket message broker
Getting Started
These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

Prerequisites
Before you begin, ensure you have the following installed:

Java JDK 11 or newer
MongoDB
Maven (if not using the Maven Wrapper included in the project)
Installation
Clone the repository to your local machine:
bash
Copy code
git clone https://github.com/your-username/spring-boot-chat-backend.git
Navigate to the project directory:
bash
Copy code
cd spring-boot-chat-backend
Ensure MongoDB is running on your local machine.
Build the project with Maven:
bash
Copy code
./mvnw clean install
If you have Maven installed locally, you can also use:
Copy code
mvn clean install
Run the application:
arduino
Copy code
./mvnw spring-boot:run
Or if you have Maven installed:
arduino
Copy code
mvn spring-boot:run
The server will start on http://localhost:8080.
Project Structure
WebSocketConfig.java: Configures the WebSocket and sets up the message broker.
ChatController.java: Handles incoming WebSocket messages related to chat functionality.
UserController.java: Manages WebSocket connections for user activities.
ChatMessageService.java, UserService.java, ChatRoomService.java: Services that contain business logic for handling chat messages, user data, and chat rooms.
ChatMessage.java, User.java, Status.java: Domain models for chat messages, users, and user status.
Contributing
We welcome contributions to this project. If you have suggestions for improvements or encounter any issues, please follow these steps to contribute:

Fork the repository.
Create your feature branch (git checkout -b feature/AmazingFeature).
Commit your changes (git commit -m 'Add some AmazingFeature').
Push to the branch (git push origin feature/AmazingFeature).
Open a pull request.





