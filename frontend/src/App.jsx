import React, { useState } from "react";
import ProgressTracker from "./components/ProgressTracker";

function App() {
  const [operationId, setOperationId] = useState("");
  const [status, setStatus] = useState("");
  const userId = "user1"; // Hardcoded for demo; replace with auth if needed

  const startOperation = async () => {
    if (!operationId) {
      setStatus("Please enter an Operation ID");
      return;
    }
    setStatus("Starting operation...");
    try {
      const response = await fetch(
        `http://localhost:8080/api/progress/start?userId=${userId}&operationId=${operationId}`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            architectureType: "demo", // You can make this dynamic if needed
            parameters: "test", // You can make this dynamic if needed
          }),
        }
      );
      if (response.ok) {
        setStatus("Operation started!");
      } else {
        setStatus("Failed to start operation");
      }
    } catch (error) {
      setStatus("Error: " + error.message);
    }
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">
        Architecture Generation Tracker
      </h1>
      <input
        type="text"
        value={operationId}
        onChange={(e) => setOperationId(e.target.value)}
        placeholder="Enter Operation ID"
        className="border p-2 mb-4"
      />
      <button
        onClick={startOperation}
        className="ml-2 px-4 py-2 bg-blue-500 text-white rounded"
      >
        Start Operation
      </button>
      {status && <div className="mt-2 text-sm">{status}</div>}
      <ProgressTracker userId={userId} operationId={operationId} />
    </div>
  );
}

export default App;
