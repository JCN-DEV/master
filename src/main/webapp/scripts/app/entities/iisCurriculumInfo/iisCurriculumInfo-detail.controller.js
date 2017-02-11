'use strict';

angular.module('stepApp')
    .controller('IisCurriculumInfoDetaileeeeeeeController',
    ['$scope','$rootScope','$stateParams','entity','IisCurriculumInfo','CmsCurriculum',
    function ($scope, $rootScope, $stateParams, entity, IisCurriculumInfo, CmsCurriculum) {
        $scope.iisCurriculumInfo = entity;
        $scope.load = function (id) {
            IisCurriculumInfo.get({id: id}, function(result) {
                $scope.iisCurriculumInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:iisCurriculumInfoUpdate', function(event, result) {
            $scope.iisCurriculumInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
