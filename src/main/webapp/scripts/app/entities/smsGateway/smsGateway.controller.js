'use strict';

angular.module('stepApp')
    .controller('SmsGatewayController',
    ['$scope', 'SmsGateway', 'SmsGatewaySearch', 'ParseLinks',
    function ($scope, SmsGateway, SmsGatewaySearch, ParseLinks) {
        $scope.smsGateways = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            SmsGateway.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.smsGateways = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            SmsGateway.get({id: id}, function(result) {
                $scope.smsGateway = result;
                $('#deleteSmsGatewayConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            SmsGateway.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSmsGatewayConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            SmsGatewaySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.smsGateways = result;
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
            $scope.smsGateway = {
                userId: null,
                phoneNo: null,
                msg: null,
                id: null
            };
        };
    }]);
