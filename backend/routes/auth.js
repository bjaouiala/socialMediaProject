let express = require('express');
let route = express.Router()
let authFunction = require('../function/authFunction')

route.post('/',authFunction.auth)

module.exports = route;