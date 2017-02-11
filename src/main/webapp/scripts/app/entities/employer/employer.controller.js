'use strict';

angular.module('stepApp')
    .controller('EmployerController',
    ['$scope', '$state', '$modal', 'DataUtils', 'Employer', 'EmployerSearch', 'ParseLinks',
    function ($scope, $state, $modal, DataUtils, Employer, EmployerSearch, ParseLinks) {

        $scope.employers = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Employer.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employers = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            EmployerSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.employers = result;
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
            $scope.employer = {
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
        $scope.areAllEmployersSelected = false;

        $scope.updateEmployersSelection = function (employerArray, selectionValue) {
            for (var i = 0; i < employerArray.length; i++)
            {
            employerArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.employers.length; i++){
                var employer = $scope.employers[i];
                if(employer.isSelected){
                    //Employer.update(employer);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.employers.length; i++){
                var employer = $scope.employers[i];
                if(employer.isSelected){
                    //Employer.update(employer);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.employers.length; i++){
                var employer = $scope.employers[i];
                if(employer.isSelected){
                    Employer.delete(employer);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.employers.length; i++){
                var employer = $scope.employers[i];
                if(employer.isSelected){
                    Employer.update(employer);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Employer.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employers = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
