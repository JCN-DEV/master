'use strict';

angular.module('stepApp')
    .controller('TrainingCategorySetupDetailController',
                ['$scope', '$rootScope', '$stateParams', 'entity', 'TrainingCategorySetup',
        function ($scope, $rootScope, $stateParams, entity, TrainingCategorySetup) {
        $scope.trainingCategorySetup = entity;
        $scope.load = function (id) {
            TrainingCategorySetup.get({id: id}, function(result) {
                $scope.trainingCategorySetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:trainingCategorySetupUpdate', function(event, result) {
            $scope.trainingCategorySetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
