'use strict';

angular.module('stepApp')
    .controller('CmsTradeDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'CmsTrade', 'CmsCurriculum',
        function ($scope, $rootScope, $stateParams, entity, CmsTrade, CmsCurriculum) {
        $scope.cmsTrade = entity;
        $scope.load = function (id) {
            CmsTrade.get({id: id}, function(result) {
                $scope.cmsTrade = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:cmsTradeUpdate', function(event, result) {
            $scope.cmsTrade = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
