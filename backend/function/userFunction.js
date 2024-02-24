const jwt = require('jsonwebtoken')
const query = require('../db/queries')
require('dotenv').config();
module.exports = {
    saveUser(req,res,next){
        let user = req.body;
        let email = req.body.email;
        query.findUserByEmail(email)
        .then((existUser) => {
          if (existUser.length != 0){
            console.log(existUser)
            res.json("user exist");
          }else{
            if(user.role == null){
              user.role = "USER"
            }else{
              user.role = "ADMIN"
            }
            query.addUser(user)
            .then(() =>{
              res.json(user);
            }).catch((error) =>{
              res.json(error);
            })
          }

        }).catch((error)=>{
          res.status(500).json(error);
        })
    }
}