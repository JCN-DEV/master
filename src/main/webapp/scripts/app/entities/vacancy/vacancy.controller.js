'use strict';

angular.module('stepApp')
    .controller('VacancyController',
    ['$scope', '$state', '$modal', 'Vacancy', 'VacancySearch', 'ParseLinks',
    function ($scope, $state, $modal, Vacancy, VacancySearch, ParseLinks) {

        $scope.vacancys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Vacancy.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.vacancys = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            VacancySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.vacancys = result;
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
            $scope.vacancy = {
                name: null,
                promotionGBResolutionDate: null,
                totalServiceTenure: null,
                currentJobDuration: null,
                status: null,
                remarks: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllVacancysSelected = false;

        $scope.updateVacancysSelection = function (vacancyArray, selectionValue) {
            for (var i = 0; i < vacancyArray.length; i++)
            {
            vacancyArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.vacancys.length; i++){
                var vacancy = $scope.vacancys[i];
                if(vacancy.isSelected){
                    //Vacancy.update(vacancy);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.vacancys.length; i++){
                var vacancy = $scope.vacancys[i];
                if(vacancy.isSelected){
                    //Vacancy.update(vacancy);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.vacancys.length; i++){
                var vacancy = $scope.vacancys[i];
                if(vacancy.isSelected){
                    Vacancy.delete(vacancy);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.vacancys.length; i++){
                var vacancy = $scope.vacancys[i];
                if(vacancy.isSelected){
                    Vacancy.update(vacancy);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Vacancy.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.vacancys = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
