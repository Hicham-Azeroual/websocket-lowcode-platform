import { useEffect, useState, useRef } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { toast } from "react-toastify";

export const useWebSocket = (userId) => {
  const [isConnected, setIsConnected] = useState(false);
  const [progressUpdates, setProgressUpdates] = useState([]);
  const [error, setError] = useState(null);
  const clientRef = useRef(null);

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    client.onConnect = () => {
      setIsConnected(true);
      setError(null);
      console.log(`Connected and subscribed to /user/${userId}/progress`);
      client.subscribe(`/user/${userId}/progress`, (message) => {
        const update = JSON.parse(message.body);
        console.log("Received update:", update);
        setProgressUpdates((prev) => [...prev, update]);
        toast.info(`Progress: ${update.message}`);
      });
      client.subscribe("/topic/system", (message) => {
        const update = JSON.parse(message.body);
        toast.info(`System Update: ${update.message}`);
      });
    };

    client.onStompError = (frame) => {
      setError("WebSocket connection error");
      toast.error("WebSocket connection error");
    };

    client.onWebSocketError = (event) => {
      setError("Network connection error");
      toast.error("Network connection error");
    };

    client.onDisconnect = () => {
      setIsConnected(false);
      setError("Disconnected from WebSocket");
    };

    client.activate();
    clientRef.current = client;

    return () => {
      if (clientRef.current) {
        clientRef.current.deactivate();
      }
    };
  }, [userId]);

  return {
    isConnected,
    progressUpdates,
    error,
    clearProgressUpdates: () => setProgressUpdates([]),
  };
};
