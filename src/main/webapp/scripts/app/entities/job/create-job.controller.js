'use strict';

angular.module('stepApp').controller('CreateJobController',
    ['$scope', '$stateParams', 'entity', 'Job', 'Cat', 'Employer', 'Country', 'User',
        function($scope, $stateParams, entity, Job, Cat, Employer, Country, User) {

        $scope.job = entity;
        $scope.cats = Cat.query({size: 500});
        $scope.employers = Employer.query({size: 500});
        $scope.countrys = Country.query({size: 500});
        $scope.users = User.query({size: 500});
        $scope.load = function(id) {
            Job.get({id : id}, function(result) {
                $scope.job = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:jobUpdate', result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.job.id != null) {
                Job.update($scope.job, onSaveSuccess, onSaveError);
            } else {
                Job.save($scope.job, onSaveSuccess, onSaveError);
            }
        };


}]);
