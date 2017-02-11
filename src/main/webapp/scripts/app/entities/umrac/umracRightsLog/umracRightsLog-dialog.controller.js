'use strict';

angular.module('stepApp').controller('UmracRightsLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'UmracRightsLog',
        function($scope, $stateParams, $modalInstance, entity, UmracRightsLog) {

        $scope.umracRightsLog = entity;
        $scope.load = function(id) {
            UmracRightsLog.get({id : id}, function(result) {
                $scope.umracRightsLog = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:umracRightsLogUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.umracRightsLog.id != null) {
                UmracRightsLog.update($scope.umracRightsLog, onSaveFinished);
            } else {
                UmracRightsLog.save($scope.umracRightsLog, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
