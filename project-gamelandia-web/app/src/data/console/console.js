'use strict';



angular.module( 'mutrack' )
    .controller( 'ConsoleCtrl', function ( $scope, ngNotify, RestSrv, SERVICE_PATH, $http, $route, $rootScope, $location  ) {

        
        $scope.console = {};
        $scope.consoles = [];

        $scope.showAddEditConsole = false;
        $scope.showConsoles = false;
        $scope.showViewConsoles = false;

        $scope.uploadme = null;

        $scope.mostrarConsoles = true;

        $scope.showBtnSave = true;
        $scope.showBtnEdit = false;

        ngNotify.config( {
            theme: 'pastel'
        } );

        $scope.show = function () {
            $scope.showAddEditConsole = true;
            $scope.showViewConsole = false;
            $scope.showConsoles = false;

        };

        $scope.showDataConsole = function () {
            $scope.showAddEditConsole = false;
            $scope.showViewConsoles = true;
            $scope.showConsoles = true;
        };


        
        $scope.hide = function () {
            $scope.showAddEditConsole = false;
            $scope.showConsoles = false;
            $scope.console = {};
        };

        
        var consoleURL = SERVICE_PATH.PRIVATE_PATH + '/console';
        var uploadEditURL = SERVICE_PATH.PRIVATE_PATH + '/fileUpload/editConsole';
        var uploadURL = SERVICE_PATH.PRIVATE_PATH + '/fileUpload/cadastrarConsole';
        

        $scope.editConsoleUpload = function(){
            
            var fd = new FormData();
            

            if($scope.uploadme != null){
                fd.append('file', $scope.uploadme);
            }else{
                fd.append('file', null);
            }     
                      
            fd.append('nomeConsole', $scope.console.nomeConsole);
            fd.append('qtdeEstoque', $scope.console.qtdeEstoque);
            fd.append('statusConsole', $scope.console.statusConsole);
            fd.append('preco', $scope.console.precoConsole);
            fd.append('id', $scope.console.id);
            

            $scope.showAddEditConsole = false;
        
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
            //var imgBlob = dataURItoBlob($scope.uploadme);

            var encodedString = btoa( $scope.uploadme);
            

            fd.append('file',  $scope.uploadme);                     
            fd.append('nomeConsole', $scope.console.nomeConsole);
            fd.append('qtdeEstoque', $scope.console.qtdeEstoque);
            fd.append('statusConsole', $scope.console.statusConsole);
            fd.append('preco', $scope.console.precoConsole);
            fd.append('id', $scope.console.id);
            
            
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
                var novoConsole = $scope.console;
                $scope.consoles.push( novoConsole );
                $route.reload();
              })
              .error(function(response) {
                console.log('error', response);
              });
        }

        function dataURItoBlob(dataURI) {
            var binary = atob(dataURI.split(',')[1]);
            var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
            var array = [];
            for (var i = 0; i < binary.length; i++) {
              array.push(binary.charCodeAt(i));
            }
            return new Blob([new Uint8Array(array)], {
              type: mimeString
            });
          }

        $scope.editConsole = function ( console ) {
            $scope.console = angular.copy( console );
            $scope.showBtnSave = false;
            $scope.showBtnEdit = true;
            $scope.show();
        };

        $scope.voltar = function(){
            $route.reload();
        }
        
        $scope.viewConsole = function ( console ) { 
                var consoleID = console.id; 
                var gameURLId = SERVICE_PATH.PRIVATE_PATH + '/console/' + consoleID; 

                $scope.mostrarConsole = false;
                $scope.mostrarBotaoCadastro = true;
                
                RestSrv.find( gameURLId, function ( consoleID ) { 
                    $scope.consoleFinded = consoleID; 
                    $scope.showDataConsole();    
                } 
            ); 
        };
 
        $scope.deleteConsole = function ( console ) {
            RestSrv.delete( consoleURL,  console, function () {
                $scope.consoles.splice( $scope.consoles.indexOf( console ), 1 );
                ngNotify.set( 'Console \'' + console.nomeConsole + '\' deletado.', 'success' );
            } );
        };

        var adicionarItemCarrinhoURL = SERVICE_PATH.PRIVATE_PATH + '/realizar-venda/adicionarConsoleCarrinho';
        $scope.addCart = function(console){
            RestSrv.add( adicionarItemCarrinhoURL, console, function ( novoItemConsole ) {
                ngNotify.set( 'Item adicionado ao carrinho', 'success' );
                $scope.items = novoItemConsole;
            })               
        ;}

        $scope.saveConsole = function ( console ) {
            if ( console.id ) {
                RestSrv.edit( consoleURL, console, function () {
                    for ( var i = 0; i < $scope.consoles.length; i++ ) {
                        if ( $scope.consoles[ i ].id === consoles.id ) {
                            $scope.consoles[ i ] = console;
                        }
                    }

                    ngNotify.set( 'Console \'' + console.nomeConsole + '\' atualizado.', 'success' );
                } );
            } else {
                RestSrv.add( consoleURL, console, function ( novoConsole ) {
                    $scope.showAddEditConsole  = false;
                    $scope.consoles.push( novoConsole );
                    ngNotify.set( 'Console \'' + console.nomeConsole + '\' cadastrado com sucesso', 'success' );
                } );
            }
        };

        var permissionUrl = SERVICE_PATH.PRIVATE_PATH + '/permission';

        RestSrv.find(permissionUrl, function(data) {
          $scope.permissions = data;
    
          RestSrv.find( consoleURL, function ( data ) {
            $scope.consoles = data;
        } );
        });

        $scope.options = ['Usado', 'Novo'];


    } );
