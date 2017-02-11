'use strict';

angular.module('stepApp')
    .controller('IisCurriculumInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'IisCurriculumInfo', 'CmsCurriculum','IisCurriculumInfoTemp',
    function ($scope, $rootScope, $stateParams, entity, IisCurriculumInfo, CmsCurriculum, IisCurriculumInfoTemp) {
        $scope.iisCurriculumInfo = entity;
        $scope.load = function (id) {
            IisCurriculumInfoTemp.get({id: id}, function(result) {
                $scope.iisCurriculumInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:iisCurriculumInfoUpdate', function(event, result) {
            $scope.iisCurriculumInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
