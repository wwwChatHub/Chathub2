addEventListener('fetch', (event) => {
  event.respondWith(handleRequest(event.request));
});

async function handleRequest(request) {
  const { readable, writable } = new TransformStream();
  const ws = new WebSocketPair();

  // Respond to regular HTTP requests
  if (request.headers.get('Upgrade') !== 'websocket') {
    return new Response('WebSocket connection required.', { status: 400 });
  }

  // Accept WebSocket connection
  const [clientSocket, serverSocket] = ws;
  const { protocol, pathname, search, hash } = new URL(request.url);
  const headers = new Headers(request.headers);

  // Respond to WebSocket handshake
  const response = new Response(null, {
    status: 101,
    webSocket: serverSocket,
    headers: headers,
  });

  // Handle WebSocket messages
  serverSocket.addEventListener('message', async (event) => {
    const text = event.data;
    const javaAppUrl = 'https://omegleclone.lv1metrash21.workers.dev/';

    try {
      // Make a POST request to your Java application's endpoint
      const response = await fetch(javaAppUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          text: text, // Include the text in the request body
        }),
      });

      if (!response.ok) {
        throw new Error('Failed to send message to Java application');
      }
    } catch (error) {
      console.error(error);
    }
  });

  return response;
}
