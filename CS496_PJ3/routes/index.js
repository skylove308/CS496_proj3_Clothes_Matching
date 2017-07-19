module.exports = function(app, Clothes, Persont)
{
   app.post('/api/closet', function(req, res) {
      var clothes = new Clothes();
      clothes.name = req.body.name;
      clothes.image = req.body.image;
      clothes.gender = req.body.gender;      
      clothes.type = req.body.type;
      clothes.tone = req.body.tone;
	  clothes.face = req.body.face;
	  clothes.bodies = req.body.bodies;
	  clothes.price = req.body.price;

      clothes.save(function(err){
         if(err){
            console.error(err);
            res.json({result: 0});
            return;
         }

         res.json({result: 1});
      });
   });
   
   app.get('/api/closet/clothes/:gender/:type/:tone/:face/:bodies', function(req, res){
       Clothes.find({gender: {"$regex" : req.params.gender, "$options":"i"}, 
					type: {"$regex" : req.params.type, "$options":"i"}, 
					tone: {"$regex" : req.params.tone, "$options":"i"}, 
					face: {"$regex" : req.params.face, "$options":"i"}, 
					bodies: {"$regex" : req.params.bodies, "$options":"i"}}, 
					{_id: 0, name: 1, image: 1, price: 1}, function(err, clothes){
           if(err) return res.status(500).json({error: err});
           if(!clothes) return res.status(404).json({error: 'person not found'});
           res.json(clothes);
       });
   });
   
   app.post('/api/person', function(req, res) {
      var person = new Persont();
	  person.fbid = req.body.fbid;
      person.name = req.body.name;
      person.image = req.body.image;
      person.gender = req.body.gender;      
      person.email = req.body.email;
	  person.mytypelist = req.body.mytypelist;

      person.save(function(err){
         if(err){
            console.error(err);
            res.json({result: 0});
            return;
         }

         res.json({result: 1});
      });
   });
   
   app.get('/api/person/:person_fbid', function(req, res){
       Persont.find({fbid: req.params.person_fbid}, 
					{_id: 0, fbid: 1, name: 1, image: 1, gender: 1, email: 1, mytypelist: 1}, function(err, person){
           if(err) return res.status(500).json({error: err});
           if(!person) return res.status(404).json({error: 'person not found'});
           res.json(person);
       });
   });
   
   app.delete('/api/person/:person_fbid', function(req, res){
	   Persont.remove({fbid: req.params.person_fbid}, function(err, output){
           if(err) return res.status(500).json({ error: "database failure" });

           /* ( SINCE DELETE OPERATION IS IDEMPOTENT, NO NEED TO SPECIFY )
           if(!output.result.n) return res.status(404).json({ error: "person not found" });
           res.json({ message: "person deleted" });
           */

           res.status(204).end();
       });
   });
   
   app.put('/api/person/:person_fbid',function(req, res){
	   Persont.update({fbid: person_fbid},{$push : {mytypelist: {$each: [req.body.mytype]}}}, done);
   });
   
   app.put('/api/person/clear/:person_fbid', function(req, res){
	   Persont.update({fbid: person_fbid}, {mytypelist:[]}, done);
   });
}