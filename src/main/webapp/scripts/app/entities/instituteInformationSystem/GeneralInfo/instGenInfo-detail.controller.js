'use strict';

angular.module('stepApp')
    .controller('InstGenInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstGenInfo', 'Institute', 'Upazila',
    function ($scope, $rootScope, $stateParams, entity, InstGenInfo, Institute, Upazila) {

        InstGenInfo.get({id : 0}, function(result){
            $scope.instGenInfo = result;
                 console.log($scope.instGenInfo);
                 console.log($scope.instGenInfo.type);
                 if($scope.instGenInfo.mpoEnlisted == true){
                     $scope.mpoListed = 'Yes';
                 }
                 else{
                     $scope.mpoListed = 'No';
                 }
                 if($scope.instGenInfo.type=='Government'){
                     $scope.instGenInfo.mpoEnlisted=false;
                     $scope.hidempoEnlisted=true;
                 }
        });

        var unsubscribe = $rootScope.$on('stepApp:instGenInfoUpdate', function(event, result) {
            $scope.instGenInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
