'use strict';

angular.module('stepApp')
    .controller('CmsSubjectDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'CmsSubject', 'CmsCurriculum', 'CmsSyllabus',
    function ($scope, $rootScope, $stateParams, entity, CmsSubject, CmsCurriculum, CmsSyllabus) {
        $scope.cmsSubject = entity;
        $scope.load = function (id) {
            CmsSubject.get({id: id}, function(result) {
                $scope.cmsSubject = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:cmsSubjectUpdate', function(event, result) {
            $scope.cmsSubject = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
