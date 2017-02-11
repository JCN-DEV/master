'use strict';

angular.module('stepApp')
    .controller('DlBookIssueDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlBookIssue', 'InstEmployee', 'DlContTypeSet', 'DlContCatSet', 'DlContSCatSet', 'DlBookReturn','DlBookEdition',
     function ($scope, $rootScope, $stateParams, entity, DlBookIssue, InstEmployee, DlContTypeSet, DlContCatSet, DlContSCatSet, DlBookReturn,DlBookEdition) {
        $scope.dlBookIssue = entity;
        $scope.dlBookEditions = DlBookEdition.query();
        $scope.load = function (id) {
            DlBookIssue.get({id: id}, function(result) {
                $scope.dlBookIssue = result;

            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlBookIssueUpdate', function(event, result) {
            $scope.dlBookIssue = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
