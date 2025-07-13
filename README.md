# WebSocket Low-Code Platform

A modern, real-time progress tracking platform for low-code development, featuring a Spring Boot backend and a React (Vite) frontend. The system uses WebSockets (STOMP over SockJS) for live updates, supporting user-specific and system-wide notifications.

---

## 📦 Project Structure

```
websocket-lowcode-platform/
├── backend/    # Spring Boot backend (Java)
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend/   # React frontend (Vite)
│   ├── src/
│   ├── package.json
│   └── ...
└── README.md
```

---

## 🚀 Overview

This platform demonstrates a scalable architecture for real-time progress tracking in long-running operations. It is ideal for low-code/no-code environments, providing:

- **User-specific progress updates** via WebSocket topics
- **System-wide notifications**
- **Asynchronous operation management**
- **Modern, responsive UI**

---

## ✨ Features

- **Real-Time Progress Tracking**: Live updates for users and system
- **User-Specific Queues**: Isolated progress per user
- **System Broadcasts**: Global notifications
- **Asynchronous Operations**: Non-blocking backend processing
- **Robust Testing**: Automated tests for frontend and backend
- **Modern UI**: Built with React and Tailwind CSS

---

## 🛠️ Technology Stack

### Backend

- **Java 17**
- **Spring Boot 3.x**
- **Spring WebSocket + STOMP**
- **Maven**

### Frontend

- **React 19**
- **Vite**
- **Tailwind CSS**
- **Jest** (for testing)

---

## 🏗️ Project Setup

### Prerequisites

- Java 17+
- Node.js 16+
- Maven 3.6+

### Backend Setup

```bash
cd backend
mvn spring-boot:run
```

- Runs at: `http://localhost:8080`

### Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

- Runs at: `http://localhost:5173`

---

## 🔌 API Usage

### WebSocket

- **Endpoint:** `ws://localhost:8080/ws`
- **Protocol:** STOMP over SockJS
- **Allowed Origin:** `http://localhost:5173`

#### Subscriptions

- **User Progress:** `/topic/progress.{userId}`
- **System Broadcast:** `/topic/system`

#### Client Message

- **Destination:** `/app/subscribe`
- **Payload:** userId (string)

### REST API

- **Trigger Generation:**
  - `GET /generate?userId={userId}`
  - **Response:** `Generation started for user: {userId}, operation: {operationId}`

#### Progress Update Message Format

```json
{
  "operationId": "op-123",
  "userId": "user-456",
  "type": "GENERATION_STARTED|GENERATION_PROGRESS|GENERATION_COMPLETED|GENERATION_ERROR",
  "percentage": 75,
  "step": "step_3",
  "message": "Processing...",
  "timestamp": "2024-01-15T10:30:00"
}
```

---

## 🧪 Testing

### Backend

```bash
cd backend
mvn test
```

### Frontend

```bash
cd frontend
npm test
```

---

## 📁 Example Usage

1. **Start Backend:**
   - `cd backend && mvn spring-boot:run`
2. **Start Frontend:**
   - `cd frontend && npm install && npm run dev`
3. **Open Frontend:**
   - Go to `http://localhost:5173`
4. **Trigger Generation:**
   - Select a user and click "Trigger Generation"
   - Watch real-time progress updates for the operation

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Make your changes
4. Add/Update tests as needed
5. Commit and push (`git commit -m "Describe your change"`)
6. Open a pull request

---

## 📄 License

This project is licensed under the MIT License.

---

**Built for modern, real-time applications.**
