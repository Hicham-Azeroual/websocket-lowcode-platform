import React, { useState } from "react";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { ProgressTracker } from "./components/ProgressTracker";

const App = () => {
  const [userId, setUserId] = useState("user123");
  const [operationId, setOperationId] = useState("");
  const [latestOperationId, setLatestOperationId] = useState("");
  const [status, setStatus] = useState("");

  // Trigger generation and get operationId from backend
  const triggerGeneration = async () => {
    setStatus("Starting generation...");
    try {
      const response = await fetch(`http://localhost:8080/generate?userId=${userId}`);
      const text = await response.text();
      // Extract operationId from response string
      const match = text.match(/operation: ([a-f0-9-]+)/i);
      if (match && match[1]) {
        setLatestOperationId(match[1]);
        setOperationId(match[1]);
        setStatus("Generation started!");
      } else {
        setStatus("Failed to extract operation ID.");
      }
    } catch (e) {
      setStatus("Error: " + e.message);
    }
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">
        Low-Code Platform Progress Tracker
      </h1>
      <div className="mb-4">
        <label className="mr-2">User ID:</label>
        <select
          value={userId}
          onChange={e => setUserId(e.target.value)}
          className="p-2 border rounded"
        >
          <option value="user123">user123</option>
          <option value="user456">user456</option>
          <option value="user789">user789</option>
        </select>
        <button
          className="ml-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
          onClick={triggerGeneration}
        >
          Trigger Generation
        </button>
      </div>
      {latestOperationId && (
        <div className="mb-4">
          <span className="font-semibold">Latest Operation ID:</span>
          <span className="ml-2 p-1 bg-gray-200 rounded select-all">{latestOperationId}</span>
          <button
            className="ml-2 px-2 py-1 bg-gray-300 rounded hover:bg-gray-400"
            onClick={() => {
              navigator.clipboard.writeText(latestOperationId);
            }}
          >
            Copy
          </button>
        </div>
      )}
      <div className="mb-4">
        <label className="mr-2">Operation ID:</label>
        <input
          type="text"
          value={operationId}
          onChange={e => setOperationId(e.target.value)}
          placeholder="Enter operation ID"
          className="p-2 border rounded"
        />
      </div>
      {status && <div className="mb-4 text-sm text-blue-700">{status}</div>}
      <ProgressTracker userId={userId} operationId={operationId} />
      <ToastContainer />
    </div>
  );
};

export default App;
