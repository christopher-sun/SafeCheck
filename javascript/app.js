var twilio = require('twilio'),
client = twilio('ACCOUNTSID', 'AUTHTOKEN');
// cronJob = require('cron').CronJob;

var numbers = ['YOURPHONENUMBER', 'YOURFRIENDSPHONENUMBER'];

for( var i = 0; i < numbers.length; i++ ) {
    client.sendMessage( { to:numbers[i], from:'YOURTWILIONUMBER', body:'Hello! Hope you’re having a good day.'}, function( err, data ) {
        console.log( data.body );
    });
}

var express = require('express'),
bodyParser = require('body-parser'),
app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

app.post('/message', function (req, res) {
    var resp = new twilio.TwimlResponse();
    resp.message('Thanks for subscribing!');
    res.writeHead(200, {
        'Content-Type':'text/xml'
    });
    res.end(resp.toString());
});

var server = app.listen(3000, function() {
    console.log('Listening on port %d', server.address().port);
});

var Firebase = require('firebase'),
usersRef = new Firebase('https://safecheck-2017.firebaseio.com/Users/');

var config = {
    apiKey: "AIzaSyCfzPkYsLtY1adV3U5ZLqrCm0soAFYJUFk",
    authDomain: "safecheck-2017.firebaseapp.com",
    databaseURL: "https://safecheck-2017.firebaseio.com",
    projectId: "safecheck-2017",
    storageBucket: "",
    messagingSenderId: "752359854545"
};
firebase.initializeApp(config);

var numbers = [];
usersRef.on('child_added', function(snapshot) {
numbers.push( snapshot.val() );
  console.log( 'Added number ' + snapshot.val() );
});

app.post('/message', function (req, res) {
    var resp = new twilio.TwimlResponse();
    if( req.body.Body.trim().toLowerCase() === 'subscribe' ) {
        var fromNum = req.body.From;
        if(numbers.indexOf(fromNum) !== -1) {
            resp.message('You already subscribed!');
        } else {
            resp.message('Thank you, you are now subscribed. Reply "STOP" to stop receiving updates.');
            usersRef.push(fromNum);
        }
    } else {
        resp.message('Welcome to Daily Updates. Text "Subscribe" receive updates.');
    }

    res.writeHead(200, {
        'Content-Type':'text/xml'
    });
    res.end(resp.toString());
});
