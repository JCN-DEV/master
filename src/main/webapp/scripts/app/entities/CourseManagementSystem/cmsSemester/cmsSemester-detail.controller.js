'use strict';

angular.module('stepApp')
    .controller('CmsSemesterDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'CmsSemester', 'CmsCurriculum',
        function ($scope, $rootScope, $stateParams, entity, CmsSemester, CmsCurriculum) {
        $scope.cmsSemester = entity;
        /*$scope.load = function (id) {
            CmsSemester.get({id: id}, function(result) {
                $scope.cmsSemester = result;
            });
        };*/
        var unsubscribe = $rootScope.$on('stepApp:cmsSemesterUpdate', function(event, result) {
            $scope.cmsSemester = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
