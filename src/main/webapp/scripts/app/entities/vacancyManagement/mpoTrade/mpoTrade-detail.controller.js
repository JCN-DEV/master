'use strict';

angular.module('stepApp')
    .controller('MpoTradeDetailController', function ($scope, $rootScope, $stateParams, MpoTrade, CmsTrade, User) {
        $scope.mpoTrade = {};
        /*$scope.load = function (id) {

        };*/
        console.log('asdljfalksjdflkjasdlkjflkasdjflkjsadflk');
        MpoTrade.get({id: $stateParams.id}, function(result) {
            $scope.mpoTrade = result;
        });
        var unsubscribe = $rootScope.$on('stepApp:mpoTradeUpdate', function(event, result) {
            $scope.mpoTrade = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
