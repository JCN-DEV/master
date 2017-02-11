'use strict';

angular.module('stepApp')
    .controller('QrcodeGenLogController',
    ['$scope','$state','$modal','QrcodeGenLog','QrcodeGenLogSearch','ParseLinks',
    function ($scope, $state, $modal, QrcodeGenLog, QrcodeGenLogSearch, ParseLinks) {

        $scope.qrcodeGenLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            QrcodeGenLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.qrcodeGenLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            QrcodeGenLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.qrcodeGenLogs = result;
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
            $scope.qrcodeGenLog = {
                refId: null,
                urlLink: null,
                qrCodeType: null,
                dataDesc: null,
                genCode: null,
                createBy: null,
                updateBy: null,
                appName: null,
                id: null
            };
        };
    }]);
