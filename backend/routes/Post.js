const express = require('express');
const route = express.Router();
const query = require('../db/queries');
const {upload} = require("../Middleware");
const fs = require('fs');
const { error } = require('console');

route.post("/:type?/:id",upload.single("file"),async(req,res)=>{
    const post = req.body;
    const {id,type} = req.params;

    let postRes;
    console.log(type)
    try{
        let filePath;
        
       if(req.file != null){
         filePath = req.file.path
        postRes=await query.addPost(post,id,filePath)
       }else{
        postRes =await query.addpostWithoutFile(post,id)
        
       }

        if(postRes.length > 0){
            console.log(postRes[0])
            if(type=="photoDeProfile"){
               await query.updatePostIsProfile(postRes[0])
               await query.saveProfilePicture(filePath,id)
            }else if(type == "photoDeCouverture"){
                await query.updatePostIsCover(postRes[0])
                await query.savecoverPicture(filePath,id)
            }
        }
        res.json("post uploaded succefully")
    }catch (error){
        console.log(error);
        res.status(500).json(error)
    };

});

route.get("/posts/:id", async (req, res) => {
    try {
        const id = req.params.id;
        const posts = await query.getAllPostsByUserId(id);

        if (posts) {
            for (const post of posts) {
                const filePath = post.postFile
                const userPicturePath = post.photoDeProfile;
                
                if (filePath && filePath !== 'NULL') {
                    const fileData = fs.readFileSync(filePath, { encoding: "base64" });
                    post.postFile = fileData;
                }
                
                if (userPicturePath && userPicturePath !== 'NULL') {
                    const userPictureData = fs.readFileSync(userPicturePath, { encoding: "base64" });
                    post.photoDeProfile = userPictureData;
                }
            }
            
            res.json(posts);
        } else {
            res.status(404).json("Posts not found");
        }
    } catch (error) {
        console.error(error);
        res.status(500).json("Internal server error");
    }
});
route.get("/:id", async (req,res)=>{
    const id = req.params.id;
    try{
        const post = await query.getPostsById(id);
        console.log(post)
        if(post){
            const filePath = post[0].postFile;
            const postData = fs.readFileSync(filePath,{encoding:"base64"});
            post[0].postFile = postData;
            res.json(post[0]);
        }else{
            res.status(404).json("post not found");
        }
    }catch(error){
        res.status(500).json(error);
    }

})
route.post('/registerPost/:userId/:postId',async(req,res)=>{
    try{
        const {userId,postId} = req.params;
        await query.registerPost(userId,postId) 
        res.json("success")
    }catch(error){
        console.log(error)
        res.status(500).json('error')
    }
})
route.get("/AllRegistredPost/:userId",async(req,res)=>{
    try{
    const userId = req.params.userId
    const posts = await query.getAllRegistredPost(userId);
    console.log(posts)
   if(posts){
    for(const post of posts){
        const profilepath=post.postFile;
        console.log(profilepath)
        if(profilepath){
            const profileData = fs.readFileSync(profilepath,{encoding:"base64"});
            post.postFile = profileData;
        }
        
    }
    res.json(posts);
   }else{
    res.json("not found ")
   }
 
    }catch(error){
        console.log(error)
        res.status(500).json("error")
    }
   

  })

route.get('/getRegistredPost/:userId/:postId', async(req,res)=>{
    try{
        const {userId,postId} = req.params;
        const registredPost = await query.getRegistredPost(userId,postId)
        if(registredPost.length > 0){
            console.log(registredPost)
            res.json("saved")
        }else{
            res.json('not saved')
        }
    }catch(error){

        console.log(error)
        res.status(500).json('error')
    }

})
route.delete("/forgetPost/:userId/:postId",async(req,res)=>{
    try{
        const {userId,postId} = req.params;
        await query.forgetPost(userId,postId) 
        res.json("success")
    }catch(error){
        console.log(error)
        res.status(500).json('error')
    }
})
route.delete("/deletePost/:userId/:postId",async(req,res)=>{
    try{
        const {userId,postId} = req.params;
        await query.deletePost(userId,postId) 
        res.json("success")
    }catch(error){
        console.log(error)
        res.status(500).json('error')
    }
})
route.delete("/unsavePosts/:userId/:postId",async (req,res)=>{
    const {userId,postId} = req.params;
    try{
        await query.unsavePost(userId,postId)
        res.json("delete succefully")
    }catch{
        console.log(error)
        res.status(500).json("error")
    }
})

module.exports = route;

