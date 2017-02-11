'use strict';

angular.module('stepApp').controller('JpEmployeeTrainingFormController',
        ['$scope', '$state', '$stateParams', 'entity', 'JpEmployeeTraining', 'JpEmployee', 'Country',
        function($scope, $state, $stateParams, entity, JpEmployeeTraining, JpEmployee, Country) {

        $scope.jpEmployeeTraining = entity;
        $scope.jpemployees = JpEmployee.query();
        $scope.countrys = Country.query({size:300});


        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jpEmployeeTrainingUpdate', result);
           // $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jpEmployeeTraining.id != null) {
                JpEmployeeTraining.update($scope.jpEmployeeTraining, onSaveFinished);
                $state.go('resume');
            } else {

                JpEmployee.get({id: 'my'}, function (result) {
                    $scope.jpEmployeeTraining.jpEmployee = result;
                    JpEmployeeTraining.save($scope.jpEmployeeTraining, onSaveFinished);
                    $state.go('resume');
                });
            }
        };

        $scope.clear = function() {
           // $modalInstance.dismiss('cancel');
        };
}]);
