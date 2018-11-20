'use strict';

angular.module('mutrack')
  .controller('HomeCtrl', function(RestSrv, $scope, $route) {
    var slideIndex = 1;

$scope.showSlides = function(slideIndex){
          
};

$scope.plusSlides = function(n){
	$scope.showSlides(slideIndex += n);          
};

$scope.currentSlide = function(n){
	$scope.showSlides(slideIndex = n);          
};
$scope.showSlides = function(n){
	var i;
  var slides = document.getElementsByClassName("mySlides");
  var dots = document.getElementsByClassName("dot");
  if (n > slides.length) {slideIndex = 1}    
  if (n < 1) {slideIndex = slides.length}
  for (i = 0; i < slides.length; i++) {
      slides[i].style.display = "none";  
  }
  for (i = 0; i < dots.length; i++) {
      dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[slideIndex-1].style.display = "block";  
  dots[slideIndex-1].className += " active";
};
  

    $scope.image='src/home/DSC01697.jpg';

    RestSrv.find( 'http://localhost:8080/api/public/login', function ( data ) {
      $scope.login = data;
    } );    
  });

