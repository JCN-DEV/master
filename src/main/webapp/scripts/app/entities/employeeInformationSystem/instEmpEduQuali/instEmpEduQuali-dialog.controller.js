'use strict';

angular.module('stepApp').controller('InstEmpEduQualiDialogController',
    ['$scope','$rootScope', '$stateParams', 'Principal', '$state', 'InstEmployeeCode', 'DataUtils', 'InstEmpEduQuali','entity', 'InstEmployee','InstEmpEduQualisCurrent','CurrentInstEmployee','$q','EduBoard','EduLevel', '$timeout','EduBoardByType','ActiveEduLevels',
        function($scope, $rootScope, $stateParams, Principal, $state, InstEmployeeCode, DataUtils, InstEmpEduQuali, entity, InstEmployee,InstEmpEduQualisCurrent,CurrentInstEmployee,$q,EduBoard,EduLevel, $timeout, EduBoardByType, ActiveEduLevels) {

          /*  console.log('-------------stateParams');
            console.log($stateParams);
        InstEmpEduQualisCurrent.get({},function(result){
            $scope.instEmpEduQualis = result;
            if($scope.instEmpEduQualis.length==0){
                $scope.instEmpEduQualis=entity;
            }
            console.log(result);
        },function(result){
           console.log(result);
            $scope.instEmpEduQualis = entity;
        });*/
            $scope.years = [];
            var currentYear = new Date().getFullYear();
            EduBoardByType.query({boardType :'board'}, function(result){
                $scope.eduBoards = result;
            });

            EduBoardByType.query({boardType :'university'}, function(result){
                $scope.eduUniversitys = result;
            });

            //$scope.eduBoards = EduBoard.query();
           // $scope.eduLevels = EduLevel.query();
            $scope.eduLevels = ActiveEduLevels.query();
       if($stateParams.instEmpEduQuali != null){
           $scope.instEmpEduQualis=$stateParams.instEmpEduQuali;
           $scope.education = 'Education';
       }else{
            $scope.education = '';
            $state.go('employeeInfo.educationalHistory',{},{reload:true});
       }
        $scope.instEmployee=null;
        CurrentInstEmployee.get({},function(result){
            $scope.instEmployee=result;
            console.log('-----------------'+result.mpoAppStatus);
            if(result.mpoAppStatus > 2){
                console.log("Eligible to apply");
                $rootScope.setErrorMessage('You have applied for mpo.So you are not allowed to edit');
                $state.go('employeeInfo.personalInfo');
            }
            console.log(result);
        });
        console.log($scope.instEmployee);
      /*  Principal.identity().then(function (account) {
            $scope.account = account;
            InstEmployeeCode.get({'code':$scope.account.login},function(result){
                $scope.instEmployee = result.instEmployee;
                console.log(result);
            });
        });*/
        /*$scope.instEmpEduQuali = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            InstEmpEduQuali.get({id : id}, function(result) {
                $scope.instEmpEduQuali = result;
            });
        };*/
        $scope.AddMore = function(){
            $scope.instEmpEduQualis.push(
                {
                    isGpaResult: true,
                    fromUniversity: false,
                    certificateName: null,
                    board: null,
                    session: null,
                    semester: null,
                    rollNo: null,
                    passingYear: null,
                    cgpa: null,
                    certificateCopy: null,
                    certificateCopyContentType: null,
                    status: null,
                    id: null,
                    instEmployee: null
                }
            );
            // Start Add this code for showing required * in add more fields
            $timeout(function() {
                $rootScope.refreshRequiredFields();
            }, 100);
            // End Add this code for showing required * in add more fields

        }

        $scope.k = [3,2,3,5,4];
        $scope.a = [];
        console.log("================" + $scope.k.length + "======================")
        console.log("================" + $scope.a.length + "======================")


        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmpEduQualiUpdate', result);
            $scope.isSaving = false;
            $state.go('employeeInfo.educationalHistory');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            angular.forEach($scope.instEmpEduQualis, function(data){
                if(data.id != null){
                    InstEmpEduQuali.update(data, onSaveSuccess, onSaveError);
                    $rootScope.setWarningMessage('stepApp.instEmployee.updated');
                }
                else{
                    if(data.certificateName != null || data.board != null || data.session != null || data.semester != null || data.rollNo != null || data.rollNo != null || data.passingYear != null || data.cgpa != null || data.certificateCopy != nul || data.certificateCopyContentType != null){
                        data.instEmployee=$scope.instEmployee;
                        InstEmpEduQuali.save(data, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.instEmployee.created');
                    }

                }
            });
            $q.all(requests).then(function () {
                $state.go('employeeInfo.educationalHistory',{},{reload:true});
            });
            /*$state.go('employeeInfo.educationalHistory',{},{reload :true });*/


        };

        $scope.clear = function() {
            $state.go('employeeInfo.educationalHistory');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setCertificateCopy = function ($file, instEmpEduQuali) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmpEduQuali.certificateCopy = base64Data;
                        instEmpEduQuali.certificateCopyContentType = $file.type;
                        instEmpEduQuali.certificateCopyName = $file.name;
                    });
                };
            }
        };

            $scope.initDates = function() {
                var i;
                for (i = currentYear ;i >= currentYear-50; i--) {
                    $scope.years.push(i);
                    //console.log( $scope.years);
                }
            };
            $scope.initDates();
}]);
