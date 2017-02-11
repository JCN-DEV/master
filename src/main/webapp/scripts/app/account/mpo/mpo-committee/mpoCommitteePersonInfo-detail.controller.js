'use strict';

angular.module('stepApp')
    .controller('MpoCommitteePersonInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'MpoCommitteePersonInfo', 'User',
    function ($scope, $rootScope, $stateParams, entity, MpoCommitteePersonInfo, User) {
        $scope.mpoCommitteePersonInfo = entity;
        $scope.load = function (id) {
            MpoCommitteePersonInfo.get({id: id}, function(result) {
                $scope.mpoCommitteePersonInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:mpoCommitteePersonInfoUpdate', function(event, result) {
            $scope.mpoCommitteePersonInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
