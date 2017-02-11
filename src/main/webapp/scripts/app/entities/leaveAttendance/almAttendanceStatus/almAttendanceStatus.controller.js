'use strict';

angular.module('stepApp')
    .controller('AlmAttendanceStatusController',
    ['$scope', 'AlmAttendanceStatus', 'AlmAttendanceStatusSearch', 'ParseLinks',
    function ($scope, AlmAttendanceStatus, AlmAttendanceStatusSearch, ParseLinks) {
        $scope.almAttendanceStatuss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmAttendanceStatus.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almAttendanceStatuss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmAttendanceStatus.get({id: id}, function(result) {
                $scope.almAttendanceStatus = result;
                $('#deleteAlmAttendanceStatusConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmAttendanceStatus.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmAttendanceStatusConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmAttendanceStatusSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almAttendanceStatuss = result;
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
            $scope.almAttendanceStatus = {
                attendanceStatusName: null,
                description: null,
                attendanceCode: null,
                ShortCode: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
