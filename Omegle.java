const socket = new WebSocket('https://omegleclone.lv1metrash21.workers.dev/');
socket.onopen = () => {
  console.log('Connected to WebSocket server');
};
socket.onmessage = (event) => {
  console.log('Received message:', event.data);
};

export default {
  async scheduled(event, env, ctx) {
    console.log(event.scheduledTime)
  },
}
