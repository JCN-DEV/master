'use strict';

angular.module('stepApp')
    .controller('InstituteGovernBodyDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstituteGovernBody', 'Institute',
    function ($scope, $rootScope, $stateParams, entity, InstituteGovernBody, Institute) {
        $scope.instituteGovernBody = entity;
        $scope.load = function (id) {
            InstituteGovernBody.get({id: id}, function(result) {
                $scope.instituteGovernBody = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instituteGovernBodyUpdate', function(event, result) {
            $scope.instituteGovernBody = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
