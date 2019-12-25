//connecting to our signaling server 

var loc = window.location, wsUri;
if (loc.protocol === "https:") {
	wsUri = "wss:";
} else {
	wsUri = "ws:";
}
wsUri += "//" + loc.host;
wsUri += "/socket";
var conn = new WebSocket(wsUri);

conn.onopen = function() {
	console.log("Connected to the signaling server");
	initialize();
};

conn.onclose = function() {
	console.log("Connected to the signaling server failed");
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
		case "terminate":
			handleTerminate();
			break;
		default:
			break;
	}
};

var constraints = {
	video: {
		frameRate: {
			ideal: 10,
			max: 15
		},
		width: 1280,
		height: 720,
		facingMode: "user"
	}
};

function send(message) {
	conn.send(JSON.stringify(message));
}

var peerConnection;
var input = document.getElementById("messageInput");
var sendChannel;
var receiveChannel;
var sendButton = document.getElementById('sendButton');
var removeVideo = document.getElementById('removeVideo');
var localVideo = document.getElementById('localVideo');
let localStream;
function initialize() {
	console.log("init");

	if (peerConnection) {
		peerConnection.close();
	}
	if (sendChannel) {
		sendChannel.close();
	}
	if (receiveChannel) {
		receiveChannel.close();
	}
	const configuration = {};
	peerConnection = new RTCPeerConnection(configuration);
	peerConnection.addEventListener('track', gotRemoteStream);
	// Setup ice handling
	peerConnection.onicecandidate = function(event) {
		if (event.candidate) {
			send({
				event: "candidate",
				data: event.candidate
			});
		}
	};

	// creating data channel
	sendChannel = peerConnection.createDataChannel("sendChannel");

	peerConnection.ondatachannel = function(event) {
		console.log("ondatachannel");
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
	sendChannel.onopen = function(event) {
		if (sendChannel) {
			console.log("Send channel's status has changed to "
				+ sendChannel.readyState);
		}
		sendButton.disabled = false;
	};
	sendChannel.onclose = function(event) {
		if (sendChannel) {
			console.log("Send channel's status has changed to "
				+ sendChannel.readyState);
		}
		sendButton.disabled = true;
	};
}

function gotRemoteStream(e) {
	console.log('gotRemoteStream')
	if (remoteVideo.srcObject !== e.streams[0]) {
		remoteVideo.srcObject = e.streams[0];
		console.log('pc2 received remote stream');
	}
}

function handleReceiveChannelStatusChange(event) {
	if (receiveChannel) {
		console.log("Receive channel's status has changed to "
			+ receiveChannel.readyState);
	}
}

const offerOptions = {
	offerToReceiveAudio: 1,
	offerToReceiveVideo: 1
};
function createOffer() {
	navigator.mediaDevices.getUserMedia({ audio: true, video: true }).then(function(stream) {
		localVideo.srcObject = stream;
		localStream = stream;
		localStream.getTracks().forEach(track => peerConnection.addTrack(track, localStream));
		peerConnection.createOffer(function(offer) {
			send({
				event: "offer",
				data: offer
			});
			peerConnection.setLocalDescription(offer);
		}, function(error) {
			alert("Error creating an offer");
		}, offerOptions);
	}).catch(function(err) { console.log(err); });

}

function handleOffer(offer) {
	peerConnection.setRemoteDescription(new RTCSessionDescription(offer));

	// create and send an answer to an offer
	peerConnection.createAnswer(function(answer) {
		peerConnection.setLocalDescription(answer);
		send({
			event: "answer",
			data: answer
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

function handleTerminate() {
	initialize();
};

function sendMessage() {
	sendChannel.send(input.value);
	input.value = "";
}
