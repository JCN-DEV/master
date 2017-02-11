'use strict';

angular.module('stepApp')
    .controller('UpazilaDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Upazila', 'District',
    function ($scope, $rootScope, $stateParams, entity, Upazila, District) {
        $scope.upazila = entity;
        $scope.load = function (id) {
            Upazila.get({id: id}, function(result) {
                $scope.upazila = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:upazilaUpdate', function(event, result) {
            $scope.upazila = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
