'use strict';

angular.module('stepApp').controller('JpEmploymentHistoryFormController',
    ['$scope','$state', '$stateParams', 'entity', 'JpEmploymentHistory', 'JpEmployee',
        function($scope, $state, $stateParams, entity, JpEmploymentHistory, JpEmployee) {

        $scope.jpEmploymentHistory = entity;
        //$scope.jpemployees = JpEmployee.query();
        /*$scope.load = function(id) {
            JpEmploymentHistory.get({id : id}, function(result) {
                $scope.jpEmploymentHistory = result;
            });
        };*/

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

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jpEmploymentHistoryUpdate', result);
            //$modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jpEmploymentHistory.id != null) {
                JpEmploymentHistory.update($scope.jpEmploymentHistory, onSaveFinished);
                $state.go('resume');
            } else {
                JpEmployee.get({id: 'my'}, function (result) {
                    $scope.jpEmploymentHistory.jpEmployee = result;
                    JpEmploymentHistory.save($scope.jpEmploymentHistory, onSaveFinished);
                    $state.go('resume');
                });

            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
