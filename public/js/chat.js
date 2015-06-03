var $chat = $('#chat');
$chat.scrollTop($chat[0].scrollHeight);

var socket = new WebSocket(wsEndpoint.url());

socket.onmessage = function(event) {
	$chat.append(event.data);
	$chat.scrollTop($chat[0].scrollHeight);
};

function sendMessage() {
	var text = $('#message').val();
	socket.send(text);
}