var express = require('express');
var router = express.Router();
const query = require('../db/queries')
const jwt = require('jsonwebtoken');
const {send,generateCode} = require('../Helper/helperFunction');
const {upload} = require("../Middleware")
const fs = require('fs')




router.get("/search/:userId",async (req,res)=>{
  let ch = req.query.friend;
  const userId= req.params.userId;
  try{
    if(ch){
      const users= await query.searchFriend(ch,userId)
      console.log(users)
       if(users.length > 0 && users != null){
         for(const user of users){
           const photoPath = user.photoDeProfile
           if(photoPath){
             const photoData = fs.readFileSync(photoPath,{encoding:'base64'})
             user.photoDeProfile = photoData
           }
           
         }
         res.json(users)

    }else res.json([])
  
    }else{
      res.json([])
    }

    
  }catch(error){
    console.log(error)
    res.status(500).json(error)
  }
})





router.post('/', async (req, res, next) => {
  try {
    let user = req.body;
    console.log(user.Lastname)
    let emailRecuperation =req.body.emailRecuperation;
    let email = req.body.email;
    req.body.role = "User";
    user.CodeConfirmation = generateCode()
    await query.addUser(user);
    send(email,emailRecuperation,user.CodeConfirmation)
    return res.json("user saved");
 
  } catch (error) {
    console.log(error)
   if(error.code == "ER_DUP_ENTRY"){
  
    res.json("user exist")
   }
    
  }
});

router.put("/",async(req,res)=>{
  let email = req.body.email;
  let emailRecuperation = req.body.emailRecuperation;
  let user = await query.findUserByEmail(email);
  console.log(user)
  if(user.length !=0 ){
    user.CodeConfirmation = generateCode();
    await query.updateUserparEmail(email,user.CodeConfirmation);
    send(email,emailRecuperation,user.CodeConfirmation)
    .then((success)=>{
      res.json(user[0]);
    }).catch((error)=>{
      console.log(error)
      res.json(error);
    })

  }else{
    res.status(404)
  }

});

router.put("/changePassword",(req,res)=>{
  let code = req.body.CodeConfirmation;
  let password = req.body.password;

  query.updatePassword(code,password)
  .then((result)=>{
    res.status(200).json("password updated succefully")
  }).catch((error)=>{
    console.log(error)
    res.status(500).json("failed to update password");
  })
  

});

router.post("/images/:type/:id",upload.single('file'),async (req,res) =>{
    const id = req.params.id;
    const type = req.params.type;
    const post = req.body;
    const imagePath = req.file.path
    try{
        if(type == "photoDeProfile"){
            const result = await query.setUserProfilePicture(id,imagePath);
            if(result){
              await query.addPost(post,id,imagePath)
              res.json("profile picture upploaded succefully")
            }else{
              res.json("something wrong")
            }
        }else if( type == "photoDeCouverture"){
          const result = await query.setUsercouverturePicture(id,imagePath)
          if(result){
            await query.addPost(post,id,imagePath)
            res.json("couverture picture upploaded succefully")
          }else{
            res.json("something wrong")
          }
          
          
        }else{
          res.status(404).json("not found");
        }
    }catch(error){
      console.log(error)
      res.json(error)
    }
   

});

router.get("/:id",async (req,res)=>{
 try{
  const id = req.params.id;
  const user =await  query.getUserById(id)
  console.log(user)
  if(user){
    const profilePath = user[0].photoDeProfile;
    console.log(profilePath)
    const couverturePath = user[0].photoDeCouverture;
    if (profilePath){
      const profileData = fs.readFileSync(profilePath,{encoding:"base64"});
    user[0].photoDeProfile = profileData;
    }
    if(couverturePath){
      const couvertureData= fs.readFileSync(couverturePath,{encoding:"base64"});
    user[0].photoDeCouverture= couvertureData
    }

    
    res.json(user[0])
  }else{
    res.status(404).json("user not found")
  }
 }catch(error){
  console.log(error)
 
  res.status(500).json(error)
 }
  
});
router.put('/account/:id',async(req,res)=>{
  try{
    const id = req.params.id;
      const existuser = await query.findUserByid(id);
      const user = req.body;
      if(existuser){
        if(user.email){
          console.log(user.email);
            existuser.email= user.email;
        }else if(user.password){
          console.log(user.password)
            existuser.password = user.password;
        }else if(user.dateBirth){
            existuser.dateBirth = user.dateBirth;
        }else if(user.Firstname || user.Lastname){
          existuser.Firstname = user.Firstname;
          if(user.Lastname){
            existuser.Lastname = user.Lastname;
          }else{
            existuser.Lastname=""
          }

        }
        await query.updateuser(existuser,id)
        res.json("user upated succefully")

      }else{
        res.status(404).json("no user found with the given id")
      }


 
  }catch(error){
      console.log(error)
      res.status(500).json("error")
  }



})










module.exports = router;
