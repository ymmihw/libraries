//connecting to our signaling server 
var conn = new WebSocket('ws://localhost:8080/socket');

conn.onopen = function() {
	console.log("Connected to the signaling server");
	initialize();
};

conn.onmessage = function(msg) {
	console.log("Got message", msg.data);
	var content = JSON.parse(msg.data);
	var data = content.data;
	switch (content.event) {
	// when somebody wants to call us
	case "offer":
		handleOffer(data);
		break;
	case "answer":
		handleAnswer(data);
		break;
	// when a remote peer sends an ice candidate to us
	case "candidate":
		handleCandidate(data);
		break;
	default:
		break;
	}
};

function send(message) {
	conn.send(JSON.stringify(message));
}

var peerConnection;
var input = document.getElementById("messageInput");
var sendChannel;
var receiveChannel;

function initialize() {
	var configuration = null;

	peerConnection = new RTCPeerConnection();

	// Setup ice handling
	peerConnection.onicecandidate = function(event) {
		if (event.candidate) {
			send({
				event : "candidate",
				data : event.candidate
			});
		}
	};

	// creating data channel
	sendChannel = peerConnection.createDataChannel("sendChannel");

	peerConnection.ondatachannel = function(event) {
		receiveChannel = event.channel;
		receiveChannel.onmessage = function(event) {
			console.log("ondatachannel message:", event.data);
		};
		receiveChannel.onopen = handleReceiveChannelStatusChange;
		receiveChannel.onclose = handleReceiveChannelStatusChange;
	};

	sendChannel.onerror = function(error) {
		console.log("Error occured on datachannel:", error);
	};
	sendChannel.onopen = handleSendChannelStatusChange;
	sendChannel.onclose = handleSendChannelStatusChange;
}
function handleReceiveChannelStatusChange(event) {
	if (receiveChannel) {
		console.log("Receive channel's status has changed to "
				+ receiveChannel.readyState);
	}
}
function handleSendChannelStatusChange(event) {
	if (sendChannel) {
		console.log("event:", event.data);
	}
}

function createOffer() {
	peerConnection.createOffer(function(offer) {
		send({
			event : "offer",
			data : offer
		});
		peerConnection.setLocalDescription(offer);
	}, function(error) {
		alert("Error creating an offer");
	});
}

function handleOffer(offer) {
	peerConnection.setRemoteDescription(new RTCSessionDescription(offer));

	// create and send an answer to an offer
	peerConnection.createAnswer(function(answer) {
		peerConnection.setLocalDescription(answer);
		send({
			event : "answer",
			data : answer
		});
	}, function(error) {
		alert("Error creating an answer");
	});

};

function handleCandidate(candidate) {
	peerConnection.addIceCandidate(new RTCIceCandidate(candidate));
};

function handleAnswer(answer) {
	peerConnection.setRemoteDescription(new RTCSessionDescription(answer));
	console.log("connection established successfully!!");
};

function sendMessage() {
	sendChannel.send(input.value);
	input.value = "";
}
