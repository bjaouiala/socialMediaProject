const { promises } = require('nodemailer/lib/xoauth2');
const query = require('../db/queries');
require('dotenv').config();
const mailer =require('../MailerConfig');

module.exports = {
generateCode(){
    return Math.floor(100000 + Math.random() * 900000).toString();
  },

   
  
 send(email,emailRecuperation,codeR){
    mailer.mailOptions.from=email;
    mailer.mailOptions.to = emailRecuperation;
    mailer.mailOptions.text= `votre code d'activation ${codeR}`;
    return new Promise((resolve,reject)=>{
      mailer.transporter.sendMail(mailer.mailOptions,(error,info)=>{
        if(error){
          reject(error);
        }else{
          resolve(info.response);
        }
      })
    })
  }



}

   
    
