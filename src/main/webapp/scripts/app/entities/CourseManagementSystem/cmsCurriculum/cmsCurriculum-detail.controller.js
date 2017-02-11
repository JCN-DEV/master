'use strict';

angular.module('stepApp')
    .controller('CmsCurriculumDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'CmsCurriculum',
        function ($scope, $rootScope, $stateParams, entity, CmsCurriculum) {
        $scope.cmsCurriculum = entity;

        console.log($scope.cmsCurriculum);
        $scope.load = function (id) {
            CmsCurriculum.get({id: id}, function(result) {
                $scope.cmsCurriculum = result;
            });
        };

        var unsubscribe = $rootScope.$on('stepApp:cmsCurriculumUpdate', function(event, result) {
            $scope.cmsCurriculum = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
