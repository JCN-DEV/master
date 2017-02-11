'use strict';

angular.module('stepApp').controller('JobListController',
['$scope', '$stateParams', 'DataUtils', 'Principal', 'Employer', 'JobInfoEmployer', 'Job', 'JobEmployer', 'User', 'Country', 'JobSearch',
        function ($scope, $stateParams, DataUtils, Principal, Employer, JobInfoEmployer, Job, JobEmployer, User, Country, JobSearch) {

        $scope.isNewProfile = false;

            Employer.get({id: 'my'}, function (result) {
                $scope.employer = result;
                console.log($scope.employer);
                JobEmployer.query({id: $scope.employer.id}, function (result) {
                    $scope.jobs = result;
                 });
                JobInfoEmployer.query({id: $scope.employer.id}, function (result) {
                    console.log(result);
                    $scope.jobInfo = result;
                });
            });


            Employer.get({id: 'my'}, function (result) {
                    $scope.isNewProfile = false;
                    $scope.employer = result;
                    $scope.tempEmployer = result;
                }, function (response) {
                    if (response.status == 404) {
                        $scope.isNewProfile = true;
                     }
            });
        }]);
