'use strict';

angular.module('stepApp')
    .controller('EduBoardDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'EduBoard',
    function ($scope, $rootScope, $stateParams, entity, EduBoard) {
        $scope.eduBoard = entity;
        $scope.load = function (id) {
            EduBoard.get({id: id}, function(result) {
                $scope.eduBoard = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:eduBoardUpdate', function(event, result) {
            $scope.eduBoard = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
