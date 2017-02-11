'use strict';

angular.module('stepApp')
    .controller('MpoCommitteeHistoryDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'MpoCommitteeHistory', 'MpoCommitteePersonInfo',
    function ($scope, $rootScope, $stateParams, entity, MpoCommitteeHistory, MpoCommitteePersonInfo) {
        $scope.mpoCommitteeHistory = entity;
        $scope.load = function (id) {
            MpoCommitteeHistory.get({id: id}, function(result) {
                $scope.mpoCommitteeHistory = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:mpoCommitteeHistoryUpdate', function(event, result) {
            $scope.mpoCommitteeHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
