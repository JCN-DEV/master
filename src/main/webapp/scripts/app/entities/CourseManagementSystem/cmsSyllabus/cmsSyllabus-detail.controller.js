'use strict';

angular.module('stepApp')
    .controller('CmsSyllabusDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'CmsSyllabus', 'CmsCurriculum',
        function ($scope, $rootScope, $stateParams, entity, CmsSyllabus, CmsCurriculum) {

        $scope.cmsSyllabus = {};
        $scope.content = '';

        $scope.load = function () {
            CmsSyllabus.get({id: $stateParams.id}, function(result) {
                $scope.cmsSyllabus = result;
                if ($scope.cmsSyllabus.syllabus) {
                    var blob = $rootScope.b64toBlob($scope.cmsSyllabus.syllabus, 'application/pdf');
                    $scope.content = (window.URL || window.webkitURL).createObjectURL(blob);
                }
            });
        };

        $scope.load();

    }]);
