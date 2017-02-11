'use strict';

angular.module('stepApp').controller('DearnessAssignDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'DearnessAssign', 'PayScale',
        function($scope, $stateParams, $state, entity, DearnessAssign, PayScale) {

        $scope.dearnessAssign = entity;
        $scope.payscales = PayScale.query();
        $scope.load = function(id) {
            DearnessAssign.get({id : id}, function(result) {
                $scope.dearnessAssign = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:dearnessAssignUpdate', result);
            $state.go('dearnessAssign');
        };

        $scope.save = function () {
            if ($scope.dearnessAssign.id != null) {
                DearnessAssign.update($scope.dearnessAssign, onSaveFinished);
            } else {
                DearnessAssign.save($scope.dearnessAssign, onSaveFinished);
            }
        };

            $scope.calendar = {
                opened: {},
                dateFormat: 'yyyy-MM-dd',
                dateOptions: {},
                open: function ($event, which) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.calendar.opened[which] = true;
                }
            };

        $scope.clear = function() {
            $state.go('dearnessAssign');
        };
}]);
