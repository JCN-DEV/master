'use strict';

angular.module('stepApp').controller('RejectedListController',
['$scope', '$stateParams', 'Jobapplication','JobApplicationsRejectedList','$state',
        function ($scope, $stateParams, Jobapplication,JobApplicationsRejectedList,$state) {

            $scope.applicantList = "Rejected List";

            JobApplicationsRejectedList.query({id: $stateParams.id}, function (result) {
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
