/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = function(knex) {
  return knex.schema.createTable('users',function(table){
    table.increments('id').primary();
    table.string('Firstname');
    table.string('Lastname');
    table.string('password');
    table.string('email').unique().notNullable();
    table.string('emailRecuperation');
    table.string('phoneNumber');
    table.date('dateBirth');
    table.string('gender');
    table.string('role');
    table.string('CodeConfirmation');
    table.boolean('isVerified');
    table.string('photoDeCouverture');
    table.string('photoDeProfile');
    

  })
  .createTable('posts',function(table){
    table.increments('id').primary();
    table.integer("user_id").unsigned().notNullable();
    table.foreign('user_id').references('id').inTable('users').onDelete('CASCADE');
    table.string('description');
    table.date('dateCreate');
    table.string("postFile");
     table.boolean('isProfile');
    table.boolean('isCover')
   
  })
  .createTable('Friends',function(table){
    table.increments('id');
    table.integer('followerId').unsigned().notNullable();
    table.foreign('followerId').references('id').inTable('users').onDelete('CASCADE');
    table.integer('followedId').unsigned().notNullable();
    table.foreign('followedId').references('id').inTable('users').onDelete('CASCADE');
    table.enu('state',['PENDING','ACCEPTED','USER','BLOCKED'])

  })
  .createTable('registredPost',function(table){
    table.increments('id');
    table.integer('userId').unsigned().notNullable();
    table.foreign('userId').references('id').inTable('users').onDelete('cascade');
    table.integer('postId').unsigned().notNullable();
    table.foreign('postId').references('id').inTable('posts').onDelete('cascade')
    })
  .createTable('Like',function(table){
    table.increments('id');
    table.integer('userId').unsigned().notNullable();
    table.foreign('userId').references('id').inTable('users').onDelete('cascade');
    table.integer('postId').unsigned().notNullable();
    table.foreign('postId').references('id').inTable('posts').onDelete('cascade')
    table.boolean('state');
    })
};

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = function(knex) {
  return knex.schema.createTable('Like',function(table){
    table.increments('id');
    table.integer('userId').unsigned().notNullable();
    table.foreign('userId').references('id').inTable('users').onDelete('cascade');
    table.integer('postId').unsigned().notNullable();
    table.foreign('postId').references('id').inTable('posts').onDelete('cascade')
    table.boolean('state');
    })
  
  
  
  
};
