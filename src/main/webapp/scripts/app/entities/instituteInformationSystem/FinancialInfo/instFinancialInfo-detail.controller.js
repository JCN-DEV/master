'use strict';

angular.module('stepApp')
    .controller('InstFinancialInfoDetailController',
    ['$scope', '$state', '$q', '$rootScope', '$stateParams', 'entity', 'InstFinancialInfo',
    function ($scope, $state, $q, $rootScope, $stateParams, entity, InstFinancialInfo) {

       InstFinancialInfo.get({id : 0}, function(result){
           $scope.instFinancialInfos = result;

           if($scope.instFinancialInfos.length<=0){
               $state.go('instituteInfo.financialInfo.new',{},{reload:true});
           }

       }, function(){
            $state.go('instituteInfo.financialInfo.new',{},{reload:true});
       });

        var unsubscribe = $rootScope.$on('stepApp:instFinancialInfoUpdate', function(event, result) {
            $scope.instFinancialInfos = result;
        });

        $scope.$on('$destroy', unsubscribe);

    }]);
