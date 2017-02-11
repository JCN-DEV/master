'use strict';

angular.module('stepApp')
    .controller('PgmsRetirmntAttachInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'PgmsRetirmntAttachInfo',
    function ($scope, $rootScope, $stateParams, entity, PgmsRetirmntAttachInfo) {
        $scope.pgmsRetirmntAttachInfo = entity;
        $scope.load = function (id) {
            PgmsRetirmntAttachInfo.get({id: id}, function(result) {
                $scope.pgmsRetirmntAttachInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pgmsRetirmntAttachInfoUpdate', function(event, result) {
            $scope.pgmsRetirmntAttachInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
