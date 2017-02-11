'use strict';

angular.module('stepApp')
    .controller('PgmsAppRetirmntNmineDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'PgmsAppRetirmntNmine',
    function ($scope, $rootScope, $stateParams, entity, PgmsAppRetirmntNmine) {
        $scope.pgmsAppRetirmntNmine = entity;
        $scope.load = function (id) {
            PgmsAppRetirmntNmine.get({id: id}, function(result) {
                $scope.pgmsAppRetirmntNmine = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pgmsAppRetirmntNmineUpdate', function(event, result) {
            $scope.pgmsAppRetirmntNmine = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
