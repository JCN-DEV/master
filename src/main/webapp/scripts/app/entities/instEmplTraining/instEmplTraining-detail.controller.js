'use strict';

angular.module('stepApp')
    .controller('InstEmplTrainingDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstEmplTraining', 'InstEmployee',
    function ($scope, $rootScope, $stateParams, entity, InstEmplTraining, InstEmployee) {
        $scope.instEmplTraining = entity;
        $scope.load = function (id) {
            InstEmplTraining.get({id: id}, function(result) {
                $scope.instEmplTraining = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmplTrainingUpdate', function(event, result) {
            $scope.instEmplTraining = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
