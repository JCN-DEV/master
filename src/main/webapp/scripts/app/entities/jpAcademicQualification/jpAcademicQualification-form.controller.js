'use strict';

angular.module('stepApp').controller('JpAcademicQualificationFormController',
        ['$scope', '$stateParams', 'entity', 'JpAcademicQualification', 'JpEmployee','$state',
        function($scope, $stateParams, entity, JpAcademicQualification, JpEmployee,$state) {
        $scope.jpAcademicQualification = entity;

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jpAcademicQualificationUpdate', result);
            $state.go('resume', null, { reload: true });
            //$modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jpAcademicQualification.id != null) {
                JpAcademicQualification.update($scope.jpAcademicQualification, onSaveFinished);


            } else {
                JpEmployee.get({id: 'my'}, function (result) {
                    $scope.jpAcademicQualification.jpEmployee = result;
                    JpAcademicQualification.save($scope.jpAcademicQualification, onSaveFinished);
                });

            }


        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
