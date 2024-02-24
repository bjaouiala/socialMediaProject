const jwt = require('jsonwebtoken')
const query = require('../db/queries')
require('dotenv').config();

module.exports = {
    auth(req,res,next){
        let email = req.body.email;
        let password = req.body.password;

        query.login(email,password)
        .then((user)=>{
           if(user.length != 0) {
            let token = jwt.sign({user},process.env.SECRET_KEY)
            res.json(token)
           }else{
            res.status(404).json('user not found')
           }

        }).catch((error) =>{
            console.log(error);
            res.json(error);
        })
    }
}