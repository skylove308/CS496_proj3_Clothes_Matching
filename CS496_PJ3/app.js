var express		= require('express');
var app         = express();
var bodyParser  = require('body-parser');
var mongoose    = require('mongoose');
var Schema		= mongoose.Schema;

var clothesSchema = new Schema({
   name: String,
   image: String,
   gender: [String],
   type: [String],
   tone: [String],
   face: [String],
   bodies: [String],
   price: String
});

var persontSchema = new Schema({
	fbid: String,
	name: String,
	image: String,
	gender: String,
	email: String,
	mytypelist: [String]});

var Persont = mongoose.model('persont', persontSchema);
var Clothes = mongoose.model('clothese', clothesSchema);

module.exports = {Persont, Clothes};


var db = mongoose.connection;
db.on('error', console.error);
db.once('open', function(){
    // CONNECTED TO MONGODB SERVER
    console.log("Connected to mongod server");
});

mongoose.Promise = require('bluebird');
mongoose.connect('mongodb://localhost/closet');

app.use(bodyParser.urlencoded({ limit : '50mb', extended: true }));
app.use(bodyParser.json({limit : '50mb'}));

var port = process.env.PORT || 8090;

var router2 = require('./routes')(app, Clothes, Persont);

var server = app.listen(port, function(){
 console.log("Express server has started on port " + port)
});