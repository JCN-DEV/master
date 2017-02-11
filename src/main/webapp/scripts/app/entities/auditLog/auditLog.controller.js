'use strict';

angular.module('stepApp')
    .controller('AuditLogController', function ($scope, AuditLog, AuditLogSearch, ParseLinks) {
        $scope.auditLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AuditLog.query({page: $scope.page, size: 50000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.auditLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AuditLog.get({id: id}, function(result) {
                $scope.auditLog = result;
                $('#deleteAuditLogConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AuditLog.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAuditLogConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AuditLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.auditLogs = result;
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
            $scope.auditLog = {
                eventTime: null,
                event: null,
                eventType: null,
                status: null,
                createBy: null,
                createDate: null,
                updateBy: null,
                updateDate: null,
                remarks: null,
                userId: null,
                userIpAddress: null,
                userMacAddress: null,
                deviceName: null,
                statusAction: null,
                types: null,
                userBrowser: null,
                id: null
            };
        };
    });
