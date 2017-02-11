'use strict';

angular.module('stepApp')
    .controller('CertNameDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'CertName',
    function ($scope, $rootScope, $stateParams, entity, CertName) {
        $scope.certName = entity;
        $scope.load = function (id) {
            CertName.get({id: id}, function(result) {
                $scope.certName = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:certNameUpdate', function(event, result) {
            $scope.certName = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
