var firebase = require("firebase");

var config = {
   apiKey: "AIzaSyCfzPkYsLtY1adV3U5ZLqrCm0soAFYJUFk",
   authDomain: "safecheck-2017.firebaseapp.com",
   databaseURL: "https://safecheck-2017.firebaseio.com",
   projectId: "safecheck-2017",
   storageBucket: "",
   messagingSenderId: "752359854545"
};

firebase.initializeApp(config);

var accountSid = 'ACfce496f254e2545a7b8251b1d7537302'; // Your Account SID from www.twilio.com/console
var authToken = '1e90c12a9ff2bfd7c7279d999975f7b1';   // Your Auth Token from www.twilio.com/console

var twilio = require('twilio'),
client = twilio(accountSid, authToken);
// cronJob = require('cron').CronJob;

var numbers = ['+14254453074', '+16788231926'];

//for( var i = 0; i < numbers.length; i++ ) {
//    client.messages.create({
//        body: 'no more yo',
//        to: numbers[i],  // Text this number
//        from: '+12064830490' // From a valid Twilio number
//    })
//    .then((message) => console.log(message.sid));
//}

var express = require('express'),
bodyParser = require('body-parser'),
app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

var server = app.listen(3000, function() {
    console.log('Listening on port %d', server.address().port);
});

// var Firebase = require('firebase'),
// usersRef = new Firebase('https://safecheck-2017.firebaseio.com/users/');
var database = firebase.database();

//var numbers = [];
//usersRef.on('child_added', function(snapshot) {
//numbers.push( snapshot.val() );
//  console.log( 'Added number ' + snapshot.val() );
//});

app.post('/message', function (req, res) {
    var fromNum = req.body.From;
    var text = req.body.Body.trim();
    var contactsRef = database.ref('/users/' + fromNum + '/contacts').once('value').then(function(snapshot) {
        var contacts = snapshot.val();

        for( var i = 0; i < contacts.length; i++ ) {
            client.messages.create({
                body: text,
                to: contacts[i],
                from: '+12064830490'
            })
            .then((message) => console.log(message.sid));
        }
    });

    // var resp = new twilio.twiml.MessagingResponse();
    // if( req.body.Body.trim().toLowerCase() === 'subscribe' ) {
    //     var fromNum = req.body.From;
    //     if(numbers.indexOf(fromNum) !== -1) {
    //         resp.message('You already subscribed!');
    //     } else {
    //         resp.message('Thank you, you are now subscribed. Reply "STOP" to stop receiving updates.');
    //         usersRef.push(fromNum);
    //     }
    // } else {
    //     resp.message('Welcome to Daily Updates. Text "Subscribe" receive updates.');
    // }
    res.writeHead(200, {
        'Content-Type':'text/xml'
    });
//    res.end(resp.toString());
});

// return firebase.database().ref('/users/' + userId).once('value').then(function(snapshot) {
//     var username = (snapshot.val() && snapshot.val().username) || 'Anonymous';
//     // ...
// });
