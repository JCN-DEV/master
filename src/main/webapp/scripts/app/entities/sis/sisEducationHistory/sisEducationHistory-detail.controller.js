'use strict';

angular.module('stepApp')
    .controller('SisEducationHistoryDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'SisEducationHistory', 'EduLevel', 'EduBoard',
    function ($scope, $rootScope, $stateParams, entity, SisEducationHistory, EduLevel, EduBoard) {
        $scope.sisEducationHistory = entity;
        $scope.load = function (id) {
            SisEducationHistory.get({id: id}, function(result) {
                $scope.sisEducationHistory = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:sisEducationHistoryUpdate', function(event, result) {
            $scope.sisEducationHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
