import { useEffect, useState, useRef } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

// Custom hook for WebSocket connection
export const useWebSocket = (userId) => {
  const [isConnected, setIsConnected] = useState(false);
  const [progressUpdates, setProgressUpdates] = useState([]);
  const clientRef = useRef(null);

  useEffect(() => {
    // Connect to backend WebSocket endpoint
    const socket = new SockJS("http://localhost:8080/ws");
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000, // Reconnect after 5 seconds
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    client.onConnect = () => {
      setIsConnected(true);
      clientRef.current = client;

      // Subscribe to private user-specific updates
      client.subscribe(`/topic/progress.${userId}`, (message) => {
        const update = JSON.parse(message.body);
        console.log(update);

        setProgressUpdates((prev) => [...prev, { ...update, isPublic: false }]);
      });

      // Subscribe to public system-wide updates
      client.subscribe("/topic/system", (message) => {
        const update = JSON.parse(message.body);
        setProgressUpdates((prev) => [...prev, { ...update, isPublic: true }]);
      });

      // Send subscription message with userId
      client.publish({ destination: "/app/subscribe", body: userId });
    };

    client.onDisconnect = () => {
      setIsConnected(false);
    };

    client.onStompError = (frame) => {
      console.error("STOMP error:", frame);
      setIsConnected(false);
    };

    client.activate();

    // Cleanup on unmount
    return () => {
      if (clientRef.current) {
        clientRef.current.deactivate();
      }
    };
  }, [userId]);

  const clearProgressUpdates = () => {
    setProgressUpdates([]);
  };

  // Separate arrays for private and public updates
  const privateUpdates = progressUpdates.filter(
    (update) => !update.isPublic && update.userId === userId
  );
  const publicUpdates = progressUpdates.filter((update) => update.isPublic);

  return {
    isConnected,
    privateUpdates,
    publicUpdates,
    // Optionally still return progressUpdates and clearProgressUpdates if needed elsewhere
    progressUpdates,
    clearProgressUpdates,
  };
};
