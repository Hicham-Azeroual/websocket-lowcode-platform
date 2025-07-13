# WebSocket Low-Code Platform

A modern, scalable real-time progress tracking platform built with Spring Boot and WebSocket technology, designed for low-code/no-code development environments with advanced architecture generation capabilities.

## 🚀 Overview

This platform provides a robust foundation for real-time progress tracking and architecture generation workflows. It's specifically designed to support low-code development environments by offering standardized WebSocket APIs for real-time progress updates and asynchronous operation management.

### Key Features
- **Real-time Progress Tracking**: WebSocket-based live progress updates
- **Architecture Generation**: Asynchronous workflow with configurable steps
- **User-Specific Queues**: Isolated progress updates per user
- **System Broadcasting**: Global notifications and status updates
- **Comprehensive Testing**: 100% test coverage with fast execution
- **Modern UI**: React-based frontend with PrimeReact components

## 🏗️ Architecture

### Backend (Spring Boot)
- **WebSocket Server**: Real-time bidirectional communication with STOMP protocol
- **Progress Service**: Centralized progress update broadcasting system
- **Architecture Generation Service**: Asynchronous architecture generation with configurable timing
- **Message Broker**: STOMP-based message routing with topic and queue support
- **REST Controllers**: HTTP endpoints for triggering operations

### Frontend (React + PrimeReact)
- **Real-time Progress UI**: Live progress updates via WebSocket connections
- **Modern Interface**: Built with PrimeReact components
- **Operation Management**: Trigger and monitor long-running architecture generation

## 🛠️ Technology Stack

### Backend
- **Java 17** - Modern Java with enhanced performance
- **Spring Boot 3.5.3** - Enterprise-grade application framework
- **Spring WebSocket + STOMP** - Advanced real-time communication
- **Spring Messaging** - Message broker and routing
- **Maven** - Dependency management and build automation
- **JUnit 5** - Comprehensive testing framework

### Frontend
- **React 18** - Modern UI framework
- **PrimeReact** - Enterprise UI component library
- **WebSocket API** - Real-time communication
- **TypeScript** - Type-safe development

## 📋 Features

### Real-Time Progress Tracking
- ✅ WebSocket-based real-time progress updates
- ✅ User-specific progress topics (`/topic/progress.{userId}`)
- ✅ System-wide broadcast topics (`/topic/system`)
- ✅ STOMP protocol with SockJS fallback
- ✅ Automatic reconnection handling
- ✅ Message persistence and delivery guarantees

### Architecture Generation
- ✅ Asynchronous architecture generation workflows
- ✅ Multi-step progress tracking (started, progress, completed, error)
- ✅ Real-time progress percentage updates
- ✅ Comprehensive error handling and recovery
- ✅ Configurable generation steps and timing
- ✅ Non-blocking CompletableFuture implementation

### Developer Experience
- ✅ Comprehensive test coverage (58+ tests)
- ✅ Modular service architecture
- ✅ Detailed logging and monitoring
- ✅ Type-safe progress update models
- ✅ Fast test execution with configurable timing

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
Allowed Origins: http://localhost:5173
```

### WebSocket Destinations

#### User-Specific Progress Updates
```
Destination: /topic/progress.{userId}
Type: User-specific progress updates
Example: /topic/progress.user123
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
Payload: userId (String)
```

### REST API Endpoints

#### Start Architecture Generation
```
GET /generate?userId={userId}
Response: "Generation started for user: {userId}, operation: {operationId}"
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
- **Unit Tests**: 58+ tests covering all components
- **Integration Tests**: WebSocket configuration and services
- **Component Tests**: Progress services and controllers
- **Model Tests**: Data models and enums

### Test Results
```
✅ BackendApplicationTests - 1 test passed
✅ GenerationControllerTest - 5 tests passed
✅ ProgressControllerTest - 5 tests passed
✅ WebSocketIntegrationTest - 14 tests passed
✅ ProgressTypeTest - 12 tests passed
✅ ProgressUpdateTest - 7 tests passed
✅ ArchitectureGenerationServiceTest - 6 tests passed
✅ WebSocketProgressServiceTest - 8 tests passed
```

