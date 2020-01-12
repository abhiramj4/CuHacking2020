// Require express and create an instance of it
var express = require('express');
var server = express();

const body_parser = require("body-parser");

server.use(body_parser.json());

const db = require("./db");
const dbName = "EcoStep"
const collectionName = "EcoStep"
const port = 3000;



db.initialize(dbName, collectionName, function (dbCollection) { // successCallback
    // get all items
    dbCollection.find().toArray(function (err, result) {
       if (err) throw err;
       console.log(result);
 
       // << return response to client >>
    });
 
    // << db CRUD routes >>
    server.post("/items", (request, response) => {
        const item = request.body;
        dbCollection.insertOne(item, (error, result) => { // callback of insertOne
           if (error) throw error;
           // return updated list
           dbCollection.find().toArray((_error, _result) => { // callback of find
              if (_error) throw _error;
              response.json(_result);
           });
        });
     });

     server.get('/', (request, response) => {
        response.send('Hello from Express!')
      });
  
     server.get("/items/:id", (request, response) => {
        const itemId = request.params.id;
        var barcode = parseInt(itemId, 10)
        
        dbCollection.findOne({ barcode: barcode }, (error, result) => {
           if (error) throw error;
           // return item
           response.json(result);
        });
     });
  
     server.get("/items", (request, response) => {
        // return updated list
        dbCollection.find().toArray((error, result) => {
           if (error) throw error;
           response.json(result);
        });
     });
  
     server.put("/items/:id", (request, response) => {
        const itemId = request.params.id;
        const item = request.body;
        console.log("Editing item: ", itemId, " to be ", item);
  
        dbCollection.updateOne({ id: itemId }, { $set: item }, (error, result) => {
           if (error) throw error;
           // send back entire updated list, to make sure frontend data is up-to-date
           dbCollection.find().toArray(function (_error, _result) {
              if (_error) throw _error;
              response.json(_result);
           });
        });
     });
  
     server.delete("/items/:id", (request, response) => {
        const itemId = request.params.id;
        console.log("Delete item with id: ", itemId);
  
        dbCollection.deleteOne({ id: itemId }, function (error, result) {
           if (error) throw error;
           // send back entire updated list after successful request
           dbCollection.find().toArray(function (_error, _result) {
              if (_error) throw _error;
              response.json(_result);
           });
        });
     });
  
  }, function (err) { // failureCallback
     throw (err);
  });
  
  server.listen(port, () => {
     console.log(`Server listening at ${port}`);
  });