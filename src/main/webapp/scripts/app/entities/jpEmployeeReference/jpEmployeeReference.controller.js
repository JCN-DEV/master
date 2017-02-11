'use strict';

angular.module('stepApp')
    .controller('JpEmployeeReferenceController',
     ['$scope', 'JpEmployeeReference', 'JpEmployeeReferenceSearch', 'ParseLinks',
     function ($scope, JpEmployeeReference, JpEmployeeReferenceSearch, ParseLinks) {
        $scope.jpEmployeeReferences = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            JpEmployeeReference.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jpEmployeeReferences = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            JpEmployeeReference.get({id: id}, function(result) {
                $scope.jpEmployeeReference = result;
                $('#deleteJpEmployeeReferenceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JpEmployeeReference.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJpEmployeeReferenceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            JpEmployeeReferenceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jpEmployeeReferences = result;
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
            $scope.jpEmployeeReference = {
                name: null,
                email: null,
                organisation: null,
                designation: null,
                relation: null,
                phone: null,
                address: null,
                remarks: null,
                id: null
            };
        };
    }]);
