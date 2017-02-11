'use strict';

angular.module('stepApp')
    .controller('InstEmplDesignationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstEmplDesignation',
    function ($scope, $rootScope, $stateParams, entity, InstEmplDesignation) {
        $scope.instEmplDesignation = entity;
        $scope.load = function (id) {
            InstEmplDesignation.get({id: id}, function(result) {
                $scope.instEmplDesignation = result;
            });
        };

        console.log("Amanur Rahman");
        console.log($scope.instEmplDesignation);

        var unsubscribe = $rootScope.$on('stepApp:instEmplDesignationUpdate', function(event, result) {
            $scope.instEmplDesignation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
