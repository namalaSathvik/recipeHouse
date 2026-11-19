// Chef Bot - chat widget. Works on every page that includes this file.

// 1. Build the chat window using JavaScript
var chatHTML =
  '<button id="chat-button">&#128172;</button>' +
  '<div id="chat-box">' +
  '  <div id="chat-header">&#127859; Chef Bot - ask me about recipes</div>' +
  '  <div id="chat-messages"></div>' +
  '  <div id="chat-quick">' +
  '    <button>Easy recipes</button>' +
  '    <button>Vegetarian recipes</button>' +
  '    <button>Suggest something</button>' +
  '  </div>' +
  '  <form id="chat-form">' +
  '    <input id="chat-input" type="text" placeholder="Type your question..." autocomplete="off">' +
  '    <button id="chat-send" type="submit">Send</button>' +
  '  </form>' +
  '</div>';

var holder = document.createElement('div');
holder.innerHTML = chatHTML;
document.body.appendChild(holder);

var chatBox = document.getElementById('chat-box');
var messages = document.getElementById('chat-messages');
var input = document.getElementById('chat-input');

// 2. Show / hide the chat window
document.getElementById('chat-button').onclick = function () {
  if (chatBox.style.display === 'flex') {
    chatBox.style.display = 'none';
  } else {
    chatBox.style.display = 'flex';
    if (messages.children.length === 0) {
      addMessage('Hi! I am Chef Bot. Ask me for ingredients, steps, easy dishes or a surprise!', 'bot');
    }
  }
};

// 3. Add one message to the chat
function addMessage(text, who) {
  var div = document.createElement('div');
  div.className = 'msg ' + who;
  div.textContent = text;      // textContent is safe (no HTML injection)
  messages.appendChild(div);
  messages.scrollTop = messages.scrollHeight;
  return div;
}

// 4. Send the question to ChatServlet and show the reply
function sendMessage(text) {
  addMessage(text, 'user');
  var waiting = addMessage('Thinking...', 'bot');

  fetch('chat', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: 'message=' + encodeURIComponent(text)
  })
    .then(function (response) { return response.text(); })
    .then(function (reply) { waiting.textContent = reply; })
    .catch(function () { waiting.textContent = 'Sorry, I could not reach the server.'; });
}

// 5. When the form is submitted
document.getElementById('chat-form').onsubmit = function (e) {
  e.preventDefault();               // stop page reload
  var text = input.value.trim();
  if (text !== '') {
    sendMessage(text);
    input.value = '';
  }
};

// 6. Quick buttons
var quickButtons = document.querySelectorAll('#chat-quick button');
for (var i = 0; i < quickButtons.length; i++) {
  quickButtons[i].onclick = function () { sendMessage(this.textContent); };
}
