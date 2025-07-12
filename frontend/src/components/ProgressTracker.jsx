import React from "react";
import { toast } from "react-toastify";
import { useWebSocket } from "../hooks/useWebSocket";
// Component to display private and public progress updates
export const ProgressTracker = ({ userId, operationId }) => {
  const { isConnected, progressUpdates, clearProgressUpdates } =
    useWebSocket(userId);

  // Filter private updates for the current user and operation
  const privateUpdates = progressUpdates.filter(
    (update) =>
      !update.isPublic &&
      update.userId === userId &&
      update.operationId === operationId
  );

  // Filter public updates
  const publicUpdates = progressUpdates.filter((update) => update.isPublic);

  // Show toast notifications for private updates
  React.useEffect(() => {
    const latestPrivateUpdate = privateUpdates[privateUpdates.length - 1];
    if (latestPrivateUpdate) {
      if (latestPrivateUpdate.type === "GENERATION_COMPLETED") {
        toast.success(latestPrivateUpdate.message);
      } else if (latestPrivateUpdate.type === "GENERATION_ERROR") {
        toast.error(latestPrivateUpdate.message);
      }
    }
  }, [privateUpdates]);

  // Determine progress bar color
  const getProgressColor = (type) => {
    switch (type) {
      case "GENERATION_COMPLETED":
        return "bg-green-500";
      case "GENERATION_ERROR":
        return "bg-red-500";
      case "GENERATION_PROGRESS":
      case "GENERATION_STARTED":
        return "bg-blue-500";
      default:
        return "bg-blue-500";
    }
  };

  return (
    <div className="progress-tracker p-4 bg-gray-100 rounded-lg shadow-md">
      <h2 className="text-lg font-bold mb-4">Progress Tracker</h2>
      {/* Connection status */}
      <div className="connection-status mb-4">
        {isConnected ? (
          <span className="text-green-600 font-semibold">Connected</span>
        ) : (
          <span className="text-red-600 font-semibold">Disconnected</span>
        )}
      </div>
      {/* Private updates */}
      <div className="private-updates mb-4">
        <h3 className="text-md font-semibold mb-2">
          Private Updates for {userId}
        </h3>
        {privateUpdates.length === 0 ? (
          <p>No private updates available</p>
        ) : (
          privateUpdates.map((update, index) => (
            <div key={index} className="mb-2 p-2 bg-white rounded shadow">
              <div className="progress-bar w-full bg-gray-200 rounded-full h-4 mb-2">
                <div
                  className={`h-4 rounded-full ${getProgressColor(
                    update.type
                  )}`}
                  style={{ width: `${update.percentage || 0}%` }}
                ></div>
              </div>
              <p>
                <strong>Operation:</strong> {update.operationId}
              </p>
              <p>
                <strong>Step:</strong> {update.step}
              </p>
              <p>
                <strong>Message:</strong> {update.message}
              </p>
              <p>
                <strong>Progress:</strong> {update.percentage || 0}%
              </p>
              <p>
                <strong>Time:</strong>{" "}
                {new Date(update.timestamp).toLocaleTimeString()}
              </p>
            </div>
          ))
        )}
      </div>
      {/* Public updates */}
      <div className="public-updates">
        <h3 className="text-md font-semibold mb-2">Public System Updates</h3>
        {publicUpdates.length === 0 ? (
          <p>No public updates available</p>
        ) : (
          publicUpdates.map((update, index) => (
            <div key={index} className="mb-2 p-2 bg-white rounded shadow">
              <p>
                <strong>User:</strong> {update.userId}
              </p>
              <p>
                <strong>Operation:</strong> {update.operationId}
              </p>
              <p>
                <strong>Message:</strong> {update.message}
              </p>
              <p>
                <strong>Time:</strong>{" "}
                {new Date(update.timestamp).toLocaleTimeString()}
              </p>
            </div>
          ))
        )}
      </div>
      {/* Clear updates button */}
      <button
        className="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        onClick={clearProgressUpdates}
      >
        Clear Updates
      </button>
    </div>
  );
};
