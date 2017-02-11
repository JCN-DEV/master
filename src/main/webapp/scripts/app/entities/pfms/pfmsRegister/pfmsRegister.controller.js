'use strict';

angular.module('stepApp')
    .controller('PfmsRegisterController', function ($scope, $rootScope, PfmsRegister, PfmsRegisterSearch, ParseLinks) {
        $scope.pfmsRegisters = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PfmsRegister.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pfmsRegisters = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PfmsRegister.get({id: id}, function(result) {
                $scope.pfmsRegister = result;
                $('#deletePfmsRegisterConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PfmsRegister.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePfmsRegisterConfirmation').modal('hide');
                    $scope.clear();
                    $rootScope.setErrorMessage('stepApp.pfmsRegister.deleted');
                });
        };

        $scope.search = function () {
            PfmsRegisterSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pfmsRegisters = result;
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
            $scope.pfmsRegister = {
                applicationType: null,
                isBillRegister: false,
                billNo: null,
                billIssueDate: null,
                billReceiverName: null,
                billPlace: null,
                billDate: null,
                noOfWithdrawal: null,
                checkNo: null,
                checkDate: null,
                checkTokenNo: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
