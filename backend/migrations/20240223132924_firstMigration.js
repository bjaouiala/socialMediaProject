/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = function(knex) {
  return knex.schema.createTable('users',function(table){
    table.increments();
    table.string('Firstname');
    table.string('Lastname');
    table.string('password');
    table.string('email');
    table.string('phoneNumber');
    table.date('dateBirth');
    table.string('gender')
    table.string('role')

  })
};

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = function(knex) {
  return knex.schema.dropTable('users');
  
};
