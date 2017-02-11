'use strict';

angular.module('stepApp')

    .controller('InsAcademicInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InsAcademicInfo', 'Institute',
    function ($scope, $rootScope, $stateParams, entity, InsAcademicInfo, Institute) {
       console.log(entity);
        $scope.insAcademicInfo = entity;
        $scope.load = function (id) {
            InsAcademicInfo.get({id: id}, function (result) {
                $scope.insAcademicInfo = result;
            });
            $scope.$on('$destroy', unsubscribe);
        }
    }]);
