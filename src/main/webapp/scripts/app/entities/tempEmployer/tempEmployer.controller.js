'use strict';

angular.module('stepApp')
    .controller('TempEmployerController',
    ['$scope', '$state', '$modal', 'DataUtils', 'TempEmployer', 'TempEmployerSearch', 'ParseLinks',
    function ($scope, $state, $modal, DataUtils, TempEmployer, TempEmployerSearch, ParseLinks) {

        $scope.tempEmployers = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TempEmployer.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tempEmployers = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            TempEmployerSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.tempEmployers = result;
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
            $scope.tempEmployer = {
                name: null,
                alternativeCompanyName: null,
                contactPersonName: null,
                personDesignation: null,
                contactNumber: null,
                companyInformation: null,
                address: null,
                city: null,
                zipCode: null,
                companyWebsite: null,
                industryType: null,
                businessDescription: null,
                companyLogo: null,
                companyLogoContentType: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        // bulk operations start
        $scope.areAllTempEmployersSelected = false;

        $scope.updateTempEmployersSelection = function (TempEmployerArray, selectionValue) {
            for (var i = 0; i < TempEmployerArray.length; i++)
            {
                TempEmployerArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.tempEmployers.length; i++){
                var tempEmployer = $scope.tempEmployers[i];
                if(tempEmployer.isSelected){
                    //TempEmployer.update(TempEmployer);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.tempEmployers.length; i++){
                var tempEmployer = $scope.tempEmployers[i];
                if(tempEmployer.isSelected){
                    //TempEmployer.update(TempEmployer);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.tempEmployers.length; i++){
                var tempEmployer = $scope.tempEmployers[i];
                if(tempEmployer.isSelected){
                    TempEmployer.delete(tempEmployer);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.tempEmployers.length; i++){
                var tempEmployer = $scope.tempEmployers[i];
                if(tempEmployer.isSelected){
                    TempEmployer.update(tempEmployer);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            TempEmployer.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tempEmployers = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
