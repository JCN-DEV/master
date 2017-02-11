'use strict';

angular.module('stepApp')
    .controller('InstEmployeeController',
    ['$scope','$state','$modal','DataUtils','InstEmployee','InstEmployeeSearch','ParseLinks',
    function ($scope, $state, $modal, DataUtils, InstEmployee, InstEmployeeSearch, ParseLinks) {

        $scope.instEmployees = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmployee.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmployees = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmployeeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmployees = result;
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
            $scope.instEmployee = {
                name: null,
                email: null,
                contactNo: null,
                fatherName: null,
                motherName: null,
                dob: null,
                category: null,
                gender: null,
                maritalStatus: null,
                bloodGroup: null,
                tin: null,
                image: null,
                imageContentType: null,
                nationality: null,
                nid: null,
                nidImage: null,
                nidImageContentType: null,
                birthCertNo: null,
                birthCertImage: null,
                birthCertImageContentType: null,
                conPerName: null,
                conPerMobile: null,
                conPerAddress: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    }]);
