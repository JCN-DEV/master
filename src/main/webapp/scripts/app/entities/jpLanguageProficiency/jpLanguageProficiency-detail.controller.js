'use strict';

angular.module('stepApp')
    .controller('JpLanguageProficiencyDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'JpLanguageProficiency', 'JpEmployee',
     function ($scope, $rootScope, $stateParams, entity, JpLanguageProficiency, JpEmployee) {
        $scope.jpLanguageProficiency = entity;
        $scope.load = function (id) {
            JpLanguageProficiency.get({id: id}, function(result) {
                $scope.jpLanguageProficiency = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jpLanguageProficiencyUpdate', function(event, result) {
            $scope.jpLanguageProficiency = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
