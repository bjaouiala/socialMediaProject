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
    addPost(post,user_id,path){
        return knex('posts').insert({
            user_id : user_id,
            description: post.description,
            dateCreate: post.dateCreate,
            postFile : path

        });

    },
    findUserByEmail(email){
        return knex('users').where({email:email});
    },  
    findUserByCodeConfirmation(code){
        return knex('users').where({CodeConfirmation: code})
    },
    

    getUserById(id) {
        const subqueryProfile= knex.select('postFile').from('posts').where({"posts.user_id" : id,"posts.isProfile" : true } ).orderBy('posts.dateCreate','desc').limit(1);
        const subqueryCover=knex.select('postFile').from('posts').where({"posts.user_id":id,"posts.isCover":true}).orderBy("posts.dateCreate","desc").limit(1)

        return knex
            .select('users.Firstname',
            'users.Lastname',
            "users.email",
            "users.dateBirth",
            "users.gender",
            "users.phoneNumber",
            "users.emailRecuperation", subqueryProfile.as("photoDeProfile"), subqueryCover.as("photoDeCouverture"))
            .from('users')
            .where('users.id', id)
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
    setUserProfilePicture(id,path){
        return knex('users').where({id: id}).update({photoDeProfile: path})
    },
    setUsercouverturePicture(id,path){
        return knex('users').where({id: id}).update({photoDeCouverture: path})
    },
    updateUserparEmail(email,code){
        return knex('users').where({email: email}).update({CodeConfirmation:code})
    },
    getAllPostsByUserId(id){
        const subqueryProfile= knex.select('postFile').from('posts').where({"posts.user_id" : id,"posts.isProfile" : true } ).orderBy('posts.dateCreate','desc').limit(1);
        return knex.select("posts.id as post_id","users.id","posts.description","posts.dateCreate","users.Firstname","users.Lastname","posts.postFile",subqueryProfile.as("photoDeProfile")).from("posts").join('users','posts.user_id','users.id').where({user_id:id})
    },
    getPostsById(id){
        return knex.select("*").from('posts').where({id:id})

    },
    updatePostIsProfile(id){
        return knex('posts').where({id:id}).update({isProfile:true})
    },
    updatePostIsCover(id){
        return knex('posts').where({id:id}).update({isCover:true})
    }
}