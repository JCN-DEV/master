'use strict';

angular.module('stepApp')
    .controller('InstAcaInfoTempController', function ($scope, InstAcaInfoTemp, InstAcaInfoTempSearch, ParseLinks) {
        $scope.instAcaInfoTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstAcaInfoTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instAcaInfoTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstAcaInfoTemp.get({id: id}, function(result) {
                $scope.instAcaInfoTemp = result;
                $('#deleteInstAcaInfoTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstAcaInfoTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstAcaInfoTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstAcaInfoTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instAcaInfoTemps = result;
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
            $scope.instAcaInfoTemp = {
                academicCounselorName: null,
                Mobile: null,
                curriculum: null,
                totalTradeTechNo: null,
                tradeTechDetails: null,
                id: null
            };
        };
    });
