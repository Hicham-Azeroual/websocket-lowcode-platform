# Low-Code Platform Frontend (React + Vite)

This is the **frontend** for a full-stack low-code platform, built with **React** and **Vite**. It provides a real-time progress tracker and user interface for interacting with the backend (Spring Boot) via WebSockets.

---

## 🚀 Project Overview

- **Purpose:**
  - Allow users to trigger architecture generation tasks and track their progress in real time.
  - Display both user-specific and system-wide progress updates using WebSockets.
- **Stack:**
  - React 19, Vite, Tailwind CSS, React Toastify, Jest, Testing Library
  - Communicates with a Spring Boot backend (see `../backend`)

---

## ✨ Features

- Real-time progress updates via WebSockets (STOMP over SockJS)
- User-specific and public (system) notifications
- Toast notifications for completion and errors
- Operation and user selection UI
- Modern, responsive design with Tailwind CSS
- Comprehensive test suite for WebSocket logic

---

## 📁 Folder Structure

```
frontend/
├── public/               # Static assets
├── src/
│   ├── components/       # React components (e.g., ProgressTracker)
│   ├── hooks/            # Custom React hooks (e.g., useWebSocket)
│   ├── test/             # Test files (Jest + Testing Library)
│   ├── assets/           # Images, icons, etc.
│   ├── App.jsx           # Main app component
│   ├── main.jsx          # Entry point
│   └── index.css         # Global styles (Tailwind)
├── package.json          # Project metadata and scripts
├── vite.config.js        # Vite configuration
├── jest.config.js        # Jest configuration
├── eslint.config.js      # ESLint configuration
└── README.md             # This file
```

---

## ⚙️ Setup & Development

### Prerequisites

- Node.js (v18+ recommended)
- npm (v9+) or yarn
- Backend server running (see `../backend`)

### Install dependencies

```sh
npm install
# or
yarn install
```

### Start the development server

```sh
npm run dev
# or
yarn dev
```

- The app will be available at [http://localhost:5173](http://localhost:5173)
- Make sure the backend is running at [http://localhost:8080](http://localhost:8080)

### Build for production

```sh
npm run build
```

### Preview production build

```sh
npm run preview
```

---

## 🧪 Testing

- Run all tests:
  ```sh
  npm test
  # or
  yarn test
  ```
- Tests are written using **Jest** and **@testing-library/react**
- WebSocket logic is thoroughly tested in `src/test/useWebSocket.test.js`

---

## 🛠️ Technologies Used

- [React](https://react.dev/)
- [Vite](https://vitejs.dev/)
- [Tailwind CSS](https://tailwindcss.com/)
- [React Toastify](https://fkhadra.github.io/react-toastify/)
- [Jest](https://jestjs.io/), [Testing Library](https://testing-library.com/)
- [SockJS](https://github.com/sockjs/sockjs-client), [STOMP.js](https://stomp-js.github.io/)

---

## 🔗 Backend Integration

- This frontend expects the backend WebSocket endpoint at `http://localhost:8080/ws`
- For backend setup and API details, see the `../backend` folder

---

## 📣 Notes

- You can select a user and trigger a generation task from the UI
- Progress updates are shown in real time, both for the current user and system-wide
- Operation IDs are displayed and can be copied for reference

---

## 📄 License

This project is licensed under the MIT License. See the root of the repository for details.
