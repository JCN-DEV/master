'use strict';

angular.module('stepApp')
    .controller('CatDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Cat', 'Job',
    function ($scope, $rootScope, $stateParams, entity, Cat, Job) {
        $scope.cat = entity;
        $scope.load = function (id) {
            Cat.get({id: id}, function (result) {
                $scope.cat = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:catUpdate', function (event, result) {
            $scope.cat = result;
        });

        $scope.detailJobCat = false;
        $scope.showCatDetail = function (value) {
            $scope.detailJobCat = value;
        }
        $scope.$on('$destroy', unsubscribe);

    }]);
