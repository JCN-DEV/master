'use strict';

angular.module('stepApp').controller('SearchApplicantController',
['$scope', '$stateParams', 'DataUtils','JobApplicationsShortList', 'Jobapplication', '$state',
        function ($scope, $stateParams, DataUtils,JobApplicationsShortList, Jobapplication, $state) {

            /*JobApplicationsShortList.query({id: $stateParams.id}, function (result) {
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
            };*/

        }]);
