'use strict';

angular.module('stepApp')
    .controller('DlEmpRegController',
    ['$scope', 'DlEmpReg', 'DlEmpRegSearch', 'ParseLinks',
    function ($scope, DlEmpReg, DlEmpRegSearch, ParseLinks) {
        $scope.dlEmpRegs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DlEmpReg.query({page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlEmpRegs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DlEmpReg.get({id: id}, function(result) {
                $scope.dlEmpReg = result;
                $('#deleteDlEmpRegConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DlEmpReg.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDlEmpRegConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            DlEmpRegSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlEmpRegs = result;
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
            $scope.dlEmpReg = {
                userName: null,
                userPw: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                status: null,
                id: null
            };
        };
    }]);
