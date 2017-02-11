'use strict';

angular.module('stepApp')
    .controller('IisCourseInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'IisCourseInfo', 'IisCurriculumInfo', 'CmsTrade', 'CmsSubject','IisCourseInfoTemp',
    function ($scope, $rootScope, $stateParams, entity, IisCourseInfo, IisCurriculumInfo, CmsTrade, CmsSubject, IisCourseInfoTemp) {
        $scope.iisCourseInfo = entity;
        $scope.load = function (id) {
            IisCourseInfoTemp.get({id: id}, function(result) {
                $scope.iisCourseInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:iisCourseInfoUpdate', function(event, result) {
            $scope.iisCourseInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
