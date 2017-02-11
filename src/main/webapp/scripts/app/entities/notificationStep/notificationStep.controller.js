'use strict';

angular.module('stepApp')
    .controller('NotificationStepController', function ($rootScope, $scope, NotificationStep, NotificationStepSearch, ParseLinks) {
        $scope.notificationSteps = [];
        $scope.page = 0;

        console.log('Notification');


        $scope.loadAll = function() {
            NotificationStep.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.notificationSteps = result;
                //$rootScope.notifications = result;
                //console.log($scope.notificationSteps);
            });
        };


        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            NotificationStep.get({id: id}, function(result) {
                $scope.notificationStep = result;
                $('#deleteNotificationStepConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            NotificationStep.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteNotificationStepConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            NotificationStepSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.notificationSteps = result;
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
            $scope.notificationStep = {
                userId: null,
                notifyDate: null,
                urls: null,
                status: null,
                createBy: null,
                createDate: null,
                updateBy: null,
                updateDate: null,
                remarks: null,
                notification: null,
                id: null
            };
        };
    });
