'use strict';

angular.module('stepApp')
    .controller('HrWingHeadSetupController', function ($scope, HrWingHeadSetup, HrWingHeadSetupSearch, ParseLinks) {
        $scope.hrWingHeadSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            HrWingHeadSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.hrWingHeadSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            HrWingHeadSetup.get({id: id}, function(result) {
                $scope.hrWingHeadSetup = result;
                $('#deleteHrWingHeadSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            HrWingHeadSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteHrWingHeadSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            HrWingHeadSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrWingHeadSetups = result;
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
            $scope.hrWingHeadSetup = {
                joinDate: null,
                endDate: null,
                activeHead: false,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
