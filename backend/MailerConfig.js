const nodemailer = require('nodemailer');
    const transporter = nodemailer.createTransport({
        service:'Gmail',
        host:'smtp.gmail.com',
        port:'465',
        secure:'true',
        auth:{
            user:'bjaouiala3@gmail.com',
            pass:'yzqs juxw jlvw utgu',
            
        }
    });

const mailOptions =  {
    from : '',
    to :'',
    subject: "confirm email",
    text: "",

}



  module.exports={transporter,mailOptions}
