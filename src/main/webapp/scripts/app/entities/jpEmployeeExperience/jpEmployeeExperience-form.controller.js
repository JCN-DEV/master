'use strict';

angular.module('stepApp').controller('JpEmployeeExperienceFormController',
    ['$scope', '$state', '$stateParams', 'entity', 'JpEmployeeExperience', 'JpEmployee',
        function($scope,$state, $stateParams, entity, JpEmployeeExperience, JpEmployee) {

        $scope.jpEmployeeExperience = entity;
        $scope.jpemployees = JpEmployee.query();
        $scope.load = function(id) {
            JpEmployeeExperience.get({id : id}, function(result) {
                $scope.jpEmployeeExperience = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jpEmployeeExperienceUpdate', result);
           // $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jpEmployeeExperience.id != null) {
                JpEmployeeExperience.update($scope.jpEmployeeExperience, onSaveFinished);
                $state.go('resume');
            } else {
                JpEmployee.get({id: 'my'}, function (result) {
                    $scope.jpEmployeeExperience.jpEmployee = result;
                    JpEmployeeExperience.save($scope.jpEmployeeExperience, onSaveFinished);
                    $state.go('resume');
                });
            }
        };

        $scope.clear = function() {
           // $modalInstance.dismiss('cancel');
        };
}]);
