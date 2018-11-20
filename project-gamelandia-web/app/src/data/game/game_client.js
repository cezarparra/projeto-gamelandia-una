'use strict';
angular.module( 'mutrack' )
    .controller( 'GameClientCtrl', function ( $scope, ngNotify, RestSrv, SERVICE_PATH, $http, $routeParams, $route ) {
        $scope.game = {};
        $scope.games = [];

        $scope.gameSearchs = [];
        $scope.gameSearch = {};

        $scope.cart = {};
        $scope.carts = [];

        $scope.console = {};
        $scope.consoles = [];


        $scope.showAddEditGame = false;
        $scope.showGames = false;
        $scope.showViewGame = false;

        ngNotify.config( {
            theme: 'pastel'
        } );        

        $scope.hideList = function () {
            $scope.showListGames = true;
        };

        $scope.show = function () {
            $scope.showAddEditGame = true;
            $scope.showViewGame = false;
            $scope.showGames = false;
            $scope.searchGame = true;

        };


        $scope.showDataGame = function () {
            $scope.showAddEditGame = false;
            $scope.showViewGame = true;
            $scope.showGames = true;

        };


        
        $scope.hide = function () {
            $scope.showListGames = true;
            $scope.showAddEditGame = false;
            $scope.game = {};
        };

        
        var gameUrl = SERVICE_PATH.PRIVATE_PATH + '/jogos';
        var gameSearchURL = SERVICE_PATH.PRIVATE_PATH + '/jogos/searchGame';
        var addCartUrl = SERVICE_PATH.PRIVATE_PATH + '/cart/addCart';

        $scope.editGame = function ( game ) {
            $scope.game = angular.copy( game );
            $scope.show();
        };

        $scope.viewGame = function ( game ) {
            $scope.game = angular.copy( game );
            $scope.searchGame = false;
            $scope.showDataGame();
        };


        $scope.deleteGame = function ( game ) {
            RestSrv.delete( gameUrl,  game, function () {
                $scope.games.splice( $scope.games.indexOf( game ), 1 );
                ngNotify.set( 'Game \'' + game.nomeJogo + '\' deletado.', 'success' );
            } );
        };      


        $scope.addCart = function(game){
            RestSrv.add( addCartUrl, game, function ( newGame ) {
                $scope.items = newGame;
                $scope.carts.push($scope.items);
                
                //$route.reload();
                ngNotify.set( 'Item adicionado ao carrinho', 'success' );
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
                    RestSrv.find( gameUrl, function ( data ) {
                    $scope.games = data;
                        
                } );
                    ngNotify.set( 'Game \'' + game.nomeJogo + '\' atualizado.', 'success' );
                } );
            } else {
                RestSrv.add( gameUrl, game, function ( newGame ) {
                    $scope.games.push( newGame );
                    $scope.hide();
                    ngNotify.set( 'Game \'' + game.nomeJogo + '\' adicionado.', 'success' );
                } );
            }
        };

        RestSrv.find( gameUrl, function ( data ) {
            $scope.games = data;
            $scope.searchGame = true;
        } );


        $scope.options = ['Usado', 'Novo'];

    } );
