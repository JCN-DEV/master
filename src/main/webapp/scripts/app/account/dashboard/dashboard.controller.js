'use strict';

angular.module('stepApp')
    .controller('DashboardController',
    ['$scope', 'Sessions', 'Job', 'JobSearch', 'Jobapplication', 'Principal',
    function ($scope, Sessions, Job, JobSearch, Jobapplication, Principal) {
        Principal.identity().then(function (account) {
            $scope.account = account;
        });

        $scope.success = null;
        $scope.error = null;
        $scope.jobs = Job.query();

        $scope.sessions = Sessions.getAll();
        $scope.jobapplications = Jobapplication.query();

        $scope.invalidate = function (series) {
            Sessions.delete({series: encodeURIComponent(series)},
                function () {
                    $scope.error = null;
                    $scope.success = 'OK';
                    $scope.sessions = Sessions.getAll();
                },
                function () {
                    $scope.success = null;
                    $scope.error = 'ERROR';
                });
        };
    }]);
