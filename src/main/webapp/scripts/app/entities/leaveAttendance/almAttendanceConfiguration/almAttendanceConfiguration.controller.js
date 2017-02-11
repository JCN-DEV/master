'use strict';

angular.module('stepApp')
    .controller('AlmAttendanceConfigurationController',

    ['$scope', 'AlmAttendanceConfiguration', 'AlmAttendanceConfigurationSearch', 'ParseLinks',
    function ($scope, AlmAttendanceConfiguration, AlmAttendanceConfigurationSearch, ParseLinks) {
        $scope.almAttendanceConfigurations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmAttendanceConfiguration.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almAttendanceConfigurations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmAttendanceConfiguration.get({id: id}, function(result) {
                $scope.almAttendanceConfiguration = result;
                $('#deleteAlmAttendanceConfigurationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmAttendanceConfiguration.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmAttendanceConfigurationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmAttendanceConfigurationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almAttendanceConfigurations = result;
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
            $scope.almAttendanceConfiguration = {
                employeeType: null,
                effectedDateFrom: null,
                effectedDateTo: null,
                isUntilFurtherNotice: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
