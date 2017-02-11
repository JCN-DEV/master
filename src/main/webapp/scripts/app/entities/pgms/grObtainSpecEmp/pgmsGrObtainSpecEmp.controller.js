'use strict';

angular.module('stepApp')
    .controller('PgmsGrObtainSpecEmpController',
    ['$scope', 'PgmsGrObtainSpecEmp', 'PgmsGrObtainSpecEmpSearch', 'ParseLinks',
    function ($scope, PgmsGrObtainSpecEmp, PgmsGrObtainSpecEmpSearch, ParseLinks) {
        $scope.pgmsGrObtainSpecEmps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PgmsGrObtainSpecEmp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pgmsGrObtainSpecEmps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PgmsGrObtainSpecEmp.get({id: id}, function(result) {
                $scope.pgmsGrObtainSpecEmp = result;
                $('#deletePgmsGrObtainSpecEmpConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PgmsGrObtainSpecEmp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePgmsGrObtainSpecEmpConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PgmsGrObtainSpecEmpSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsGrObtainSpecEmps = result;
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
            $scope.pgmsGrObtainSpecEmp = {
                empName: null,
                empDesignation: null,
                empDepartment: null,
                empEndDate: null,
                empStatus: false,
                empWrkingYear: null,
                amountAsGr: null,
                obtainDate: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
