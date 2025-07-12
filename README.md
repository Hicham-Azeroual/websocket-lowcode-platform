# WebSocket Low-Code Platform

A modern, scalable real-time progress tracking platform built with Spring Boot and WebSocket technology, designed for low-code/no-code development environments with advanced architecture generation capabilities.

## 🚀 Overview

This platform provides a robust foundation for real-time progress tracking and architecture generation workflows. It's specifically designed to support low-code development environments by offering standardized WebSocket APIs for real-time progress updates and asynchronous operation management.

## 🏗️ Architecture

### Backend (Spring Boot)
- **WebSocket Server**: Real-time bidirectional communication with progress tracking
- **Progress Service**: Centralized progress update broadcasting system
- **Architecture Generation Service**: Asynchronous architecture generation with real-time progress
- **Message Broker**: STOMP-based message routing with topic and queue support

### Frontend (React + PrimeReact)
- **Real-time Progress UI**: Live progress updates via WebSocket connections
- **Modern Interface**: Built with PrimeReact components
- **Operation Management**: Trigger and monitor long-running architecture generation

## 🛠️ Technology Stack

### Backend
- **Java 17** - Modern Java with enhanced performance
- **Spring Boot 3.2.0** - Enterprise-grade application framework
- **Spring WebSocket + STOMP** - Advanced real-time communication
- **Spring Messaging** - Message broker and routing
- **Maven** - Dependency management and build automation

### Frontend
- **React 18** - Modern UI framework
- **PrimeReact** - Enterprise UI component library
- **WebSocket API** - Real-time communication
- **TypeScript** - Type-safe development

## 📋 Features

### Real-Time Progress Tracking
- ✅ WebSocket-based real-time progress updates
- ✅ User-specific progress queues (`/queue/progress`)
- ✅ System-wide broadcast topics (`/topic/system`)
- ✅ STOMP protocol with SockJS fallback
- ✅ Automatic reconnection handling

### Architecture Generation
- ✅ Asynchronous architecture generation workflows
- ✅ Multi-step progress tracking (started, progress, completed, error)
- ✅ Real-time progress percentage updates
- ✅ Comprehensive error handling and recovery
- ✅ Configurable generation steps and timing

### Developer Experience
- ✅ Comprehensive test coverage (41 tests)
- ✅ Modular service architecture
- ✅ Detailed logging and monitoring
- ✅ Type-safe progress update models

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
Protocol: STOMP over SockJS
```

### WebSocket Destinations

#### User-Specific Progress Updates
```
Destination: /user/{userId}/queue/progress
Type: User-specific progress updates
```

#### System Broadcast
```
Destination: /topic/system
Type: System-wide notifications
```

#### Application Messages
```
Destination: /app/subscribe
Type: Client subscription messages
```

### Progress Update Message Format
```json
{
  "operationId": "op-123",
  "userId": "user-456",
  "type": "GENERATION_STARTED|GENERATION_PROGRESS|GENERATION_COMPLETED|GENERATION_ERROR|VALIDATION_PROGRESS|DEPLOYMENT_PROGRESS",
  "percentage": 75,
  "step": "step_3",
  "message": "Processing architecture components...",
  "timestamp": "2024-01-15T10:30:00"
}
```

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

**Test Coverage:**
- Unit Tests: 41 tests
- Integration Tests: WebSocket configuration and services
- Component Tests: Progress services and controllers

### Test Results
```
✅ ProgressTypeTest - 6 tests passed
✅ ProgressUpdateTest - 6 tests passed
✅ WebSocketProgressServiceTest - 8 tests passed
✅ ArchitectureGenerationServiceTest - 6 tests passed
✅ ProgressControllerTest - 3 tests passed
✅ WebSocketIntegrationTest - 11 tests passed
✅ BackendApplicationTests - 1 test passed
```

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
│   │   ├── BackendApplication.java              # Main application
│   │   ├── config/
│   │   │   └── WebSocketConfig.java            # WebSocket + STOMP configuration
│   │   ├── controller/
│   │   │   └── ProgressController.java         # WebSocket message handling
│   │   ├── service/
│   │   │   ├── WebSocketProgressService.java   # Progress update service
│   │   │   └── ArchitectureGenerationService.java # Architecture generation
│   │   └── model/
│   │       ├── ProgressUpdate.java             # Progress update model
│   │       └── ProgressType.java               # Progress type enum
│   ├── src/test/                               # Comprehensive test suite
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/                         # React components
│   │   ├── services/                           # WebSocket and API services
│   │   └── utils/                              # Utility functions
│   ├── public/
│   └── package.json
└── README.md
```

## 🎯 Business Value

### For Low-Code/No-Code Platforms
- **Real-Time Progress Tracking**: Live updates for long-running operations
- **Architecture Generation**: Automated architecture creation workflows
- **Standardized Integration**: Pre-built WebSocket progress APIs
- **Scalable Architecture**: Supports multiple concurrent users and operations

### For Enterprise Applications
- **Reliable Progress Communication**: Robust error handling and reconnection
- **Asynchronous Processing**: Non-blocking architecture generation
- **Security Ready**: Framework supports authentication and authorization
- **Monitoring**: Comprehensive logging and progress metrics
- **Maintainable Code**: Clean architecture and comprehensive testing

## 🔒 Security Considerations

- WebSocket connections support authentication via Principal
- User-specific progress queues for data isolation
- CORS configuration for cross-origin requests
- Input validation and sanitization
- Secure message routing with STOMP

## 📈 Performance

- **WebSocket + STOMP**: Low-latency real-time communication
- **Spring Boot**: Optimized for high-throughput applications
- **Async Processing**: Non-blocking architecture generation with CompletableFuture
- **Message Broker**: Efficient topic and queue-based message routing
- **Connection Pooling**: Efficient resource management

## 🚀 Progress Types Supported

- **GENERATION_STARTED**: Operation initialization
- **GENERATION_PROGRESS**: Step-by-step progress updates
- **GENERATION_COMPLETED**: Successful completion
- **GENERATION_ERROR**: Error handling and reporting
- **VALIDATION_PROGRESS**: Validation step progress
- **DEPLOYMENT_PROGRESS**: Deployment step progress

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
- Check the WebSocket API documentation

---

**Built with ❤️ for modern real-time applications** 