// Require express and create an instance of it
var express = require('express');
var app = express();
const MongoClient = require('mongodb').MongoClient;

// on the request to root (localhost:3000/)
app.get('/', function (req, res) {
    res.send('My first express http server. Sahil is a hoe.');
});

// On localhost:3000/welcome
app.get('/welcome', function (req, res) {
    res.send('Hello welcome to my http server made with express. Sahil is a hoe.');
});

// Get barcode number from db
app.get('/barcode/:barNum', function(req, res){

});

// Change the 404 message modifing the middleware
app.use(function(req, res, next) {

    
    res.status(404).send("Sorry, that route doesn't exist. Have a nice day :)");


    
});

// start the server in the port 3000 !
app.listen(3000, function () {
    console.log('Example app listening on port 3000.');

    const uri = "mongodb+srv://admin:admin@ecocluster-9oc4s.mongodb.net/test?retryWrites=true&w=majority"
    MongoClient.connect(uri, function(err, client) {
        if(err) {
             console.log('Error occurred while connecting to MongoDB Atlas...\n',err);
        }
        console.log('Connected database...');
        const collection = client.db("test").collection("devices");
        // perform actions on the collection object
        client.close();
     });

});

