const knex = require('./knex');
const bcrypt = require('bcrypt')
module.exports = {
    addUser(user){

        return  bcrypt.hash(user.password,12)
        .then((hashpassword) => {
           return knex('users').insert({
                Firstname:user.Firstname,
                Lastname:user.Lastname,
                email:user.email,
                password:hashpassword,
                phoneNumber:user.phoneNumber,
                dateBirth:user.dateBirth,
                gender:user.gender,
                role:user.role,
                CodeConfirmation : user.CodeConfirmation,
                isVerified: user.isVerified,
                emailRecuperation:user.emailRecuperation,

                
    
            });
        }).catch((error) => {
            console.error('Error:', error);
            throw error;
        
        });
    },
    updateuser(user,id){
        if (user.password) {
            return bcrypt.hash(user.password, 12)
                .then((hashpassword) => {
                    return knex('users').update({
                        Firstname: user.Firstname,
                        Lastname: user.Lastname,
                        email: user.email,
                        password: hashpassword,
                    }).where({"users.id":id});
                }).catch((error) => {
                    console.error('Error:', error);
                    throw error;
                });
        } else {
            return knex('users').update({
                Firstname: user.Firstname,
                Lastname: user.Lastname,
                email: user.email,
                
            }).where({"users.id":id});
        }
    },
    findUserByid(id){
        return knex.select("*").from("users").where({"users.id": id});
    },
    addPost(post,user_id,path){
        return knex('posts').insert({
            user_id : user_id,
            description: post.description,
            dateCreate: post.dateCreate,
            postFile : path

        });
    },
    addpostWithoutFile(post,id){
        return knex('posts').insert({
            user_id : id,
            description: post.description,
            dateCreate: post.dateCreate,
            });
    },
    findUserByEmail(email){
        return knex('users').where({email:email});
    },  
    findUserByCodeConfirmation(code){
        return knex('users').where({CodeConfirmation: code})
    },
    

    getUserById(id) {
        const subqueryProfileId=knex.select('id ').from('posts').where({"posts.user_id":id,"posts.isProfile":true}).orderBy("posts.dateCreate","desc").limit(1);
        const subqueryCoverId=knex.select('id ').from('posts').where({"posts.user_id":id,"posts.isCover":true}).orderBy("posts.dateCreate","desc").limit(1);
        

        return knex
            .select('users.Firstname',
            'users.Lastname',
            "users.email",
            "users.dateBirth",
            "users.gender",
            "users.phoneNumber",
            "users.emailRecuperation","users.photoDeProfile","users.photoDeCouverture"
            ,subqueryProfileId.as("profileId"))
            .from('users')
            .where('users.id', id)
    },
    getFriendById(followerid,followedid) {

        const subqueryProfileId=knex.select('id ').from('posts').where({"posts.user_id":followerid,"posts.isProfile":true}).orderBy("posts.dateCreate","desc").limit(1);
        const subqueryCoverId=knex.select('id ').from('posts').where({"posts.user_id":followerid,"posts.isCover":true}).orderBy("posts.dateCreate","desc").limit(1);
        const subqueryFriendRelation=knex.select('Friends.state').from('Friends').where({'followerId': followedid,'followedId':followerid}).orWhere({'followerId': followerid,'followedId':followedid}).limit(1)
        const subqueryFollowerId=knex.select('Friends.followerId').from('Friends').where({'followerId': followerid,'followedId': followedid}).orWhere({'followerId': followedid,'followedId':followerid}).limit(1)
        const subqueryFollowedId=knex.select('Friends.followedId').from('Friends').where({'followerId': followerid,'followedId': followedid}).orWhere({'followerId': followedid,'followedId':followerid}).limit(1)

        return knex
            .select('users.Firstname',
            'users.Lastname',
            "users.email",
            "users.dateBirth",
            "users.gender",
            "users.phoneNumber",
            "users.emailRecuperation", 
            "users.photoDeProfile","users.photoDeCouverture",subqueryFriendRelation.as("state"),subqueryFollowerId.as('followerId'),subqueryCoverId.as("coverId"),subqueryFollowedId.as('followedId')
            ,subqueryProfileId.as("profileId"))
            .from('users')
            .where('users.id', followerid)
    },
    login(email,password){
        return knex('users').where({email:email,password:password})
    },
    updatePassword(code,password){
        return bcrypt.hash(password,12)
        .then((hashed)=>{
            return knex('users').where({CodeConfirmation:code}).update({password:hashed })

        }).catch((error)=>{
            console.log(error)
        })
        
    },
    updateUserState(code){
        return knex('users').where({CodeConfirmation: code}).update({isVerified: true})
    },
    updateUserparEmail(email,code){
        return knex('users').where({email: email}).update({CodeConfirmation:code})
    },
    getAllPostsByUserId(id){
    
        return knex.select("posts.id as post_id","users.id ","posts.description","posts.dateCreate",
        "users.Firstname","users.Lastname","posts.postFile","users.photoDeProfile")
        .from("posts").join('users','posts.user_id','users.id').where({user_id:id}).orderBy('posts.dateCreate','desc')
    },
    getPostsById(id){
        return knex.select("*").from('posts').where({id:id})

    },
    updatePostIsProfile(id){
        return knex('posts').where({id:id}).update({isProfile:true})
    },
    updatePostIsCover(id){
        return knex('posts').where({id:id}).update({isCover:true})
    },
    searchFriend(friend,userId){
        return knex.select('users.Firstname', 'users.Lastname', 'users.photoDeProfile', 'users.id')
        .from('users')
        .whereLike(knex.raw(`CONCAT(Firstname,' ',Lastname)`), `%${friend}%`)
        .andWhere('users.id', 'not in', function() {
            this.select('followerId')
                .from('Friends')
                .where('state', 'blocked')
                .andWhere('followedId', userId)
                .union(function(){
                this.select('followedId')
                .from('Friends')
                .where('state', 'blocked')
                .andWhere('followerId', userId)
                })
        
                        
        })
    
 
    },
    saveProfilePicture(imagePath,id){
        return knex('users').where({id:id}).update({photoDeProfile:imagePath});
    },
    savecoverPicture(imagePath,id){
        return knex('users').where({id:id}).update({photoDeCouverture:imagePath});
    },
    saveFriendRelation(followerId,followedId){
        return knex('Friends').insert({
            
            followerId:followerId,
            followedId:followedId,
            state:'PENDING'
        })
    },
    deleteFriendRelation(followerId,followedId){
        return knex('Friends').delete().where({followerId:followerId,
            followedId:followedId,}).orWhere({followerId:followedId,followedId:followerId})
    },
    acceptFriendRequest(followerId,followedId){
        return knex('Friends').where({followerId:followedId,followedId:followerId}).orWhere({followedId:followedId,followerId:followerId}).update({state:'ACCEPTED'})
    },
    BlockFriendRequest(followerId,followedId){
        return knex('Friends').update({state:'blocked'}).where({followerId:followerId,
            followedId:followedId,}).orWhere({followerId:followedId,followedId:followerId})
    },
    countLikes(postId){
        return knex.count('* as likeCount').from('Like').where({'Like.postId':postId})
    },
    getWelcomePage(userId) {
        return knex.select(
                "users.Firstname",
                "users.Lastname",
                "Friends.followerId",
                "posts.description",
                "posts.dateCreate",
                "posts.postFile",
                "users.photoDeProfile",            
                "users.id as userId",
                "posts.id as post_id",
                "Like.postId as likedPostId",
                "Like.userId as likedUserId",
                "Like.state",
            )
            .from("users")
            .join("Friends", function() {
                this.on("users.id", "=", "Friends.followedId")
                    .orOn("users.id", "=", "Friends.followerId");
            })
            .join("posts", "users.id", "=", "posts.user_id")
            .where(function() {
                this.where("Friends.followerId", userId)
                    .orWhere("Friends.followedId", userId)
                
            })
            .leftJoin("Like", function() {
                this.on("posts.id", "=", "Like.postId")
                     .andOn("Like.userId", "=", knex.raw("?", [userId]));
                   
            })
            
            .andWhere('users.id', 'not in', function() {
                this.select('followerId')
                    .from('Friends')
                    .where('state', 'blocked')
                    .andWhere('followedId', userId)
                    .union(function() {
                        this.select('followedId')
                            .from('Friends')
                            .where('state', 'blocked')
                            .andWhere('followerId', userId)
                    });
            })
            .andWhere("Friends.state", "ACCEPTED")
            .groupBy("posts.id")
            .orderBy("posts.dateCreate", "desc");
    },

    getFrienRequest(userId){
        return knex.select("users.photoDeProfile","users.Firstname","users.Lastname","Friends.state","Friends.followerId","Friends.followedId").from("Friends")
        .join("users","Friends.followedId","users.id")
        .where({"Friends.followerId":userId})
        .andWhere("Friends.state","=","PENDING")
    },
    getAcceptedFriends(userId) {
        const query1 = knex.select("users.photoDeProfile","users.Firstname","users.Lastname","Friends.state","Friends.followerId","Friends.followedId")
            .from("Friends")
            .join("users", "Friends.followedId", "users.id")
            .where({"Friends.followerId": userId})
            .andWhere("Friends.state", "=", "ACCEPTED");
    
        const query2 = knex.select("users.photoDeProfile","users.Firstname","users.Lastname","Friends.state","Friends.followerId","Friends.followedId")
            .from("Friends")
            .join("users", "Friends.followerId", "users.id")
            .where({"Friends.followedId": userId})
            .andWhere("Friends.state", "=", "ACCEPTED");
    
        return Promise.all([query1, query2]).then(results => {
            return results[0].concat(results[1]);
        });
    },
    unblockFriend(followerId,followedId){
        return knex('Friends').where({"followedId":followedId,"followerId":followerId}).del()

    },
    getbloCkedFriends(id){
        const query1= knex.select("users.Firstname","users.Lastname","users.photoDeProfile",'Friends.followerId',"Friends.followedId").from("users").join("Friends","users.id","Friends.followedId")
        .where({"Friends.followerId":id}).andWhere("Friends.state","BLOCKED");
        const query2 = knex.select("users.Firstname","users.Lastname","users.photoDeProfile",'Friends.followerId',"Friends.followedId").from("users").join("Friends","users.id","Friends.followerId")
        .where({"Friends.followedId":id}).andWhere("Friends.state","BLOCKED");

        return Promise.all([query1,query2]).then(results => {
            return results[0].concat(results[1]);
        })
    },
    registerPost(userId,postId){
        return knex('registredPost').insert({userId:userId,postId:postId})
    },
    getRegistredPost(userId,postId){
        return knex.select("registredPost.postId","registredPost.userId").from("registredPost").where({"registredPost.postId":postId}).andWhere({"registredPost.userId":userId})
    },
    getAllRegistredPost(userId){
        return knex.select("posts.postFile","registredPost.postId as post_id","registredPost.userId","users.Firstname","users.Lastname").from("registredPost")
        .join("posts","registredPost.postId","posts.id")
        .join("users","posts.user_id","users.id")
        .where({"registredPost.userId":userId})
    },
    forgetPost(userId,postId){
        return knex("registredPost").where({"registredPost.postId":postId}).andWhere({"registredPost.userId":userId}).del();
    },
    deletePost(userId,postId){
        return knex("posts").where({"posts.id":postId}).andWhere({"posts.user_id":userId}).del();
    },
    unsavePost(userId,postId){
        return knex("registredPost").where({"userId":userId,"postId":postId}).delete()
    },
    like(userId,postId){
        return knex('Like').insert({"userId":userId,"postId":postId,"state":true})
    },
    getLike(userId,postId){
        return knex.select("userId","postId").from("Like").where({"userId":userId,"postId":postId})
    },
    dislike(userId,postId){
        return knex("Like").where({"userId":userId,"postId":postId}).del()
    },
    



}