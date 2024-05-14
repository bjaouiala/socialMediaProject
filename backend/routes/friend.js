const express = require('express')
const route = express.Router()
const query = require('../db/queries')
const fs = require('fs')
const { error } = require('console')

var state = ['PENDING','ACCEPTED']

route.post('/:followedid/:followerid',async(req,res)=>{
    try{
        const {followedid,followerid} = req.params;
        await query.saveFriendRelation(followedid,followerid)
        res.json('added succefully')
    }catch(error){
        console.log(error)
        res.status(500).json(error)
    }  
})
route.delete('/:followedid/:followerid',async(req,res)=>{
    try{
        const {followedid,followerid} = req.params;
        await query.deleteFriendRelation(followerid,followedid)
        res.json('delete succefully')
    }catch(error){
        console.log(error)
        res.status(500).json(error)
    }  
});
route.get('/friendPosts/:followerid/:followedid',async (req,res)=>{
    try{
        const {followedid,followerid} = req.params;
        const user = await query.getFriendById(followerid,followedid)
        console.log(user)
        if(user){
            const profilePath = user[0].photoDeProfile;
            const couverturePath = user[0].photoDeCouverture;
            if (profilePath){
              const profileData = fs.readFileSync(profilePath,{encoding:"base64"});
            user[0].photoDeProfile = profileData;
            }
            if(couverturePath ){
              const couvertureData= fs.readFileSync(couverturePath,{encoding:"base64"});
            user[0].photoDeCouverture= couvertureData
            }
        
         res.json(user[0])
    }

}catch(error){
        console.log(error)
        res.status(500)

    }

}),

route.put('/:followerid/:followedid',async (req,res)=>{
    try{
        const {followerid,followedid} = req.params;
        await query.acceptFriendRequest(followerid,followedid)
        res.json("updated succefully")
    }catch(error){
        console.log(error)
        res.status(500).json("error")
    }
})
route.put('/blockFriend/:followedid/:followerid',async (req,res)=>{
    try{
        const {followerid,followedid} = req.params;
        await query.BlockFriendRequest(followerid,followedid)
        res.json("updated succefully")
    }catch(error){
        console.log(error)
        res.status(500).json.apply(error)
    }
  })

  route.get("/:userId",async(req,res)=>{
    const userId = req.params.userId;
    try{
        const FriendPost= await query.getWelcomePage(userId)
        console.log(FriendPost)
        if(FriendPost){
            for(const friend of FriendPost){
                const postPath = friend.postFile;
                const profilepath = friend.photoDeProfile;
                if(postPath){
                    const postData = fs.readFileSync(postPath,{encoding : "base64"});
                friend.postFile = postData
                }
                if(profilepath){
                    const postData = fs.readFileSync(profilepath,{encoding : "base64"});
                    friend.photoDeProfile = postData;
                }
            }
            res.json(FriendPost)
        }else{
            res.status(404).json("not found")
        }

    }catch(error){
        console.log(error)
        res.status(500).json(error)
    }

  })

  route.get("/AcceptedFriends/:userId",async(req,res)=>{
    try{
        const userId = req.params.userId
        const FriendPost = await query.getAcceptedFriends(userId);
        console.log(FriendPost)
       if(FriendPost){
        for(const friend of FriendPost){
            const profilepath= friend.photoDeProfile;
            console.log(profilepath)
            if(profilepath){
                const profileData = fs.readFileSync(profilepath,{encoding:"base64"});
                friend.photoDeProfile = profileData;
            }
            
        }
        res.json(FriendPost);
       }else{
        res.status(404).json("no user found")
       }
     
        }catch(error){
            console.log(error)
            res.status(500).json("error")
        }
  })

  route.get("/friendRequest/:userId",async(req,res)=>{
    try{
    const userId = req.params.userId
    const FriendPost = await query.getFrienRequest(userId);
    console.log(FriendPost)
   if(FriendPost){
    for(const friend of FriendPost){
        const profilepath= friend.photoDeProfile;
        console.log(profilepath)
        if(profilepath){
            const profileData = fs.readFileSync(profilepath,{encoding:"base64"});
            friend.photoDeProfile = profileData;
        }
        
    }
    res.json(FriendPost);
   }else{
    res.json("not found ")
   }
 
    }catch(error){
        console.log(error)
        res.status(500).json("error")
    }
   

  })

  route.get("/blockedFriend/:id",async (req,res)=>{
    try{
        const {id} = req.params;
        const users=await query.getbloCkedFriends(id);
        
        if(users){
            console.log(users)
            for(const user of users){
                if(user.photoDeProfile){
                    const photoPath = user.photoDeProfile;
                    const photoData = fs.readFileSync(photoPath,{encoding:"base64"})
                    user.photoDeProfile = photoData
                }
            }
       
            res.json(users)
        }else{
            res.status(404).json("no user found")
        }
    }catch(error){
        console.log(error)
        res.json(error)
    }

  })
  route.delete("/unblockFriend/:followerid/:followedid",async (req,res)=>{
    const {followerid,followedid} = req.params;
    console.log(followerid,followedid)
    try{
        await query.unblockFriend(followerid,followedid)
        res.json("deleted succefully")
    }catch{
        console.log(error)
        res.status(500).json(error)
    }

  })


module.exports = route