let express = require('express');
let route = express.Router()
const query = require('../db/queries');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
require('dotenv').config();

route.get('/:code',(req,res)=>{
    code=req.params.code;
   query.findUserByCodeConfirmation(code)
   .then((users)=>{
    if(users.length >0 ){
        const user = users[0];
        res.json(user);
    }else{
        res.status(404).json("user not found")
    }
   }).catch((error)=>{
    console.log(error)
    res.json(error);
   })
})

route.post('/', async(req,res)=> {
    try{
        let email = req.body.email;
        let password = req.body.password;
        let user = await query.findUserByEmail(email);
        if(user){
            let match = await bcrypt.compare(password,user[0].password);
            if(match){
             if(user.length != 0 && user[0].isVerified== true){
                 let token = jwt.sign({user},process.env.SECRET_KEY);
                 console.log(user)
                 res.json({token : token, user:user[0]});
             }else {
                 res.status(404).json("verify your credentials");
             }
            }else{res.status(404).json("user not found");}
     }
    }catch(error){
        console.log(error);
        res.status(500).json("network error");
    }
 
})



route.put('/', async (req, res, next) =>{
    try{
     let CodeConfirmation = req.body.CodeConfirmation;
    let user = await query.findUserByCodeConfirmation(CodeConfirmation);
    if(user){
     user.isVerified = true;
     await query.updateUserState(CodeConfirmation);
     res.json("user is verified");
   
    }else{
     res.status(404).json("user not found")
    }
   
    }catch(error){
     res.status(401).json("you have to verify your account")}
    
    
   
   });

module.exports = route;