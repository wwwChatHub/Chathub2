const socket = new WebSocket('wss://your-websocket-server.com');

document.addEventListener('DOMContentLoaded', function() {
    const chatBox = document.getElementById('chat-box');
    const messageInput = document.getElementById('message-input');
    const sendButton = document.getElementById('send-button');

    function addMessage(message, sender) {
        const messageElement = document.createElement('div');
        messageElement.classList.add('message');
        if (sender === 'me') {
            messageElement.classList.add('me');
        } else {
            messageElement.classList.add('stranger');
        }
        messageElement.textContent = message;
        chatBox.appendChild(messageElement);
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    function sendMessage() {
        const message = messageInput.value.trim();
        if (message !== '') {
            addMessage(message, 'me');
            socket.send(message); // Send message to WebSocket server
            messageInput.value = '';
        }
    }

    sendButton.addEventListener('click', sendMessage);
    messageInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            sendMessage();
        }
    });

    // Handle WebSocket messages
    socket.onmessage = function(event) {
        const message = event.data;
        addMessage(message, 'stranger');
    };
});
