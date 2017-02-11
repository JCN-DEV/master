'use strict';

angular.module('stepApp')
    .controller('HrWingSetupController', function ($scope, $state, $modal, HrWingSetup, HrWingSetupSearch, ParseLinks) {
      
        $scope.hrWingSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            HrWingSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.hrWingSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrWingSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrWingSetups = result;
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
            $scope.hrWingSetup = {
                wingName: null,
                wingDesc: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
