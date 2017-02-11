'use strict';

angular.module('stepApp')
    .controller('DlBookInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlBookInfo', 'DlContTypeSet', 'DlContCatSet', 'DlContSCatSet',
    function ($scope, $rootScope, $stateParams, entity, DlBookInfo, DlContTypeSet, DlContCatSet, DlContSCatSet) {
        $scope.dlBookInfo = entity;
        $scope.load = function (id) {
            DlBookInfo.get({id: id}, function(result) {
                $scope.dlBookInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlBookInfoUpdate', function(event, result) {
            $scope.dlBookInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
