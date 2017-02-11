'use strict';

angular.module('stepApp')
    .controller('InstAdmInfoDetailController',
    ['$scope', '$q', '$state', '$rootScope', '$stateParams', 'entity', 'InstAdmInfo', 'Institute','InstituteAllInfo','InstAdmInfoTemp','InstituteAllInfosTemp',
    function ($scope, $q, $state, $rootScope, $stateParams, entity, InstAdmInfo, Institute,InstituteAllInfo, InstAdmInfoTemp, InstituteAllInfosTemp) {

        InstAdmInfoTemp.get({id : 0}, function(result){
            $scope.instAdmInfo = result;

            if(!$scope.instAdmInfo){
                $state.go('instituteInfo.adminInfo.new',{},{reload:true});
            }

            InstituteAllInfosTemp.get({id: $scope.instAdmInfo.institute.id},function(result){
               $scope.instGovernBodys = result.instGovernBody;
            });
        }, function(){
             $state.go('instituteInfo.adminInfo.new',{},{reload:true});
        });

        var unsubscribe = $rootScope.$on('stepApp:instAdmInfoUpdate', function(event, result) {
            $scope.instAdmInfo = result;
        });

        $scope.$on('$destroy', unsubscribe);

    }]);
