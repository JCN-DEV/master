'use strict';

angular.module('stepApp')
    .controller('InstInfraInfoDetailController',
    ['$scope', '$q','$state', '$rootScope', '$stateParams', 'entity', 'InstInfraInfo', 'Institute', 'InstBuilding', 'InstituteByLogin','InstLand','AllInstInfraInfo','InstInfraInfoTempAll',
    function ($scope, $q,$state, $rootScope, $stateParams, entity, InstInfraInfo, Institute, InstBuilding, InstituteByLogin,InstLand,AllInstInfraInfo,InstInfraInfoTempAll) {
        /*$scope.instInfraInfo = InstInfraInfo.get({id: 0}, function(response)
        {
            if(!response.id){
                    alert($scope.instInfraInfo.id);
                           $state.go('instituteInfo.infrastructureInfo.new',{},{reload:true});
                       }else{
                             $q.all([$scope.instInfraInfo.$promise]).then(function(){
                                        AllInstInfraInfo.get({institueid: $scope.instInfraInfo.institute.id},function(result){
                                            console.log(result);
                                            $scope.instShopInfos = result.instShopInfoList;
                                            $scope.instLabInfos = result.instLabInfoList;
                                            $scope.instPlayGroundInfos = result.instPlayGroundInfoList;
                                        });
                                       return $scope.instInfraInfo.$promise;
                                    });
                                    $scope.load = function (id) {
                                        InstInfraInfo.get({id: id}, function(result) {
                                            $scope.instInfraInfo = result;

                                        });
                                    };
                                    var unsubscribe = $rootScope.$on('stepApp:instInfraInfoUpdate', function(event, result) {
                                        $scope.instInfraInfo = result;
                                    });
                                    $scope.$on('$destroy', unsubscribe);
                       }
        });*/





/*
                                            }, function(){
                                                 //$state.go('instituteInfo.infrastructureInfo.new',{},{reload:true});
                                            });*/

                                            InstituteByLogin.get({}, function(result){
                                                $scope.institute = result;

                                                InstInfraInfoTempAll.get({institueid : result.id}, function(result){
                                                                    $scope.instInfraInfo = result;
                                                                     $scope.instShopInfos = result.instShopInfoList;
                                                                    $scope.instLabInfos = result.instLabInfoList;
                                                                    $scope.instPlayGroundInfos = result.instPlayGroundInfoList;
                                                                      console.log("guru ekhane");
                                                                      console.log($scope.instInfraInfo.instInfraInfo.instLand.boundaryEast);
                                                                      console.log("guru ");

                                                                    if(!$scope.instInfraInfo){
                                                                        $state.go('instituteInfo.infrastructureInfo.new',{},{reload:true});
                                                                    }

                                            });
                                            });




    }]);
