'use strict';

angular.module('stepApp')
    .controller('JobDetailController',
     ['$scope', '$rootScope', '$stateParams', 'Principal', 'entity', 'EmployeeJobApplication', 'Job', 'Cat', 'Employer', 'Country', 'User',
     function ($scope, $rootScope, $stateParams, Principal, entity, EmployeeJobApplication, Job, Cat, Employer, Country, User) {
        $scope.job = entity;
        $scope.canApply=false;
        $scope.load = function (id) {
            Job.get({id: id}, function(result) {
             console.log('job: '+result);
                $scope.job = result;
            });
        };
console.log('comes to job details');
        $scope.users = [];
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN", "ROLE_EMPLOYER"];
        $scope.userRole = 'ROLE_USER';

        Principal.identity().then(function (account) {
            if(account != null) {
                $scope.account = account;

                if($scope.isInArray('ROLE_ADMIN', $scope.account.authorities))
                {
                    $scope.userRole = 'ROLE_ADMIN';
                }
                else if($scope.isInArray('ROLE_EMPLOYER', $scope.account.authorities))
                {
                    $scope.userRole = 'ROLE_EMPLOYER';
                }
                else if($scope.isInArray('ROLE_JOBSEEKER', $scope.account.authorities))
                {
                    $scope.canApply=true;
                    $scope.userRole = 'ROLE_USER';
                }
            }else{
                $scope.canApply=true;
            }

        });


        $scope.isInArray = function isInArray(value, array) {
            return array.indexOf(value) > -1;
        };

        var unsubscribe = $rootScope.$on('stepApp:jobUpdate', function(event, result) {
            $scope.job = result;
        });

        $scope.$on('$destroy', unsubscribe);

        EmployeeJobApplication.query({id:'my'},function(result){
            $scope.empJobs = result;
            console.log($scope.empJobs);

            for(var i=0; i<$scope.empJobs.length; i++){
                if($scope.empJobs[i].job.id==$scope.jobID){
                    $scope.canApply=false;
                }
            }
        });

    }]);
