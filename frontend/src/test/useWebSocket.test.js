/* eslint-env jest, node */
// src/test/useWebSocket.test.js
import { renderHook, act } from "@testing-library/react";
import { useWebSocket } from "../hooks/useWebSocket";

// Mock SockJS and StompJS for testing
jest.mock("sockjs-client", () => ({
  __esModule: true,
  default: jest.fn(() => ({})),
}));

jest.mock("@stomp/stompjs", () => {
  const mockClient = {
    activate: jest.fn(),
    deactivate: jest.fn(),
    publish: jest.fn(),
    subscribe: jest.fn(),
    onConnect: jest.fn(),
    onDisconnect: jest.fn(),
    onStompError: jest.fn(),
  };
  return {
    __esModule: true,
    Client: jest.fn(() => mockClient),
  };
});

describe("useWebSocket", () => {
  let mockClient;
  let originalLog;
  let originalError;

  beforeAll(() => {
    // Suppress console.log and console.error during tests
    originalLog = console.log;
    originalError = console.error;
    console.log = jest.fn();
    console.error = jest.fn();
  });

  afterAll(() => {
    // Restore original console methods
    console.log = originalLog;
    console.error = originalError;
  });

  beforeEach(() => {
    jest.clearAllMocks();
    mockClient = jest.requireMock("@stomp/stompjs").Client();
  });

  test("should initialize with isConnected false and empty progressUpdates", () => {
    const { result } = renderHook(() => useWebSocket("user1"));
    expect(result.current.isConnected).toBe(false);
    expect(result.current.progressUpdates).toEqual([]);
  });

  test("should connect and set isConnected to true", async () => {
    const { result } = renderHook(() => useWebSocket("user1"));
    await act(async () => {
      mockClient.onConnect();
    });
    expect(mockClient.activate).toHaveBeenCalled();
    expect(mockClient.subscribe).toHaveBeenCalledTimes(2);
    expect(mockClient.publish).toHaveBeenCalledWith({
      destination: "/app/subscribe",
      body: "user1",
    });
    expect(result.current.isConnected).toBe(true);
  });

  test("should receive and store progress updates", async () => {
    const { result } = renderHook(() => useWebSocket("user1"));
    await act(async () => {
      mockClient.onConnect();
    });
    const userMessage = { id: 1, status: "STARTED" };
    await act(async () => {
      const userCallback = mockClient.subscribe.mock.calls[0][1];
      userCallback({ body: JSON.stringify(userMessage) });
    });
    expect(result.current.progressUpdates).toEqual([
      { ...userMessage, isPublic: false },
    ]);
    const systemMessage = { id: 2, status: "COMPLETED" };
    await act(async () => {
      const systemCallback = mockClient.subscribe.mock.calls[1][1];
      systemCallback({ body: JSON.stringify(systemMessage) });
    });
    expect(result.current.progressUpdates).toEqual([
      { ...userMessage, isPublic: false },
      { ...systemMessage, isPublic: true },
    ]);
  });

  test("should handle STOMP error and set isConnected to false", async () => {
    const { result } = renderHook(() => useWebSocket("user1"));
    await act(async () => {
      mockClient.onConnect();
    });
    expect(result.current.isConnected).toBe(true);
    await act(async () => {
      mockClient.onStompError({ error: "Connection failed" });
    });
    expect(result.current.isConnected).toBe(false);
  });

  test("should handle disconnect and set isConnected to false", async () => {
    const { result } = renderHook(() => useWebSocket("user1"));
    await act(async () => {
      mockClient.onConnect();
    });
    expect(result.current.isConnected).toBe(true);
    await act(async () => {
      mockClient.onDisconnect();
    });
    expect(result.current.isConnected).toBe(false);
  });

  test("should clear progress updates", async () => {
    const { result } = renderHook(() => useWebSocket("user1"));
    await act(async () => {
      mockClient.onConnect();
      const userCallback = mockClient.subscribe.mock.calls[0][1];
      userCallback({ body: JSON.stringify({ id: 1, status: "STARTED" }) });
    });
    expect(result.current.progressUpdates).toHaveLength(1);
    await act(async () => {
      result.current.clearProgressUpdates();
    });
    expect(result.current.progressUpdates).toEqual([]);
  });

  test("should deactivate client on unmount", async () => {
    const { unmount } = renderHook(() => useWebSocket("user1"));
    await act(async () => {
      mockClient.onConnect();
    });
    expect(mockClient.activate).toHaveBeenCalled();
    await act(async () => {
      unmount();
    });
    expect(mockClient.deactivate).toHaveBeenCalled();
  });
});
