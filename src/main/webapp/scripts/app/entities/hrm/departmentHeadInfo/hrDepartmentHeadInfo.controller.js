'use strict';

angular.module('stepApp')
    .controller('HrDepartmentHeadInfoController', function ($scope, HrDepartmentHeadInfo, HrDepartmentHeadInfoSearch, ParseLinks) {
        $scope.hrDepartmentHeadInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            HrDepartmentHeadInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.hrDepartmentHeadInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            HrDepartmentHeadInfo.get({id: id}, function(result) {
                $scope.hrDepartmentHeadInfo = result;
                $('#deleteHrDepartmentHeadInfoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            HrDepartmentHeadInfo.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteHrDepartmentHeadInfoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            HrDepartmentHeadInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrDepartmentHeadInfos = result;
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
            $scope.hrDepartmentHeadInfo = {
                joinDate: null,
                endDate: null,
                activeHead: false,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
