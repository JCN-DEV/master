'use strict';

angular.module('stepApp')
    .controller('AssetAuctionInformationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetAuctionInformation', 'AssetDistribution',
    function ($scope, $rootScope, $stateParams, entity, AssetAuctionInformation, AssetDistribution) {
        $scope.assetAuctionInformation = entity;
        $scope.load = function (id) {
            AssetAuctionInformation.get({id: id}, function(result) {
                $scope.assetAuctionInformation = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:assetAuctionInformationUpdate', function(event, result) {
            $scope.assetAuctionInformation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
