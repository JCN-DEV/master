/*
 'use strict';

angular.module('stepApp').controller('InstInfraInfoDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', '$q',  'InstInfraInfo', 'Institute', 'InstBuilding', 'InstLand','entity','InstInfraBuildingDirections','AllInstInfraInfo','InstLabInfo','InstShopInfo','InstPlayGroundInfo','InstBuildingTemp','InstLandTemp','InstInfraInfoTemp', 'InstShopInfoTemp','InstLabInfoTemp','InstPlayGroundInfoTemp','InstInfraInfoTempAll',
        function($scope,$rootScope, $stateParams, $state, $q, InstInfraInfo, Institute, InstBuilding, InstLand,entity,InstInfraBuildingDirections,AllInstInfraInfo,InstLabInfo,InstShopInfo,InstPlayGroundInfo, InstBuildingTemp, InstLandTemp, InstInfraInfoTemp, InstShopInfoTemp, InstLabInfoTemp, InstPlayGroundInfoTemp, InstInfraInfoTempAll) {


        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        $scope.instInfraInfo = entity;


        $scope.instInfraBuildingDirections = InstInfraBuildingDirections.get({},function(result){
            $scope.positionLabels = result;
        });


        console.log($scope.instInfraBuildingDirections);


        $q.all([$scope.instInfraInfo.$promise]).then(function() {
           console.log($scope.instInfraInfo);
           $scope.instBuilding = $scope.instInfraInfo.instBuildingTemp;
           $scope.instLand = $scope.instInfraInfo.instLandTemp;
           if($scope.instInfraInfo.id != null){
               InstInfraInfoTempAll.get({institueid: $scope.instInfraInfo.institute.id},function(result){
                   if(result.instShopInfoList.length > 0){
                       $scope.instShopInfos = result.instShopInfoList;
                   }
                   else{
                       $scope.instShopInfos = [{
                           nameOrNumber: null,
                           buildingNameOrNumber: null,
                           length: null,
                           width: null,
                           id: null
                       }];
                   }

                   if(result.instLabInfoList.length > 0){
                       $scope.instLabInfos = result.instLabInfoList;
                   }
                   else{
                       $scope.instLabInfos = [{
                           nameOrNumber: null,
                           buildingNameOrNumber: null,
                           length: null,
                           width: null,
                           totalBooks: null,
                           id: null
                       }];
                   }

                   if(result.instPlayGroundInfoList.length > 0){
                       $scope.instPlayGroundInfos = result.instPlayGroundInfoList;
                   }
                   else{
                       $scope.instPlayGroundInfos = [{
                           playgroundNo: null,
                           area: null,
                           id: null
                       }];
                   }



                   console.log(result);
               },function(result){
                   console.log(result);
               });
           }
           else{
               $scope.instShopInfos = [{
                   nameOrNumber: null,
                   buildingNameOrNumber: null,
                   length: null,
                   width: null,
                   id: null
               }];
               $scope.instLabInfos = [{
                   nameOrNumber: null,
                   buildingNameOrNumber: null,
                   length: null,
                   width: null,
                   totalBooks: null,
                   id: null
               }];
               $scope.instPlayGroundInfos = [{
                   playgroundNo: null,
                   area: null,
                   id: null
               }];
           }

           return $scope.instInfraInfo.$promise;
        });



        //$scope.instInfraInfo = InstInfraInfo.get({id : $stateParams.id});;

        /!*$scope.institutes = Institute.query({filter: 'instinfrainfo-is-null'});*!/

        /!*$q.all([$scope.instInfraInfo.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.instInfraInfo.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.instInfraInfo.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });*!/

        /!*$scope.instbuildings = InstBuilding.query({filter: 'instinfrainfo-is-null'});*!/

        /!*$q.all([$scope.instInfraInfo.$promise, $scope.instbuildings.$promise]).then(function() {
            if (!$scope.instInfraInfo.instBuilding.id) {
                return $q.reject();
            }
            return InstBuilding.get({id : $scope.instInfraInfo.instBuilding.id}).$promise;
        }).then(function(instBuilding) {
            $scope.instbuildings.push(instBuilding);
        });*!/


        /!*$scope.instlands = InstLand.query({filter: 'instinfrainfo-is-null'});*!/

        /!*$q.all([$scope.instInfraInfo.$promise, $scope.instlands.$promise]).then(function() {
            if (!$scope.instInfraInfo.instLand.id) {
                return $q.reject();
            }
            return InstLand.get({id : $scope.instInfraInfo.instLand.id}).$promise;
        }).then(function(instLand) {
            $scope.instlands.push(instLand);
        });*!/

        /!*$scope.load = function(id) {
            InstInfraInfo.get({id : id}, function(result) {
                $scope.instInfraInfo = result;
                $scope.instLand = $scope.instInfraInfo.instLand;
                $scope.instBuilding = $scope.instInfraInfo.instBuilding;
            });
        };
            $scope.load($stateParams.id);*!/

        var onInstBuildingSaveSuccess = function (result) {
            $scope.$emit('stepApp:instInfraInfoUpdate', result);
            $scope.isSaving = false;
            $scope.instInfraInfo.instBuildingTemp = result;
            if(!$scope.instLand.id)
                InstLandTemp.save($scope.instLand, onInstLandSaveSuccess, onInstLandSaveError);
            else
                InstLandTemp.update($scope.instLand, onInstLandSaveSuccess, onInstLandSaveError);
        };

        var onInstLandSaveSuccess = function (result) {
            $scope.$emit('stepApp:instInfraInfoUpdate', result);
            $scope.isSaving = false;
            $scope.instInfraInfo.instLandTemp = result;
            if(!$scope.instInfraInfo.id){
                InstInfraInfoTemp.save($scope.instInfraInfo, onInstInfraInfoSaveSuccess, onInstInfraInfoSaveError);
                $rootScope.setSuccessMessage('stepApp.instInfraInfo.created');
            }
            else{
                InstInfraInfoTemp.update($scope.instInfraInfo, onInstInfraInfoSaveSuccess, onInstInfraInfoSaveError);
                $rootScope.setWarningMessage('stepApp.instInfraInfo.updated');
            }

        };
        var onPlayGroundSave = function(result){
            console.log(result);
        }

        var onLabSave = function(result){
            console.log(result);

        };

        var onShopSave = function(result){

            console.log(result);
        };

        var onInstInfraInfoSaveSuccess = function (result) {
            $scope.$emit('stepApp:instInfraInfoUpdate', result);
            $scope.isSaving = false;
            console.log("shop length :"+$scope.instShopInfos.length);
            console.log(+$scope.instShopInfos);
            angular.forEach($scope.instShopInfos, function(data){
                if(data.id!=null){
                    InstShopInfoTemp.update(data,onShopSave);
                }
                else{
                    if(data.nameOrNumber!=null && data.buildingNameOrNumber != null && data.length && data.width != null){
                        data.instInfraInfoTemp = result;
                        InstShopInfoTemp.save(data,onShopSave);
                    }

                }
            });
            $scope.instBuilding.dStatus =  true;
            console.log("instLabInfos length :"+$scope.instLabInfos.length);
            console.log(+$scope.instLabInfos);
            angular.forEach($scope.instLabInfos , function(data){
                if(data.id != null){
                    console.log('lab if');
                    InstLabInfoTemp.update(data, onLabSave);
                }
                else{
                    console.log('lab else');
                    if(data.nameOrNumber!=null && data.buildingNameOrNumber != null && data.length && data.width != null && data.totalBooks != null){
                        console.log('lab else inside if');
                        data.instInfraInfoTemp = result;
                        console.log(data.instInfraInfoTemp);
                        InstLabInfoTemp.save(data, onLabSave);
                    }

                }
            });

            angular.forEach($scope.instPlayGroundInfos, function(data){
                if(data.id != null){
                    console.log('playground if');
                    InstPlayGroundInfoTemp.update(data, onPlayGroundSave);
                }
                else{
                    console.log('playground else');
                    if(data.playgroundNo != null && data.area != null){
                        console.log('playground else inside if');
                        data.instInfraInfoTemp = result;
                        InstPlayGroundInfoTemp.save(data, onPlayGroundSave);
                    }

                }
            });

            $state.go('instituteInfo.infrastructureInfo',{},{reload:true});
            $rootScope.setSuccessMessage('stepApp.instInfraInfo.created');


        };


        var onInstBuildingSaveError = function (result) {
            $scope.isSaving = false;
        };

        var onInstLandSaveError = function (result) {
            $scope.isSaving = false;
        };


        var onInstInfraInfoSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.instBuilding.totalRoom =  22;
            $scope.isSaving = true;
            if ($scope.instInfraInfo.id != null) {
                if($scope.instInfraInfo.status = 1){
                    $scope.instInfraInfo.status = 3;
                    InstBuildingTemp.update($scope.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
                    $rootScope.setSuccessMessage('stepApp.instInfraInfo.created');
                }else{
                    $scope.instInfraInfo.status = 0;
                    InstBuildingTemp.update($scope.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
                    $rootScope.setSuccessMessage('stepApp.instInfraInfo.created');
                }

            } else {
                $scope.instInfraInfo.status = 0;
                console.log("Come to final save");
                InstBuildingTemp.save($scope.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
                $rootScope.setSuccessMessage('stepApp.instInfraInfo.created');
            }
        };

        $scope.clear = function() {
        };


        $scope.addWorkShopMore = function(){
            $scope.instShopInfos.push({
                nameOrNumber: null,
                buildingNameOrNumber: null,
                length: null,
                width: null,
                id: null
            });
        };

        $scope.removeShopInfoInfo = function(ShopInfo){
            var index =  $scope.instShopInfos.indexOf(ShopInfo);
            $scope.instShopInfos.splice(index,1);

        }
        $scope.addLibraryMore = function(){
            $scope.instLabInfos.push({
                nameOrNumber: null,
                buildingNameOrNumber: null,
                length: null,
                width: null,
                totalBooks: null,
                id: null
            });
        };
        $scope.removeLibraryInfo = function(bankInfo){
            var index =  $scope.instLabInfos.indexOf(bankInfo);
            $scope.instLabInfos.splice(index,1);

        }

        $scope.addPlayGroundMore = function(){
            $scope.instPlayGroundInfos.push({
                playgroundNo: null,
                area: null,
                id: null
            });
        }
        $scope.removePlayGroundInfo = function(PlayGroundInfo){
            var index =  $scope.instPlayGroundInfos.indexOf(PlayGroundInfo);
            $scope.instPlayGroundInfos.splice(index,1);

        }

}]);
*/


