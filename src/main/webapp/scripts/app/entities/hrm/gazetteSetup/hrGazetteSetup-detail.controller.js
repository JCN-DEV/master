'use strict';

angular.module('stepApp')
    .controller('HrGazetteSetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrGazetteSetup',
    function ($scope, $rootScope, $stateParams, entity, HrGazetteSetup) {
        $scope.hrGazetteSetup = entity;
        $scope.load = function (id) {
            HrGazetteSetup.get({id: id}, function(result) {
                $scope.hrGazetteSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrGazetteSetupUpdate', function(event, result) {
            $scope.hrGazetteSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
