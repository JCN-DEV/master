'use strict';

angular.module('stepApp').controller('PgmsPenGrSetupDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'PgmsPenGrSetup', 'PgmsPenGrRate', 'PgmsPenGrRateList', 'User', 'Principal', 'DateUtils',
        function($scope, $stateParams, $state, entity, PgmsPenGrSetup,PgmsPenGrRate, PgmsPenGrRateList, User, Principal, DateUtils) {

       // $scope.pgmsPenGrSetup = entity;
        $scope.pgmsPenGrRatelist = [];
        $scope.users = User.query({filter: 'pgmsPenGrSetup-is-null'});

        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                });
            });
        };
        $scope.getLoggedInUser();

        $scope.load = function(id) {
            PgmsPenGrSetup.get({id : id}, function(result) {
                $scope.pgmsPenGrSetup = result;
            });
        };

        $scope.loadAllDataList = function ()
        {
           //console.log("StateId: "+$stateParams.id);
           //$scope.load($stateParams.id);
          // console.log("PRE:"+JSON.stringify($scope.iisPutupList));
           //$scope.iisPutupList = entity;
          // if($scope.iisPutupList.id == null)
           if($stateParams.id == null)
           {
                $scope.btnShow = true;
                $scope.pgmsPenGrSetup = entity;
                $scope.pgmsPenGrRatelist.push($scope.initiateModel());
           }
           else
           {
                //$scope.iisApplicantInfoList = IisPutupListApplicant.get({putupId : $scope.iisPutupList.id, sts: true});
                //console.log("ApplicantList: "+$scope.iisPutupList.id+", "+JSON.stringify($scope.iisApplicantInfoList));
                //var appDataJson = {putupId : $scope.iisPutupList.id, sts: true};
                $scope.pgmsPenGrSetup = entity;
                PgmsPenGrRateList.get({penGrSetId : $stateParams.id}, function(result) {
                    $scope.pgmsPenGrRatelist = result;
                    console.log("Rate info:"+JSON.stringify($scope.pgmsPenGrRatelist));
                    if(entity.setupVersion == 'PNV01' || entity.setupVersion == 'GRV01' )
                    {
                      $scope.btnShow = true;
                    }
                    else {
                        $scope.btnShow = false;
                    }
                });
           }

        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsPenGrSetupUpdate', result);
            $scope.isSaving = false;

            angular.forEach($scope.pgmsPenGrRatelist,function(pgRate)
            {
               pgRate.penGrSetId = result.id;
               pgRate.id = null;

               /*
               if (pgRate.id != null) {
                      PgmsPenGrRate.update(pgRate);
               } else { */

                    PgmsPenGrRate.save(pgRate);
               //}
            });
            $state.go("pgmsPenGrSetup");
        };
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:pgmsPenGrSetupUpdate', result);
            $scope.isSaving = true;
            if($scope.pgmsPenGrSetup.setupType == 'pension'){
                $scope.pgmsPenGrSetup.setupVersion = 'PNV01';
            }
            else {
                $scope.pgmsPenGrSetup.setupVersion = 'GRV01';
            }
            $scope.pgmsPenGrSetup.activeStatus = true;
            $scope.pgmsPenGrSetup.id = null;
            $scope.pgmsPenGrSetup.createBy = $scope.loggedInUser.id;
            $scope.pgmsPenGrSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
            PgmsPenGrSetup.save($scope.pgmsPenGrSetup, onSaveFinished, onSaveError);
        };

        var onSaveError = function (result) {
             $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.pgmsPenGrSetup.updateBy = $scope.loggedInUser.id;
            $scope.pgmsPenGrSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.pgmsPenGrSetup.id != null) {

                if($scope.pgmsPenGrSetup.setupType == 'pension'){
                    $scope.pgmsPenGrSetup.setupVersion = 'PNV'+$stateParams.id;
                }
                else {
                    $scope.pgmsPenGrSetup.setupVersion = 'GRV'+$stateParams.id;
                }
                $scope.pgmsPenGrSetup.activeStatus = false;
                PgmsPenGrSetup.update($scope.pgmsPenGrSetup, onSaveSuccess, onSaveError);

            } else {
                /*
                if($scope.pgmsPenGrSetup.setupType == 'pension'){
                    $scope.pgmsPenGrSetup.setupVersion = 'PNV01';
                }
                else {
                    $scope.pgmsPenGrSetup.setupVersion = 'GRV01';
                }*/
                $scope.pgmsPenGrSetup.createBy = $scope.loggedInUser.id;
                $scope.pgmsPenGrSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PgmsPenGrSetup.save($scope.pgmsPenGrSetup, onSaveFinished, onSaveError);
            }
        };
        $scope.pgsType = function(stype)
        {
            if(stype ==1)
            {
              $scope.setupType = "Pension";
              $scope.pgmsPenGrSetup.setupVersion = 'PNV01';
            }
            else{
              $scope.setupType = "Gratuity";
              $scope.pgmsPenGrSetup.setupVersion = 'GRV01';
            }
        };

        $scope.initiateModel = function()
        {
            return {
               penGrSetId: null,
               workingYear: null,
               rateOfPenGr: null,
               activeStatus: true,
               id: null
            };
        };
        $scope.loadAllDataList();

        $scope.addMore = function()
        {
            $scope.pgmsPenGrRatelist.push(
                {
                  penGrSetId: null,
                  workingYear: null,
                  rateOfPenGr: null,
                  activeStatus: true,
                  id: null
                }
            );
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };
}]);
