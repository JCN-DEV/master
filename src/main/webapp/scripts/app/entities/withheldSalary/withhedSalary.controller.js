'use strict';

//Here are two Controller
// 1.withedSalaryController, 2.EmployeeSalaryController

angular.module('stepApp')
    .controller('withedSalaryController', function ($scope, $state, $modal, Deo, InstEmployee, InstEmployeeOneByIndex, ParseLinks) {

        $scope.instEmployee = {};
        $scope.page = 0;
        /*$scope.loadAll = function() {
            Deo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.deos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
*/

        $scope.search = function () {
            InstEmployeeOneByIndex.get({indexNo: $scope.searchQuery}, function(result) {
                console.log(result);
                $scope.instEmployee = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.instEmployee = {};
                }
            });
        };
        var onSaveSuccess = function(result){
            $scope.isSaving = false;
            location.reload();
        };

        var onSaveError = function(result){
            console.log(result);
        };
        $scope.save = function () {
            $scope.isSaving = true;
            if($scope.instEmployee.id != null){
                InstEmployee.update($scope.instEmployee, onSaveSuccess, onSaveError);
            }
        };

    }).controller('EmployeeSalaryController', function ($scope, $state, $modal, Deo, InstEmployee, InstEmployeeOneByIndex, ParseLinks) {

        $scope.instEmployee = {};
        $scope.page = 0;
        /*$scope.loadAll = function() {
            Deo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.deos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
*/

        $scope.search = function () {
            InstEmployeeOneByIndex.get({indexNo: $scope.searchQuery}, function(result) {
                console.log(result);
                $scope.instEmployee = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.instEmployee = {};
                }
            });
        };
        var onSaveSuccess = function(result){
            $scope.isSaving = false;
            location.reload();
        };

        var onSaveError = function(result){
            console.log(result);
        };
        $scope.save = function () {
            $scope.isSaving = true;
            if($scope.instEmployee.id != null){
                InstEmployee.update($scope.instEmployee, onSaveSuccess, onSaveError);
            }
        };

    });
