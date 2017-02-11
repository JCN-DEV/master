'use strict';

angular.module('stepApp')
    .controller('InstEmplWithhelHistDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstEmplWithhelHist', 'InstEmployee',
    function ($scope, $rootScope, $stateParams, entity, InstEmplWithhelHist, InstEmployee) {
        $scope.instEmplWithhelHist = entity;
        $scope.load = function (id) {
            InstEmplWithhelHist.get({id: id}, function(result) {
                $scope.instEmplWithhelHist = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instEmplWithhelHistUpdate', function(event, result) {
            $scope.instEmplWithhelHist = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
