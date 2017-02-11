'use strict';

angular.module('stepApp')
    .controller('AssetRequisitionController',
    ['$scope', 'AssetRequisition', 'AssetRequisitionSearch', 'ParseLinks',
    function ($scope, AssetRequisition, AssetRequisitionSearch, ParseLinks) {
        $scope.assetRequisitions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AssetRequisition.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.assetRequisitions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AssetRequisition.get({id: id}, function(result) {
                $scope.assetRequisition = result;
                $('#deleteAssetRequisitionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AssetRequisition.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAssetRequisitionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AssetRequisitionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.assetRequisitions = result;
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
            $scope.assetRequisition = {
                empId: null,
                empName: null,
                designation: null,
                department: null,
                requisitionId: null,
                requisitionDate: null,
                quantity: null,
                reasonOfReq: null,
                reqStatus: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                remarks: null,
                id: null
            };
        };
    }]);
