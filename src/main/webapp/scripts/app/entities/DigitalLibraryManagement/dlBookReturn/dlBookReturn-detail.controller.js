'use strict';

angular.module('stepApp')
    .controller('DlBookReturnDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlBookReturn', 'DlBookIssue', 'InstEmployee',
     function ($scope, $rootScope, $stateParams, entity, DlBookReturn, DlBookIssue, InstEmployee) {
        $scope.dlBookReturn = {};

         DlBookReturn.get({id: $stateParams.id}, function(result) {
                        $scope.dlBookReturn = result;
                        console.log("return detail");
                        console.log($scope.dlBookReturn);
                    });
        var unsubscribe = $rootScope.$on('stepApp:dlBookReturnUpdate', function(event, result) {
            $scope.dlBookReturn = result;
        });
        $scope.$on('$destroy', unsubscribe);


        $scope.DateForRecipt=new Date();
        $scope.count=0;


    }]);
