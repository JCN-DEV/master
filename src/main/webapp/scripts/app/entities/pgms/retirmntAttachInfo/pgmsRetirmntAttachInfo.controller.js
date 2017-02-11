'use strict';

angular.module('stepApp')
    .controller('PgmsRetirmntAttachInfoController',
    ['$scope','$rootScope', 'PgmsRetirmntAttachInfo', 'PgmsRetirmntAttachInfoSearch', 'ParseLinks',
    function ($scope, $rootScope, PgmsRetirmntAttachInfo, PgmsRetirmntAttachInfoSearch, ParseLinks) {
        $scope.pgmsRetirmntAttachInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PgmsRetirmntAttachInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pgmsRetirmntAttachInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PgmsRetirmntAttachInfo.get({id: id}, function(result) {
                $scope.pgmsRetirmntAttachInfo = result;
                $('#deletePgmsRetirmntAttachInfoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PgmsRetirmntAttachInfo.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePgmsRetirmntAttachInfoConfirmation').modal('hide');
                    $rootScope.setErrorMessage('stepApp.pgmsRetirmntAttachInfo.deleted');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PgmsRetirmntAttachInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsRetirmntAttachInfos = result;
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
            $scope.pgmsRetirmntAttachInfo = {
                attachName: null,
                priority: null,
                attachType: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateBy: null,
                updateDate: null,
                id: null
            };
        };
    }]);
