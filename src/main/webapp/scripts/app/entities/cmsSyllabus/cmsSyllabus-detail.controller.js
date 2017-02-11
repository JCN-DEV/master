'use strict';

angular.module('stepApp')
    .controller('CmsSyllabusDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'CmsSyllabus', 'CmsCurriculum',
     function ($scope, $rootScope, $stateParams, entity, CmsSyllabus, CmsCurriculum) {
        $scope.cmsSyllabus = entity;
        $scope.load = function (id) {
            CmsSyllabus.get({id: id}, function(result) {
                $scope.cmsSyllabus = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:cmsSyllabusUpdate', function(event, result) {
            $scope.cmsSyllabus = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
