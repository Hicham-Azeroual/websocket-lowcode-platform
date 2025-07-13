import React, { useState } from "react";
import { useWebSocket } from "../hooks/useWebSocket";
import styles from "./GlobalNotifications.module.css";

export const GlobalNotifications = () => {
  const { publicUpdates } = useWebSocket();
  const [visible, setVisible] = useState(true);

  if (!visible || !publicUpdates.length) return null;

  return (
    <div className={styles.globalNotifications}>
      <div className={styles.headerRow}>
        <h3 className={styles.header}>System Notifications</h3>
        <button
          className={styles.closeBtn}
          onClick={() => setVisible(false)}
          aria-label="Close notifications"
        >
          ×
        </button>
      </div>
      <ul className={styles.list}>
        {publicUpdates.map((update, idx) => (
          <li key={idx} className={styles.item}>
            <span className={styles.time}>
              {new Date(update.timestamp).toLocaleTimeString()}
            </span>
            <span className={styles.message}>{update.message}</span>
          </li>
        ))}
      </ul>
    </div>
  );
};
