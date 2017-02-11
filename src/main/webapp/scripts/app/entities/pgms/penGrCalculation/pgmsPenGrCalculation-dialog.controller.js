'use strict';

angular.module('stepApp').controller('PgmsPenGrCalculationDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'PgmsPenGrCalculation', 'HrGradeSetup', 'HrPayScaleSetup', 'PgmsPenGrRate', 'PgmsPenGrVersionId', 'PgmsPenGrPrcRate', 'User', 'Principal', 'DateUtils',
        function($scope, $stateParams, $state, entity, PgmsPenGrCalculation, HrGradeSetup, HrPayScaleSetup, PgmsPenGrRate, PgmsPenGrVersionId ,PgmsPenGrPrcRate, User, Principal, DateUtils) {

        $scope.pgmsPenGrCalculation = entity;
        $scope.hrgradesetups = HrGradeSetup.query();
        $scope.hrpayscalesetups = HrPayScaleSetup.query();
        $scope.pgmspengrrates = PgmsPenGrRate.query();
        $scope.pensionParcentageList = {};
        $scope.gratuityRateList = {};
         var penTotal, graTotal, penPercentage, graRate;

        $scope.users = User.query({filter: 'pgmsPenGrCalculation-is-null'});

        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                });PgmsPenGrVersionId
            });
        };
        $scope.getLoggedInUser();

        $scope.selCategory = function(selectType)
        {
          //console.log("Select Type:"+selectType);
          if(selectType == 'Contemporary')
          {
           $scope.pgmsPenGrCalculation.categoryType = "gratuity";
          }
          else {
             $scope.pgmsPenGrCalculation.categoryType = "pension";
          }

        }

        $scope.pgTatal = function()
        {
            var appDataJson = {pVersion : 'PNV01', rVersion : 'GRV01', wrkYear : $scope.pgmsPenGrCalculation.workingYear.workingYear};
            console.log("appDataJsonPenRate: "+JSON.stringify(appDataJson));
            PgmsPenGrPrcRate.get(appDataJson, function(result) {
             //console.log("Pension and Gratuity Rate Info: "+JSON.stringify(result));
                 var penId = 0;
                 angular.forEach(result,function(application)
                 {
                    application.putupId = result.id;
                    if (penId== 0) {
                           penPercentage = application.rateOfPenGr;
                           penId +=1;
                    }
                    else {

                         graRate = application.rateOfPenGr
                    }
                 });

                 if($scope.pgmsPenGrCalculation.categoryType == "pension" )
                 {
                     var basic = $scope.pgmsPenGrCalculation.salaryInfo.basicPayScale;
                     var festivalAllowance = 15600;
                     var medicalAllowance = 2500;

                     penTotal =  (basic * penPercentage)/2 * graRate + [(basic * penPercentage)/2 + (festivalAllowance + medicalAllowance)];
                     $scope.pgcTotal = penTotal;
                 }
                 else {
                      var graTotal;
                      var basic = $scope.pgmsPenGrCalculation.salaryInfo.basicPayScale;

                      graTotal = [(basic * penPercentage)/2 * graRate] + ((basic * penPercentage)/2 *  (graRate/2));

                      //console.log("Current Grauity Total:"+JSON.stringify($scope.pensionParcentageList));
                      console.log("Current Basic:"+JSON.stringify(basic));
                      console.log("Current pensionPercentage:"+JSON.stringify(penPercentage));
                      console.log("Current gratuityRate:"+JSON.stringify(graRate));
                      $scope.pgcTotal = graTotal;
                 }


            });


        };
        $scope.load = function(id) {
            PgmsPenGrCalculation.get({id : id}, function(result) {
                $scope.pgmsPenGrCalculation = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsPenGrCalculationUpdate', result);
            $scope.isSaving = false;
            $state.go("pgmsPenGrCalculation");
        };

        $scope.save = function () {
            $scope.pgmsPenGrCalculation.updateBy = $scope.loggedInUser.id;
            $scope.pgmsPenGrCalculation.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.pgmsPenGrCalculation.id != null) {
                PgmsPenGrCalculation.update($scope.pgmsPenGrCalculation, onSaveFinished);
            } else {
                $scope.pgmsPenGrCalculation.createBy = $scope.loggedInUser.id;
                $scope.pgmsPenGrCalculation.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PgmsPenGrCalculation.save($scope.pgmsPenGrCalculation, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };
}]);
