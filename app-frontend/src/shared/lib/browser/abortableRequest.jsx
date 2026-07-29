const createAbortableRequest = () => {
  const controller = new AbortController();

  const signal = controller.signal;

  const abort = () => {
    controller.abort();
  };

  return { signal, abort };
};

export default createAbortableRequest;
