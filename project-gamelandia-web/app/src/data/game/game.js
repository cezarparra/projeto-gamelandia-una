'use strict';



angular.module( 'mutrack' )
    .controller( 'GameCtrl', function ( $scope, ngNotify, RestSrv, SERVICE_PATH, $http, $route, $rootScope, $location  ) {

        
        $scope.game = {};
        $scope.games = [];

        $scope.showAddEditGame = false;
        $scope.showGames = false;
        $scope.showViewGame = false;

        $scope.uploadme = null;

        $scope.mostrarJogos = true;

        $scope.showBtnSave = true;
        $scope.showBtnEdit = false;

        ngNotify.config( {
            theme: 'pastel'
        } );

        $scope.show = function () {
            $scope.showAddEditGame = true;
            $scope.showViewGame = false;
            $scope.showGames = false;

        };

        $scope.showDataGame = function () {
            $scope.showAddEditGame = false;
            $scope.showViewGame = true;
            $scope.showGames = true;

        };


        
        $scope.hide = function () {
            $scope.showAddEditGame = false;
            $scope.showGames = false;
            $scope.game = {};
        };

        
        var gameUrl = SERVICE_PATH.PRIVATE_PATH + '/jogos';
        var uploadURL = SERVICE_PATH.PRIVATE_PATH + '/fileUpload';
        var getImages = SERVICE_PATH.PRIVATE_PATH + '/fileUpload/images';
        var uploadEditURL = SERVICE_PATH.PRIVATE_PATH + '/fileUpload/editGame';

        $scope.editGameUpload = function(){
            
            var fd = new FormData();
            

            if($scope.uploadme != null){
                fd.append('file', $scope.uploadme);
            }else{
                fd.append('file', null);
            }     
                      
            fd.append('nomeJogo', $scope.game.nomeJogo);
            
            fd.append('qtdeEstoque', $scope.game.qtdeEstoque);
            
            fd.append('statusJogo', $scope.game.statusJogo);
            
            fd.append('anoLancamento', $scope.game.anoLancamento);
            fd.append('preco', $scope.game.preco);
            fd.append('console', $scope.game.console);
            fd.append('id', $scope.game.id);
            

            $scope.showAddEditGame = false;
        
            $http.post(
                uploadEditURL,
                fd, {
                  transformRequest: angular.identity,
                  headers: {
                    'Content-Type': undefined
                  }
                }
              )
              .success(function(response) {
                $route.reload();
              })
              .error(function(response) {
              });
        }


        $scope.upload = function(){

                    
            var fd = new FormData();
            if($scope.uploadme != null){
                fd.append('file', $scope.uploadme);
            }else{
                fd.append('file', null);
            }  
            fd.append('nomeJogo', $scope.game.nomeJogo);
            fd.append('qtdeEstoque', $scope.game.qtdeEstoque);
            fd.append('statusJogo', $scope.game.statusJogo);
            fd.append('anoLancamento', $scope.game.anoLancamento);
            fd.append('preco', $scope.game.preco);
            fd.append('console', $scope.game.console);
            
            
            $http.post(
                uploadURL,
                fd, {
                  transformRequest: angular.identity,
                  headers: {
                    'Content-Type': undefined
                  }
                }
              )
              .success(function(response) {
                  var newGame =  $scope.game;
                  console.log(newGame);
                $scope.games.push( newGame );
                $route.reload();
              })
              .error(function(response) {
                console.log('error', response);
              });
        }

        $scope.editGame = function ( game ) {
            $scope.game = angular.copy( game );
            $scope.showBtnSave = false;
            $scope.showBtnEdit = true;
            
            $scope.show();
        };

       
        $scope.voltar = function(){
            $route.reload();
        }

         
        $scope.viewGame = function ( game ) { 
                var gameID = game.id; 
                var gameURLId = SERVICE_PATH.PRIVATE_PATH + '/jogos/' + gameID; 

                $scope.mostrarJogos = false;
                $scope.mostrarBotaoCadastro = true;
                
                RestSrv.find( gameURLId, function ( gameID ) { 
                    $scope.gamesFinded = gameID; 
                    $scope.showDataGame();  
                    
                } 
            ); 
        };
 
        $scope.deleteGame = function ( game ) {
            RestSrv.delete( gameUrl,  game, function () {
                $scope.games.splice( $scope.games.indexOf( game ), 1 );
                ngNotify.set( 'Game \'' + game.nomeJogo + '\' deletado.', 'success' );
            } );
        };

        var addCartUrl = SERVICE_PATH.PRIVATE_PATH + '/realizar-venda/addCartSale';
        $scope.addCart = function(game){
            RestSrv.add( addCartUrl, game, function ( newGame ) {
                ngNotify.set( 'Item adicionado ao carrinho', 'success' );
                $scope.items = newGame;
            })               
        ;}

      

        $scope.saveGame = function ( game ) {
            if ( game.id ) {
                RestSrv.edit( gameUrl, game, function () {
                    for ( var i = 0; i < $scope.games.length; i++ ) {
                        if ( $scope.games[ i ].id === game.id ) {
                            $scope.games[ i ] = game;
                        }
                    }

                    ngNotify.set( 'Game \'' + game.nomeJogo + '\' atualizado.', 'success' );
                } );
            } else {
                RestSrv.add( gameUrl, game, function ( newGame ) {
                    $scope.showAddEditGame  = false;
                    $scope.games.push( newGame );
                    ngNotify.set( 'Game \'' + game.nomeJogo + '\' cadastrado com sucesso', 'success' );
                } );
            }
        };

        var permissionUrl = SERVICE_PATH.PRIVATE_PATH + '/permission';

        RestSrv.find(permissionUrl, function(data) {
          $scope.permissions = data;
    
          RestSrv.find( gameUrl, function ( data ) {
            $scope.games = data;
        } );
        });

        $scope.options = ['Usado', 'Novo'];

        $scope.optionsConsole = ['Super Nintendo', 'PlayStation 4', 'PlayStation 3', 'Xbox 360', 'Xbox One'];

    } );