'use strict';

angular.module('stepApp').controller('InstInfraInfoDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', '$q',  'InstInfraInfo', 'Institute', 'InstBuilding', 'InstLand','entity','InstInfraBuildingDirections','AllInstInfraInfo','InstLabInfo','InstShopInfo','InstPlayGroundInfo','InstBuildingTemp','InstLandTemp','InstInfraInfoTemp', 'InstShopInfoTemp','InstLabInfoTemp','InstPlayGroundInfoTemp','InstInfraInfoTempAll',
        function($scope,$rootScope, $stateParams, $state, $q, InstInfraInfo, Institute, InstBuilding, InstLand,entity,InstInfraBuildingDirections,AllInstInfraInfo,InstLabInfo,InstShopInfo,InstPlayGroundInfo, InstBuildingTemp, InstLandTemp, InstInfraInfoTemp, InstShopInfoTemp, InstLabInfoTemp, InstPlayGroundInfoTemp, InstInfraInfoTempAll) {


            $scope.calendar = {
                opened: {},
                dateFormat: 'yyyy-MM-dd',
                dateOptions: {},
                open: function ($event, which) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.calendar.opened[which] = true;
                }
            };

            $scope.instInfraInfo = entity;


            $scope.instInfraBuildingDirections = InstInfraBuildingDirections.get({},function(result){
                $scope.positionLabels = result;
            });


            console.log($scope.instInfraBuildingDirections);


            $q.all([$scope.instInfraInfo.$promise]).then(function() {
                console.log($scope.instInfraInfo);
                $scope.instBuilding = $scope.instInfraInfo.instBuildingTemp;
                $scope.instLand = $scope.instInfraInfo.instLandTemp;
                if($scope.instInfraInfo.id != null){
                    InstInfraInfoTempAll.get({institueid: $scope.instInfraInfo.institute.id},function(result){
                        if(result.instShopInfoList.length > 0){
                            $scope.instShopInfos = result.instShopInfoList;
                        }
                        else{
                            $scope.instShopInfos = [{
                                nameOrNumber: null,
                                buildingNameOrNumber: null,
                                length: null,
                                width: null,
                                id: null
                            }];
                        }

                        if(result.instLabInfoList.length > 0){
                            $scope.instLabInfos = result.instLabInfoList;
                        }
                        else{
                            $scope.instLabInfos = [{
                                nameOrNumber: null,
                                buildingNameOrNumber: null,
                                length: null,
                                width: null,
                                totalBooks: null,
                                id: null
                            }];
                        }

                        if(result.instPlayGroundInfoList.length > 0){
                            $scope.instPlayGroundInfos = result.instPlayGroundInfoList;
                        }
                        else{
                            $scope.instPlayGroundInfos = [{
                                playgroundNo: null,
                                area: null,
                                id: null
                            }];
                        }



                        console.log(result);
                    },function(result){
                        console.log(result);
                    });
                }
                else{
                    $scope.instShopInfos = [{
                        nameOrNumber: null,
                        buildingNameOrNumber: null,
                        length: null,
                        width: null,
                        id: null
                    }];
                    $scope.instLabInfos = [{
                        nameOrNumber: null,
                        buildingNameOrNumber: null,
                        length: null,
                        width: null,
                        totalBooks: null,
                        id: null
                    }];
                    $scope.instPlayGroundInfos = [{
                        playgroundNo: null,
                        area: null,
                        id: null
                    }];
                }

                return $scope.instInfraInfo.$promise;
            });



            //$scope.instInfraInfo = InstInfraInfo.get({id : $stateParams.id});;

            /*$scope.institutes = Institute.query({filter: 'instinfrainfo-is-null'});*/

            /*$q.all([$scope.instInfraInfo.$promise, $scope.institutes.$promise]).then(function() {
             if (!$scope.instInfraInfo.institute.id) {
             return $q.reject();
             }
             return Institute.get({id : $scope.instInfraInfo.institute.id}).$promise;
             }).then(function(institute) {
             $scope.institutes.push(institute);
             });*/

            /*$scope.instbuildings = InstBuilding.query({filter: 'instinfrainfo-is-null'});*/

            /*$q.all([$scope.instInfraInfo.$promise, $scope.instbuildings.$promise]).then(function() {
             if (!$scope.instInfraInfo.instBuilding.id) {
             return $q.reject();
             }
             return InstBuilding.get({id : $scope.instInfraInfo.instBuilding.id}).$promise;
             }).then(function(instBuilding) {
             $scope.instbuildings.push(instBuilding);
             });*/


            /*$scope.instlands = InstLand.query({filter: 'instinfrainfo-is-null'});*/

            /*$q.all([$scope.instInfraInfo.$promise, $scope.instlands.$promise]).then(function() {
             if (!$scope.instInfraInfo.instLand.id) {
             return $q.reject();
             }
             return InstLand.get({id : $scope.instInfraInfo.instLand.id}).$promise;
             }).then(function(instLand) {
             $scope.instlands.push(instLand);
             });*/

            /*$scope.load = function(id) {
             InstInfraInfo.get({id : id}, function(result) {
             $scope.instInfraInfo = result;
             $scope.instLand = $scope.instInfraInfo.instLand;
             $scope.instBuilding = $scope.instInfraInfo.instBuilding;
             });
             };
             $scope.load($stateParams.id);*/

            var onInstBuildingSaveSuccess = function (result) {
                $scope.$emit('stepApp:instInfraInfoUpdate', result);
                $scope.isSaving = false;
                $scope.instInfraInfo.instBuildingTemp = result;
                if(!$scope.instLand.id)
                    InstLandTemp.save($scope.instLand, onInstLandSaveSuccess, onInstLandSaveError);
                else
                    InstLandTemp.update($scope.instLand, onInstLandSaveSuccess, onInstLandSaveError);
            };

            var onInstLandSaveSuccess = function (result) {
                $scope.$emit('stepApp:instInfraInfoUpdate', result);
                $scope.isSaving = false;
                $scope.instInfraInfo.instLandTemp = result;
                if(!$scope.instInfraInfo.id){
                    InstInfraInfoTemp.save($scope.instInfraInfo, onInstInfraInfoSaveSuccess, onInstInfraInfoSaveError);
                    $rootScope.setSuccessMessage('stepApp.instInfraInfo.created');
                }
                else{
                    InstInfraInfoTemp.update($scope.instInfraInfo, onInstInfraInfoSaveSuccess, onInstInfraInfoSaveError);
                    $rootScope.setWarningMessage('stepApp.instInfraInfo.updated');
                }

            };
            var onPlayGroundSave = function(result){
                console.log(result);
            }

            var onLabSave = function(result){
                console.log(result);

            };

            var onShopSave = function(result){

                console.log(result);
            };

            var onInstInfraInfoSaveSuccess = function (result) {
                $scope.$emit('stepApp:instInfraInfoUpdate', result);
                $scope.isSaving = false;
                console.log("shop length :"+$scope.instShopInfos.length);
                console.log(+$scope.instShopInfos);
                angular.forEach($scope.instShopInfos, function(data){
                    if(data.id!=null){
                        InstShopInfoTemp.update(data,onShopSave);
                    }
                    else{
                        if(data.nameOrNumber!=null && data.buildingNameOrNumber != null && data.length && data.width != null){
                            data.instInfraInfoTemp = result;
                            InstShopInfoTemp.save(data,onShopSave);
                        }

                    }
                });
                $scope.instBuilding.dStatus =  true;
                console.log("instLabInfos length :"+$scope.instLabInfos.length);
                console.log(+$scope.instLabInfos);
                angular.forEach($scope.instLabInfos , function(data){
                    if(data.id != null){
                        console.log('lab if');
                        InstLabInfoTemp.update(data, onLabSave);
                    }
                    else{
                        console.log('lab else');
                        if(data.nameOrNumber!=null && data.buildingNameOrNumber != null && data.length && data.width != null && data.totalBooks != null){
                            console.log('lab else inside if');
                            data.instInfraInfoTemp = result;
                            console.log(data.instInfraInfoTemp);
                            InstLabInfoTemp.save(data, onLabSave);
                        }

                    }
                });

                angular.forEach($scope.instPlayGroundInfos, function(data){
                    if(data.id != null){
                        console.log('playground if');
                        InstPlayGroundInfoTemp.update(data, onPlayGroundSave);
                    }
                    else{
                        console.log('playground else');
                        if(data.playgroundNo != null && data.area != null){
                            console.log('playground else inside if');
                            data.instInfraInfoTemp = result;
                            console.log("playground data data for ");
                            console.log(data);

                            InstPlayGroundInfoTemp.save(data, onPlayGroundSave);
                        }

                    }
                });

                $state.go('instituteInfo.infrastructureInfo',{},{reload:true});
                $rootScope.setSuccessMessage('stepApp.instInfraInfo.created');


            };


            var onInstBuildingSaveError = function (result) {
                $scope.isSaving = false;
            };

            var onInstLandSaveError = function (result) {
                $scope.isSaving = false;
            };


            var onInstInfraInfoSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.instBuilding.totalRoom =  22;
                $scope.isSaving = true;
                if ($scope.instInfraInfo.id != null) {
                    if($scope.instInfraInfo.status = 1){
                        $scope.instInfraInfo.status = 3;
                        InstBuildingTemp.update($scope.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
                        $rootScope.setSuccessMessage('stepApp.instInfraInfo.created');
                    }else{
                        $scope.instInfraInfo.status = 0;
                        InstBuildingTemp.update($scope.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
                        $rootScope.setSuccessMessage('stepApp.instInfraInfo.created');
                    }

                } else {
                    $scope.instInfraInfo.status = 0;
                    console.log("Come to final save");
                    InstBuildingTemp.save($scope.instBuilding, onInstBuildingSaveSuccess, onInstBuildingSaveError);
                    $rootScope.setSuccessMessage('stepApp.instInfraInfo.created');
                }
            };

            $scope.clear = function() {
            };


            $scope.addWorkShopMore = function(){
                $scope.instShopInfos.push({
                    nameOrNumber: null,
                    buildingNameOrNumber: null,
                    length: null,
                    width: null,
                    id: null
                });
            };

            $scope.removeShopInfoInfo = function(ShopInfo){
                var index =  $scope.instShopInfos.indexOf(ShopInfo);
                $scope.instShopInfos.splice(index,1);

            }
            $scope.addLibraryMore = function(){
                $scope.instLabInfos.push({
                    nameOrNumber: null,
                    buildingNameOrNumber: null,
                    length: null,
                    width: null,
                    totalBooks: null,
                    id: null
                });
            };
            $scope.removeLibraryInfo = function(bankInfo){
                var index =  $scope.instLabInfos.indexOf(bankInfo);
                $scope.instLabInfos.splice(index,1);

            }

            $scope.addPlayGroundMore = function(){
                $scope.instPlayGroundInfos.push({
                    playgroundNo: null,
                    area: null,
                    id: null
                });
            }
            $scope.removePlayGroundInfo = function(PlayGroundInfo){
                var index =  $scope.instPlayGroundInfos.indexOf(PlayGroundInfo);
                $scope.instPlayGroundInfos.splice(index,1);

            }

        }]);

