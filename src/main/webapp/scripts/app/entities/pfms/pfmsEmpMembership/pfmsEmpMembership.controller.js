'use strict';

angular.module('stepApp')
    .controller('PfmsEmpMembershipController', function ($scope, PfmsEmpMembership, PfmsEmpMembershipSearch, ParseLinks) {
        $scope.pfmsEmpMemberships = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PfmsEmpMembership.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pfmsEmpMemberships = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PfmsEmpMembership.get({id: id}, function(result) {
                $scope.pfmsEmpMembership = result;
                $('#deletePfmsEmpMembershipConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PfmsEmpMembership.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePfmsEmpMembershipConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PfmsEmpMembershipSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pfmsEmpMemberships = result;
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
            $scope.pfmsEmpMembership = {
                initOwnContribute: null,
                initOwnContributeInt: null,
                curOwnContribute: null,
                curOwnContributeInt: null,
                curOwnContributeTot: null,
                percentOfDeduct: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
