'use strict';

angular.module('stepApp')
    .controller('DistrictDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'District', 'Division', 'Upazila',
     function ($scope, $rootScope, $stateParams, entity, District, Division, Upazila) {
        $scope.district = entity;
        $scope.load = function (id) {
            District.get({id: id}, function(result) {
                $scope.district = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:districtUpdate', function(event, result) {
            $scope.district = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
