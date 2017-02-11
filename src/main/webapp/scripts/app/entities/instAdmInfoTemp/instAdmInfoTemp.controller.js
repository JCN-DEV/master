'use strict';

angular.module('stepApp')
    .controller('InstAdmInfoTempController', function ($scope, InstAdmInfoTemp, InstAdmInfoTempSearch, ParseLinks) {
        $scope.instAdmInfoTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstAdmInfoTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instAdmInfoTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstAdmInfoTemp.get({id: id}, function(result) {
                $scope.instAdmInfoTemp = result;
                $('#deleteInstAdmInfoTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstAdmInfoTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstAdmInfoTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstAdmInfoTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instAdmInfoTemps = result;
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
            $scope.instAdmInfoTemp = {
                adminCounselorName: null,
                counselorMobileNo: null,
                insHeadName: null,
                insHeadMobileNo: null,
                deoName: null,
                deoMobileNo: null,
                status: null,
                id: null
            };
        };
    });
