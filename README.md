# WebSocket Low-Code Platform

A modern, scalable real-time notification platform built with Spring Boot and WebSocket technology, designed for low-code/no-code development environments.

## 🚀 Overview

This platform provides a robust foundation for real-time communication between backend services and frontend applications. It's specifically designed to support low-code development workflows by offering standardized APIs and WebSocket endpoints for real-time notifications.

## 🏗️ Architecture

### Backend (Spring Boot)
- **WebSocket Server**: Real-time bidirectional communication
- **REST API**: Standardized endpoints for operation management
- **Process Simulator**: Demonstrates multi-step operation workflows
- **Notification Service**: Centralized message broadcasting system

### Frontend (React + PrimeReact)
- **Real-time UI**: Live updates via WebSocket connections
- **Modern Interface**: Built with PrimeReact components
- **Operation Management**: Trigger and monitor long-running processes

## 🛠️ Technology Stack

### Backend
- **Java 17** - Modern Java with enhanced performance
- **Spring Boot 3.2.0** - Enterprise-grade application framework
- **Spring WebSocket** - Real-time communication support
- **Maven** - Dependency management and build automation

### Frontend
- **React 18** - Modern UI framework
- **PrimeReact** - Enterprise UI component library
- **WebSocket API** - Real-time communication
- **TypeScript** - Type-safe development

## 📋 Features

### Real-Time Communication
- ✅ WebSocket-based real-time notifications
- ✅ Bidirectional communication support
- ✅ Automatic reconnection handling
- ✅ Message broadcasting to multiple clients

### Operation Management
- ✅ Multi-step process simulation
- ✅ Progress tracking and status updates
- ✅ Configurable operation workflows
- ✅ Error handling and recovery

### Developer Experience
- ✅ Comprehensive test coverage (66 tests)
- ✅ RESTful API design
- ✅ Detailed logging and monitoring
- ✅ Modular architecture

## 🔧 Quick Start

### Prerequisites
- Java 17 or higher
- Node.js 16 or higher
- Maven 3.6+

### Backend Setup
```bash
cd backend
mvn spring-boot:run
```

The backend server will start on `http://localhost:8080`

### Frontend Setup
```bash
cd frontend
npm install
npm start
```

The frontend application will start on `http://localhost:3000`

## 🔌 API Documentation

### WebSocket Connection
```
Endpoint: ws://localhost:8080/ws
Topic: /topic/notifications
```

### REST API Endpoints

#### Start Operation
```http
POST /api/operations/start
Content-Type: application/json

{}
```

#### WebSocket Message Format
```json
{
  "step": "STARTED|PROCESSING|DONE",
  "message": "Operation status message",
  "timestamp": 1752337875095
}
```

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

**Test Coverage:**
- Unit Tests: 66 tests
- Integration Tests: WebSocket and REST API
- Component Tests: Service layer and controllers

### Frontend Tests
```bash
cd frontend
npm test
```

## 📁 Project Structure

```
websocket-lowcode-platform-main/
├── backend/
│   ├── src/main/java/com/hicham/backend/
│   │   ├── BackendApplication.java          # Main application
│   │   ├── config/
│   │   │   └── WebSocketConfig.java        # WebSocket configuration
│   │   ├── controller/
│   │   │   └── NotificationController.java # REST API endpoints
│   │   ├── service/
│   │   │   └── NotificationService.java    # Notification logic
│   │   ├── simulator/
│   │   │   └── FakeProcessSimulator.java   # Process simulation
│   │   ├── model/
│   │   │   └── OperationStatus.java        # Data models
│   │   └── dto/
│   │       └── NotificationPayload.java    # Data transfer objects
│   ├── src/test/                           # Comprehensive test suite
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/                     # React components
│   │   ├── services/                       # WebSocket and API services
│   │   └── utils/                          # Utility functions
│   ├── public/
│   └── package.json
└── README.md
```

## 🎯 Business Value

### For Low-Code/No-Code Platforms
- **Standardized Integration**: Pre-built WebSocket and REST APIs
- **Real-Time Capabilities**: Live updates without polling
- **Scalable Architecture**: Supports multiple concurrent users
- **Developer Productivity**: Ready-to-use components and services

### For Enterprise Applications
- **Reliable Communication**: Robust error handling and reconnection
- **Security Ready**: Framework supports authentication and authorization
- **Monitoring**: Comprehensive logging and metrics
- **Maintainable Code**: Clean architecture and comprehensive testing

## 🔒 Security Considerations

- WebSocket connections support authentication
- REST API endpoints can be secured with Spring Security
- CORS configuration for cross-origin requests
- Input validation and sanitization

## 📈 Performance

- **WebSocket**: Low-latency real-time communication
- **Spring Boot**: Optimized for high-throughput applications
- **Connection Pooling**: Efficient resource management
- **Async Processing**: Non-blocking operation execution

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For technical support or questions:
- Create an issue in the repository
- Review the test documentation
- Check the API documentation

---

**Built with ❤️ for modern web applications** 