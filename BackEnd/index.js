var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.get('/', function(req, res){
  res.sendFile(__dirname + '/index.html');
});

io.emit('some event', { for: 'everyone' });

io.on('connection', function(socket){
  socket.on('bb', function(msg){
    io.emit('bb', msg);
  });
});

http.listen(3000, function(){
  console.log('listening on *:3000');
});