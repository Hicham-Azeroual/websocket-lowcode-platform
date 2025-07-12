import React from 'react';
import { useWebSocket } from '../hooks/useWebSocket';

export const ProgressTracker = ({ userId, operationId }) => {
  const { isConnected, progressUpdates, error } = useWebSocket(userId);
  const currentOperation = progressUpdates.find((update) => update.operationId === operationId);

  if (!operationId) {
    return <div className="text-gray-500">Please enter an Operation ID</div>;
  }

  if (!currentOperation) {
    return <div className="text-gray-500">No progress updates for this operation</div>;
  }

  const getProgressColor = (type) => {
    switch (type) {
      case 'GENERATION_COMPLETED':
        return 'bg-green-500';
      case 'GENERATION_ERROR':
        return 'bg-red-500';
      case 'GENERATION_PROGRESS':
      case 'GENERATION_STARTED':
        return 'bg-blue-500';
      default:
        return 'bg-blue-500';
    }
  };

  return (
    <div className="progress-tracker p-4 border rounded-lg shadow-md">
      <div className="connection-status mb-4">
        {isConnected ? (
          <span className="text-green-500 font-bold">Connected</span>
        ) : (
          <span className="text-red-500 font-bold">Disconnected</span>
        )}
        {error && <p className="text-red-500">{error}</p>}
      </div>
      <div className="progress-content">
        <h4 className="text-lg font-semibold mb-2">Operation Progress: {operationId}</h4>
        <div className="progress-bar w-full bg-gray-200 rounded-full h-4 mb-2">
          <div
            className={`h-4 rounded-full ${getProgressColor(currentOperation.type)}`}
            style={{ width: `${currentOperation.percentage || 0}%` }}
          ></div>
        </div>
        <div className="progress-details">
          <p><strong>Step:</strong> {currentOperation.status}</p>
          <p><strong>Message:</strong> {currentOperation.message}</p>
          <p><strong>Progress:</strong> {currentOperation.percentage || 0}%</p>
          <p><strong>Time:</strong> {new Date(currentOperation.timestamp).toLocaleTimeString()}</p>
        </div>
      </div>
    </div>
  );
};

export default ProgressTracker; 