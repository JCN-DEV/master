'use strict';

angular.module('stepApp')
    .controller('CareerInformationController',
    ['$scope', '$state', '$modal', 'CareerInformation', 'CareerInformationSearch', 'ParseLinks',
     function ($scope, $state, $modal, CareerInformation, CareerInformationSearch, ParseLinks) {

        $scope.careerInformations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CareerInformation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.careerInformations = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            CareerInformationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.careerInformations = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.careerInformation = {
                objectives: null,
                keyword: null,
                presentSalary: null,
                expectedSalary: null,
                lookForNature: null,
                availableFrom: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllCareerInformationsSelected = false;

        $scope.updateCareerInformationsSelection = function (careerInformationArray, selectionValue) {
            for (var i = 0; i < careerInformationArray.length; i++)
            {
            careerInformationArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.careerInformations.length; i++){
                var careerInformation = $scope.careerInformations[i];
                if(careerInformation.isSelected){
                    //CareerInformation.update(careerInformation);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.careerInformations.length; i++){
                var careerInformation = $scope.careerInformations[i];
                if(careerInformation.isSelected){
                    //CareerInformation.update(careerInformation);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.careerInformations.length; i++){
                var careerInformation = $scope.careerInformations[i];
                if(careerInformation.isSelected){
                    CareerInformation.delete(careerInformation);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.careerInformations.length; i++){
                var careerInformation = $scope.careerInformations[i];
                if(careerInformation.isSelected){
                    CareerInformation.update(careerInformation);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            CareerInformation.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.careerInformations = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
