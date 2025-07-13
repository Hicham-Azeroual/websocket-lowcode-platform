import React from "react";
import { toast } from "react-toastify";
import { useWebSocket } from "../hooks/useWebSocket";
import styles from "./ProgressTracker.module.css";

export const ProgressTracker = ({ userId, operationId }) => {
  const { privateUpdates } = useWebSocket(userId);

  // Filter private updates for the current operation
  const operationUpdates = privateUpdates.filter(
    (update) => update.operationId === operationId
  );

  // Get the latest private update for this operation
  const latestUpdate = operationUpdates[operationUpdates.length - 1];

  // Always call hooks at the top level!
  React.useEffect(() => {
    if (latestUpdate) {
      if (latestUpdate.type === "GENERATION_COMPLETED") {
        toast.success(latestUpdate.message);
      } else if (latestUpdate.type === "GENERATION_ERROR") {
        toast.error(latestUpdate.message);
      }
    }
  }, [latestUpdate]);

  // Only show the tracker if there is at least one private update for this operation
  // AND the operation is not completed or errored
  if (
    !latestUpdate ||
    latestUpdate.type === "GENERATION_COMPLETED" ||
    latestUpdate.type === "GENERATION_ERROR"
  ) {
    return null;
  }

  // Header text: show 'Generating: <step>' if available, else just 'Generating...'
  const headerText = latestUpdate.step
    ? `Generating: ${latestUpdate.step}`
    : "Generating...";

  // Format JSON with syntax highlighting, omitting isPublic
  const formatJson = (obj) => {
    if (!obj) return null;
    // Omit isPublic from the object (do not assign to a variable to avoid linter warning)
    const { isPublic: _omit, ...rest } = obj;
    const json = JSON.stringify(rest, null, 2);
    // Simple syntax highlighting
    return json
      .replace(/("[^"]+": )/g, `<span class='${styles.key}'>$1</span>`) // keys
      .replace(/: ("[^"]*")/g, `: <span class='${styles.string}'>$1</span>`) // string values
      .replace(/: (\d+)/g, `: <span class='${styles.number}'>$1</span>`) // numbers
      .replace(
        /: (true|false)/g,
        `: <span class='${styles.boolean}'>$1</span>`
      ); // booleans
  };

  return (
    <div className={styles.progressTracker}>
      <div className={styles.header}>
        <span className={styles.spinner}></span>
        <span className={styles.generatingText}>{headerText}</span>
      </div>
      <div className={styles.codeBlock}>
        <pre
          className={styles.pre}
          dangerouslySetInnerHTML={{ __html: formatJson(latestUpdate) }}
        />
      </div>
    </div>
  );
};
