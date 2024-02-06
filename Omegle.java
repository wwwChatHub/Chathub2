const socket = new WebSocket('https://omegleclone.lv1metrash21.workers.dev/');
socket.onopen = () => {
  console.log('Connected to WebSocket server');
};
socket.onmessage = (event) => {
  console.log('Received message:', event.data);
};
