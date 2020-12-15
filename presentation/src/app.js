let stompClient = null;
let initValue = null;
let playerId = null;
let gameId = null;
let isPlayer1 = false;
const alertTxt = "<div class=\"alert alert-success\" role=\"alert\" >";

// Connect to websocket and listen to /response/turn endpoint
function connectWebSocket() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/response/turn/' + gameId, function (move) {
            showMove(JSON.parse(move.body));
        });
    });
}

// Register a player before starting the game
function registerPlayer(){
    let request = new XMLHttpRequest();
    request.open( "GET", "http://localhost:8080/register", false );
    request.send( null );
    let response =  JSON.parse(request.responseText);

    playerId = response.playerId;
    gameId = response.gameId;
    isPlayer1 = response.player1;
    initValue = response.initValue;
}

// Disconnect websocket
function disconnectWebSocket() {
    if (stompClient !== null)
        stompClient.disconnect();
}

// Init page by registering player, connecting to websocket and displaying divs acording to player 1 or 2
function init() {
    registerPlayer();

    if (isPlayer1) {
        let inputVal = document.getElementById('init_value');
        let btn = document.getElementById('startButton');

        inputVal.addEventListener("input", function(){
            btn.disabled = (this.value === '' || this.value <= 0);
        })
        $("#setIniValue").show();
        $("#playerAlert").append(alertTxt + "Playing as Player 1 - Game Id: " + gameId + "</div>")
    }
    else {
        if (initValue != null){
            $("#yourTurn").show();
            $("#turn_value").text(initValue);
        }

        $("#playerAlert").append(alertTxt + "Playing as Player 2 - Game Id: " + gameId + "</div>")
    }
    connectWebSocket();
}

// Function for first game move
function doStart() {
    doPlay(document.getElementById("init_value").value);
    $("#setIniValue").hide();
}

// Sends a request to the server with a move. If it is not the user's turn, it will receive an empty response
function doPlay(operation) {
    let request = {
        "playerId" : playerId,
        "gameId": gameId,
        "value": operation
    }

    stompClient.send("/request/turn/" + gameId, {}, JSON.stringify(request));
}

// On every game turn response, populate the moves table and display if player won or lost game
function showMove(move) {
    // Ignore any message missing operation and resultingValue
    if (move.operation === null && move.resultingValue === 0)
        return

    if ((!move.player1 && isPlayer1) || (move.player1 && !isPlayer1)) {
        if(move.won)
            showFinalMessage(false)
        else {
            $("#yourTurn").show();
            $("#waitPlayer").hide();
            $("#turn_value").text(move.resultingValue);
        }
    }
    else {
        if(move.won)
            showFinalMessage(true)
        else {
            $("#yourTurn").hide();
            $("#waitPlayer").show();
            $("#waiting_value").text(move.resultingValue);
        }
    }

    $("#moves").append("<tr>" +
        "<td>Player " + (move.player1 ? "1" : "2") + "</td>" +
        "<td>" + (move.operation == null ? "-" : move.operation) + "</td>" +
        "<td>" + move.resultingValue + "</td>" +
        "</tr>");
}

// Display the win or lose message on game end. Also disconnect from socket
function showFinalMessage(won) {
    $("#yourTurn").hide();
    $("#waitPlayer").hide();
    if (won)
        $("#youWon").show();
    else
        $("#youLost").show();
    disconnectWebSocket()
}

