'use strict';

angular.module('stepApp')
    .controller('DlBookReturnDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlBookReturn', 'DlBookIssue', 'InstEmployee',
    function ($scope, $rootScope, $stateParams, entity, DlBookReturn, DlBookIssue, InstEmployee) {
        $scope.dlBookReturn = entity;
        $scope.load = function (id) {
            DlBookReturn.get({id: id}, function(result) {
                $scope.dlBookReturn = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlBookReturnUpdate', function(event, result) {
            $scope.dlBookReturn = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
