var $chat = $('#chat');
$chat.scrollTop($chat[0].scrollHeight);

var socket = new WebSocket(wsEndpoint.url());

socket.onmessage = function(event) {
	var data = event.data;
	if (data[0] != '<') {
		var opMsg = JSON.parse(data);
		var op = opMsg.op;
		var data = opMsg.data;
		if (op === 'connected' && $('#' + data).length == 0) {
			$('#users-list').append('<p id="' + data + '">' + data + '</p>');
			return;
		}
		if (op === 'disconnected') {
			$('#' + data).remove();
			return;
		}
	} else {
		$chat.append(event.data);
		$chat.scrollTop($chat[0].scrollHeight);
	}
};

function sendMessage() {
	var text = $('#message').val();
	socket.send(text);
}