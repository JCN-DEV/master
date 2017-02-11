'use strict';

angular.module('stepApp')
    .controller('AuditLogHistoryController', function ($scope, AuditLogHistory, AuditLogHistorySearch, ParseLinks) {
        $scope.auditLogHistorys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AuditLogHistory.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.auditLogHistorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AuditLogHistory.get({id: id}, function(result) {
                $scope.auditLogHistory = result;
                $('#deleteAuditLogHistoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AuditLogHistory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAuditLogHistoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AuditLogHistorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.auditLogHistorys = result;
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
            $scope.auditLogHistory = {
                colName: null,
                valueBefore: null,
                valueAfter: null,
                status: null,
                createBy: null,
                createDate: null,
                updateBy: null,
                updateDate: null,
                remarks: null,
                userId: null,
                entityName: null,
                id: null
            };
        };
    });
