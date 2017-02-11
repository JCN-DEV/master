'use strict';

angular.module('stepApp')
    .controller('InstGovernBodyTempController', function ($scope, InstGovernBodyTemp, InstGovernBodyTempSearch, ParseLinks) {
        $scope.instGovernBodyTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstGovernBodyTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instGovernBodyTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstGovernBodyTemp.get({id: id}, function(result) {
                $scope.instGovernBodyTemp = result;
                $('#deleteInstGovernBodyTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstGovernBodyTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstGovernBodyTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstGovernBodyTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instGovernBodyTemps = result;
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
            $scope.instGovernBodyTemp = {
                name: null,
                position: null,
                mobileNo: null,
                status: null,
                id: null
            };
        };
    });
