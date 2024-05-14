const express = require('express')
const likeRoute = express.Router()
const query = require("../db/queries")

likeRoute.post("/:userId/:postId",async (req,res)=>{
    const {userId,postId} = req.params;
   
    try{
        const likes = await query.getLike(userId,postId)
        console.log(likes.length)
        if(likes.length == 0){
            await query.like(userId,postId)
        res.json(likes.length)
        }else if(likes.length == 1){
            await query.dislike(userId,postId)
            res.json(likes.length)
        }else{
            throw new Error("error")
        }
    }catch(error){
        console.log(error)
        
        res.status(500).json('error')
    }
})

likeRoute.get("/:postId",async(req,res)=>{
    const postId = req.params.postId
    try{
        const count = await query.countLikes(postId)
        res.json(count[0])
    }catch(error){
        console.log(error)
        res.status(500).json("error")
    }
})

module.exports = likeRoute;