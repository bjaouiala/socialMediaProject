const express = require('express');
const route = express.Router();
const query = require('../db/queries');
const {upload} = require("../Middleware");
const fs = require('fs')

route.post("/:type/:id",upload.single("file"),async(req,res)=>{
    const post = req.body;
    const {id,type} = req.params;
    const filePath = req.file.path
    console.log(type)
    try{
       
        const postRes=await query.addPost(post,id,filePath)
        
    
        if(postRes.length > 0){
            console.log(postRes[0])
            if(type=="photoDeProfile"){
               await query.updatePostIsProfile(postRes[0])
            }else if(type == "photoDeCouverture"){
                await query.updatePostIsCover(postRes[0])
            }
        }
        res.json("post uploaded succefully")
    }catch (error){
        console.log(error);
        res.status(500).json(error)
    };

});

route.get("/posts/:id",async (req,res)=>{
    
    try{
        const id = req.params.id;
        const posts = await query.getAllPostsByUserId(id);
        console.log(posts)
        if(posts){
            for(const post of posts){
                const filePath = post.postFile;
                const userPicturePath =post.photoDeProfile;
                const fileData = fs.readFileSync(filePath,{encoding:"base64"});
                const userPictureData = fs.readFileSync(userPicturePath,{encoding:"base64"});
                post.photoDeProfile=userPictureData;
                post.postFile = fileData;
               
               

            }res.json(posts)
        }else{
            res.status(404).json("posts not found")
        }
    }catch(error){
        console.log(error);
        res.status(500).json(error);
    }
})
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

module.exports = route;

