'use strict';

angular.module('stepApp')
    .controller('UmracIdentitySetupController',
        ['$scope', 'UmracIdentitySetup', 'UmracIdentitySetupSearch', 'ParseLinks',
        function ($scope, UmracIdentitySetup, UmracIdentitySetupSearch, ParseLinks) {
        $scope.umracIdentitySetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            UmracIdentitySetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.umracIdentitySetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UmracIdentitySetup.get({id: id}, function(result) {
                $scope.umracIdentitySetup = result;
                $('#deleteUmracIdentitySetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UmracIdentitySetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUmracIdentitySetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            UmracIdentitySetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.umracIdentitySetups = result;
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
            $scope.umracIdentitySetup = {
                empId: null,
                userName: null,
                uPw: null,
                confm_Pw: null,
                status: null,
                createDate: null,
                createBy: null,
                updatedBy: null,
                updatedTime: null,
                id: null
            };
        };
    }]);
