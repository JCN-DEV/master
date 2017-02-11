'use strict';

angular.module('stepApp').controller('JpLanguageProficiencyFormController',
    ['$scope','$state', '$stateParams', 'entity', 'JpLanguageProficiency', 'JpEmployee',
        function($scope,$state, $stateParams, entity, JpLanguageProficiency, JpEmployee) {

        $scope.jpLanguageProficiency = entity;


        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jpLanguageProficiencyUpdate', result);
           // $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jpLanguageProficiency.id != null) {
                JpLanguageProficiency.update($scope.jpLanguageProficiency, onSaveFinished);
                $state.go('resume');
            } else {
                JpEmployee.get({id: 'my'}, function (result) {
                    $scope.jpLanguageProficiency.jpEmployee = result;
                    JpLanguageProficiency.save($scope.jpLanguageProficiency, onSaveFinished);
                    $state.go('resume');
                });
            }
        };

        $scope.clear = function() {
            $state.go('resume');
        };
}]);
