'use strict';

angular.module('stepApp').controller('UnviewedListController',
['$scope', '$stateParams', 'Jobapplication','UniewedJobApplication','$state',
        function ($scope, $stateParams, Jobapplication,UniewedJobApplication,$state) {

            $scope.applicantList = "Unviewed List";

            UniewedJobApplication.query({id: $stateParams.id}, function (result) {
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

        }]);
