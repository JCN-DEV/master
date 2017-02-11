'use strict';

angular.module('stepApp')
    .controller('MiscConfigSetupController', function ($scope, MiscConfigSetup, MiscConfigSetupSearch, ParseLinks) {
        $scope.miscConfigSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MiscConfigSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.miscConfigSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            MiscConfigSetup.get({id: id}, function(result) {
                $scope.miscConfigSetup = result;
                $('#deleteMiscConfigSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MiscConfigSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMiscConfigSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            MiscConfigSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.miscConfigSetups = result;
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
            $scope.miscConfigSetup = {
                propertyName: null,
                propertyTitle: null,
                propertyValue: null,
                propertyDataType: null,
                propertyValueMax: null,
                propertyDesc: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
