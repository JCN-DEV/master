'use strict';

angular.module('stepApp')
    .controller('InsAcademicInfoTempController', function ($scope, InsAcademicInfoTemp, InsAcademicInfoTempSearch, ParseLinks) {
        $scope.insAcademicInfoTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InsAcademicInfoTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.insAcademicInfoTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InsAcademicInfoTemp.get({id: id}, function(result) {
                $scope.insAcademicInfoTemp = result;
                $('#deleteInsAcademicInfoTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InsAcademicInfoTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInsAcademicInfoTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InsAcademicInfoTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.insAcademicInfoTemps = result;
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
            $scope.insAcademicInfoTemp = {
                counselorName: null,
                curriculum: null,
                totalTechTradeNo: null,
                tradeTechDetails: null,
                status: null,
                id: null
            };
        };
    });
