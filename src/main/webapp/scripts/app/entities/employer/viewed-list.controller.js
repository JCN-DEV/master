'use strict';

angular.module('stepApp').controller('ViewedListController',
['$scope','$state', '$stateParams', 'Jobapplication','ViewedJobApplication','$rootScope',
        function ($scope,$state, $stateParams, Jobapplication,ViewedJobApplication, $rootScope) {

            $scope.applicantList = "Viewed List";

            ViewedJobApplication.query({id: $stateParams.id}, function (result) {
                console.log(result);
                $scope.applications = result;
            });

            $scope.updateStatus=function (status, id){
                Jobapplication.get({id : id}, function(result) {
                    $scope.jobapplication = result;
                    $scope.jobapplication.applicantStatus = status;
                    Jobapplication.update($scope.jobapplication);
                    $state.go('job.applied', {id:$stateParams.id}, { reload: true });

                });
            };

            /*$scope.calculateAge = function(birthday) {
                // console.log(birthday+" starting date");
                var ageDifMs = Date.now() - new Date(birthday);
                console.log(new Date(ageDifMs));
                var ageDate = new Date(ageDifMs);
                console.log(ageDate.getUTCFullYear()- 1970);
                console.log(">>>>>>>>>>>>>>>>>.");
                console.log(Math.abs(ageDate.getUTCFullYear() - 1970));
                return Math.abs(ageDate.getUTCFullYear() - 1970);
            };*/

        }]);
