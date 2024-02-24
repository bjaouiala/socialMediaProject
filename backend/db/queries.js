const knex = require('./knex');
module.exports = {
    addUser(user){
     
        return  knex('users').insert(user);
    },
    findUserByEmail(email){
        return knex('users').where({email});
    },
    getUserById(id){
        return knex('users').where({id});
    },
    login(email,password){
        return knex('users').where({email:email,password:password})

    }
}