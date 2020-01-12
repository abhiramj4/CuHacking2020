// Require express and create an instance of it
var express = require('express');
var app = express();

const body_parser = require("body-parser");

app.use(body_parser.json());

const db = require("./db");
const dbName = "EcoStep"
const collectionName = "EcoStep"

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
    
    
});

db.initialize(dbName, collectionName, function(dbCollection) { // successCallback
    console.log(dbCollection)
    // get all items
    dbCollection.find().toArray(function(err, result) {
        if (err) throw err;
          console.log(result);
    });

    // << db CRUD routes >>

}, function(err) { // failureCallback
    throw (err);
});


app.get("/items/:barcode", (request, response) => {
    const itemId = request.params.id;

    dbCollection.findOne({ barcode: itemId }, (error, result) => {
        if (error) throw error;
        // return item
        response.json(result);
    });
});
