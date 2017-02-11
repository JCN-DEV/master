'use strict';

angular.module('stepApp')
    .controller('DesignationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Designation',
    function ($scope, $rootScope, $stateParams, entity, Designation) {
        $scope.designation = entity;
        $scope.load = function (id) {
            Designation.get({id: id}, function(result) {
                $scope.designation = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:designationUpdate', function(event, result) {
            $scope.designation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
