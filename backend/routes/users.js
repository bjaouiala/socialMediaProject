var express = require('express');
var router = express.Router();
const query = require('../db/queries')
const jwt = require('jsonwebtoken');
const middleware = require('../function/userFunction')

router.post('/',middleware.saveUser )


router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

module.exports = router;
