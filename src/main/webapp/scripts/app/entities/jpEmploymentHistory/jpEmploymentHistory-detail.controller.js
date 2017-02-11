'use strict';

angular.module('stepApp')
    .controller('JpEmploymentHistoryDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'JpEmploymentHistory', 'JpEmployee',
     function ($scope, $rootScope, $stateParams, entity, JpEmploymentHistory, JpEmployee) {
        $scope.jpEmploymentHistory = entity;
        $scope.load = function (id) {
            JpEmploymentHistory.get({id: id}, function(result) {
                $scope.jpEmploymentHistory = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jpEmploymentHistoryUpdate', function(event, result) {
            $scope.jpEmploymentHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
