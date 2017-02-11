'use strict';

angular.module('stepApp')
    .controller('AssetAccuisitionSetupController',
    ['$scope', '$rootScope', 'AssetAccuisitionSetup', 'AssetAccuisitionSetupSearch', 'ParseLinks',
        function ($scope, $rootScope, AssetAccuisitionSetup, AssetAccuisitionSetupSearch, ParseLinks) {
        $scope.assetAccuisitionSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AssetAccuisitionSetup.query({page: $scope.page, size: 5000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.assetAccuisitionSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AssetAccuisitionSetup.get({id: id}, function(result) {
                $scope.assetAccuisitionSetup = result;
                $('#deleteAssetAccuisitionSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AssetAccuisitionSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $rootScope.setErrorMessage('stepApp.assetAccuisitionSetup.deleted');
                    $('#deleteAssetAccuisitionSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AssetAccuisitionSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.assetAccuisitionSetups = result;
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
            $scope.assetAccuisitionSetup = {
                trackID: null,
                code: null,
                name: null,
                Description: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                remarks: null,
                status: null,
                id: null
            };
        };
    }]);
