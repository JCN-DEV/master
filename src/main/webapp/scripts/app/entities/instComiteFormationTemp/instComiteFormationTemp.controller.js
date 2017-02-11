'use strict';

angular.module('stepApp')
    .controller('InstComiteFormationTempController', function ($scope, InstComiteFormationTemp, InstComiteFormationTempSearch, ParseLinks) {
        $scope.instComiteFormationTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstComiteFormationTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instComiteFormationTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstComiteFormationTemp.get({id: id}, function(result) {
                $scope.instComiteFormationTemp = result;
                $('#deleteInstComiteFormationTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstComiteFormationTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstComiteFormationTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstComiteFormationTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instComiteFormationTemps = result;
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
            $scope.instComiteFormationTemp = {
                comiteName: null,
                comiteType: null,
                address: null,
                timeFrom: null,
                timeTo: null,
                formationDate: null,
                id: null
            };
        };
    });
