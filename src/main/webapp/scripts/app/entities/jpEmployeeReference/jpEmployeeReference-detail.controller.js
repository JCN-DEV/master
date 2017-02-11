'use strict';

angular.module('stepApp')
    .controller('JpEmployeeReferenceDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'JpEmployeeReference', 'JpEmployee',
     function ($scope, $rootScope, $stateParams, entity, JpEmployeeReference, JpEmployee) {
        $scope.jpEmployeeReference = entity;
        $scope.load = function (id) {
            JpEmployeeReference.get({id: id}, function(result) {
                $scope.jpEmployeeReference = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jpEmployeeReferenceUpdate', function(event, result) {
            $scope.jpEmployeeReference = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
