'use strict';

angular.module('stepApp')
    .controller('AlmAttendanceInformationController',
    ['$scope', 'AlmAttendanceInformation', 'AlmAttendanceInformationSearch', 'ParseLinks',
    function ($scope, AlmAttendanceInformation, AlmAttendanceInformationSearch, ParseLinks) {
        $scope.almAttendanceInformations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmAttendanceInformation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almAttendanceInformations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmAttendanceInformation.get({id: id}, function(result) {
                $scope.almAttendanceInformation = result;
                $('#deleteAlmAttendanceInformationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmAttendanceInformation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmAttendanceInformationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmAttendanceInformationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almAttendanceInformations = result;
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
            $scope.almAttendanceInformation = {
                officeIn: null,
                officeOut: null,
                punchIn: null,
                punchInNote: null,
                punchOut: null,
                punchOutNote: null,
                processDate: null,
                isProcessed: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