### Test Features
- **Fast Execution**: Configurable sleep timing for quick test runs
- **Comprehensive Coverage**: All components, edge cases, and error scenarios
- **Mock Testing**: Isolated unit tests with Mockito
- **Integration Testing**: Full Spring Boot context testing

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
│   │   ├── websocket/
│   │   │   └── WebSocketConfig.java            # WebSocket + STOMP configuration
│   │   ├── controller/
│   │   │   ├── ProgressController.java         # WebSocket message handling
│   │   │   └── GenerationController.java       # REST API endpoints
│   │   ├── service/
│   │   │   ├── WebSocketProgressService.java   # Progress update service
│   │   │   └── ArchitectureGenerationService.java # Architecture generation
│   │   └── model/
│   │       ├── ProgressUpdate.java             # Progress update model
│   │       └── ProgressType.java               # Progress type enum
│   ├── src/test/java/com/hicham/backend/       # Comprehensive test suite
│   │   ├── controller/                         # Controller tests
│   │   ├── service/                            # Service tests
│   │   ├── model/                              # Model tests
│   │   ├── integration/                        # Integration tests
│   │   └── CompleteTestSuite.java              # Test documentation
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
- **Developer Productivity**: Fast feedback loops with real-time updates

### For Enterprise Applications
- **Reliable Progress Communication**: Robust error handling and reconnection
- **Asynchronous Processing**: Non-blocking architecture generation
- **Security Ready**: Framework supports authentication and authorization
- **Monitoring**: Comprehensive logging and progress metrics
- **Maintainable Code**: Clean architecture and comprehensive testing
- **Performance**: Optimized for high-throughput applications

## 🔒 Security Considerations

- WebSocket connections support authentication via Principal
- User-specific progress topics for data isolation
- CORS configuration for cross-origin requests
- Input validation and sanitization
- Secure message routing with STOMP
- Configurable allowed origins

## 📈 Performance

- **WebSocket + STOMP**: Low-latency real-time communication
- **Spring Boot**: Optimized for high-throughput applications
- **Async Processing**: Non-blocking architecture generation with CompletableFuture
- **Message Broker**: Efficient topic and queue-based message routing
- **Connection Pooling**: Efficient resource management
- **Configurable Timing**: Adjustable sleep intervals for different environments

## 🚀 Progress Types Supported

- **GENERATION_STARTED**: Operation initialization
- **GENERATION_PROGRESS**: Step-by-step progress updates
- **GENERATION_COMPLETED**: Successful completion
- **GENERATION_ERROR**: Error handling and reporting
- **VALIDATION_PROGRESS**: Validation step progress
- **DEPLOYMENT_PROGRESS**: Deployment step progress

## 🔧 Configuration

### WebSocket Configuration
```java
// Allowed origins
.setAllowedOriginPatterns("http://localhost:5173")

// Message broker prefixes
/topic - Public broadcasts
/queue - Private user messages
/app - Application messages
/user - User-specific messages
```

### Architecture Generation Configuration
```java
// Configurable parameters
TOTAL_STEPS = 5                    // Number of generation steps
sleepMillis = 3000                 // Sleep duration per step (configurable)
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass (`mvn test`)
6. Update documentation if needed
7. Submit a pull request

### Development Guidelines
- Follow existing code style and patterns
- Add comprehensive tests for new features
- Update documentation for API changes
- Ensure fast test execution for CI/CD

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For technical support or questions:
- Create an issue in the repository
- Review the test documentation in `CompleteTestSuite.java`
- Check the WebSocket API documentation above
- Examine the integration tests for usage examples

## 🚀 Recent Updates

### v1.0.0
- ✅ Comprehensive test suite with 58+ tests
- ✅ Configurable architecture generation timing
- ✅ Fast test execution (no artificial delays)
- ✅ Improved WebSocket message routing
- ✅ Enhanced error handling and validation
- ✅ Updated documentation and examples

---

**Built with ❤️ for modern real-time applications** 
