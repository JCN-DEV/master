'use strict';

angular.module('stepApp')
    .controller('CmsSubAssignDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'CmsSubAssign', 'CmsCurriculum', 'CmsTrade', 'CmsSemester', 'CmsSyllabus',
        function ($scope, $rootScope, $stateParams, entity, CmsSubAssign, CmsCurriculum, CmsTrade, CmsSemester, CmsSyllabus) {
        $scope.cmsSubAssign = entity;
        $scope.load = function (id) {
            CmsSubAssign.get({id: id}, function(result) {
                $scope.cmsSubAssign = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:cmsSubAssignUpdate', function(event, result) {
            $scope.cmsSubAssign = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
