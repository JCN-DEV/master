'use strict';

angular.module('stepApp').controller('JpEmployeeReferenceFormController',
    ['$scope','$state', '$stateParams', 'entity', 'JpEmployeeReference', 'JpEmployee',
        function($scope,$state, $stateParams, entity, JpEmployeeReference, JpEmployee) {

        $scope.jpEmployeeReference = entity;
        $scope.jpemployees = JpEmployee.query();
        $scope.load = function(id) {
            JpEmployeeReference.get({id : id}, function(result) {
                $scope.jpEmployeeReference = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jpEmployeeReferenceUpdate', result);
           // $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jpEmployeeReference.id != null) {
                JpEmployeeReference.update($scope.jpEmployeeReference, onSaveFinished);
                $state.go('resume');
            } else {

                JpEmployee.get({id: 'my'}, function (result) {
                    $scope.jpEmployeeReference.jpEmployee = result;
                    JpEmployeeReference.save($scope.jpEmployeeReference, onSaveFinished);
                    $state.go('resume');
                });
            }
        };

        $scope.clear = function() {
           // $modalInstance.dismiss('cancel');
        };
}]);
