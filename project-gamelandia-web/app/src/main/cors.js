function allowCrossDomain(){
  this.permission = function(req, res, next){
    res.headerSrv('Access-Control-Allow-Origin', 'http://localhost:8081/#/');
    res.headerSrv('Access-Control-Allow-Headers', 'Content-Type');
    res.headerSrv('Access-Control-Allow-Methods', 'GET, PUT, POST, DELETE, OPTIONS');
    if ('DELETE' === req.method) {
    res.send(200);
      } else {
    next();
  }
  }
}

angular.module.exports = new allowCrossDomain();
