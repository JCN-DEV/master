'use strict';

angular.module('stepApp').controller('InstEmployeeRegistrationController',
    ['$scope', '$rootScope', '$stateParams', '$state', '$q', 'DataUtils', 'entity', 'InstituteByLogin', 'InstEmployee', 'Institute',  'Religion', 'CourseTech', 'CourseSub', 'InstEmpAddress', 'InstEmpEduQuali','InstEmplDesignation','AllInstEmplDesignationsList','InstGenInfosByCurrentUser','InstLevel','IisTradesOfCurrentInst','CmsSubAssignByTrade','ActiveInstEmplDesignation','InstDesignationByInstLevel','ActiveInstEmplDesignationByType','HrDesignationSetup','HrEmplTypeInfo',
        function($scope, $rootScope, $stateParams, $state, $q, DataUtils, entity, InstituteByLogin, InstEmployee, Institute,  Religion, CourseTech, CourseSub, InstEmpAddress, InstEmpEduQuali, InstEmplDesignation,AllInstEmplDesignationsList,InstGenInfosByCurrentUser,InstLevel,IisTradesOfCurrentInst,CmsSubAssignByTrade,ActiveInstEmplDesignation,InstDesignationByInstLevel,ActiveInstEmplDesignationByType,HrDesignationSetup,HrEmplTypeInfo) {

        $scope.cmsSubAssigns= [];
        InstituteByLogin.query({},function(result){
            $scope.logInInstitute = result;
            $scope.levelId = result.instLevel.id;
            $scope.levelName = '';
            HrDesignationSetup.query({page: $scope.page, size: 2000},function(result){
                $scope.designationSetups=result;
            });
            HrEmplTypeInfo.query({},function(result){

                $scope.employeeTypes=result;
            });


            $scope.instLevels = [];
            InstLevel.query(function(allInst){
                angular.forEach(allInst, function(value, key) {
                    if(parseInt($scope.levelId) == parseInt(value.id))
                    {
                        $scope.levelName = value.name;
                    }
                });

                var splitNames = $scope.levelName.split('&');

                angular.forEach(allInst, function(value, key) {
                    angular.forEach(splitNames, function(spname, spkey) {
                        if(spname.trim() == value.name.trim())
                        {
                            $scope.instLevels.push(value);
                        }
                    });

                });

            });
            console.log(result);
        });




        /*InstGenInfosByCurrentUser.query({},function(result) {
            $scope.inst_levels = result;
            $scope.levelId = $scope.inst_levels[0].INST_LEVEL_ID;
            $scope.levelName = '';

            $scope.instLevels = [];
            InstLevel.query(function(allInst){
                angular.forEach(allInst, function(value, key) {
                     if(parseInt($scope.levelId) == parseInt(value.id))
                      {
                        $scope.levelName = value.name;
                      }
                });

                var splitNames = $scope.levelName.split('&');

                angular.forEach(allInst, function(value, key) {
                    angular.forEach(splitNames, function(spname, spkey) {
                         if(spname.trim() == value.name.trim())
                          {
                            $scope.instLevels.push(value);
                          }
                    });

                });

            });

        });*/


        $scope.isRequired = false;

        $scope.updateFormFields = function()
        {
            if($scope.instEmployee.category == 'Teacher')
            {
                $scope.isRequired = true;
                console.log('TRUE');
                console.log('>>>>>>>>>>');
                console.log($('#label-level').html());
                if($('#label-level').html().indexOf('*') == -1)
                    $('#label-level').append('<strong style="color:red"> * </strong>');
                if($('#label-courseTech').html().indexOf('*') == -1)
                    $('#label-courseTech').append('<strong style="color:red"> * </strong>');
                if($('#label-subject').html().indexOf('*') == -1)
                    $('#label-subject').append('<strong style="color:red"> * </strong>');
                if($('#label-email').html().indexOf('*') == -1)
                    $('#label-email').append('<strong style="color:red"> * </strong>');
                if($('#label-contactNo').html().indexOf('*') == -1)
                    $('#label-contactNo').append('<strong style="color:red"> * </strong>');
            }
            else {
                $scope.isRequired = false;
                $scope.instEmplDesignation = ActiveInstEmplDesignationByType.query({type: 'Employee'});
                console.log('FALSE');
                console.log('<<<<<<<<<<<<<<<<');
                console.log($('#label-level').html());
                $('#label-level').html($('#label-level').html().replace("*", ""));
                $('#label-courseTech').html($('#label-courseTech').html().replace("*", ""));
                $('#label-subject').html($('#label-subject').html().replace("*", ""));
                $('#label-email').html($('#label-email').html().replace("*", ""));
                $('#label-contactNo').html($('#label-contactNo').html().replace("*", ""));
            }
            console.log($scope.instEmployee.category);
        };

        IisTradesOfCurrentInst.query({}, function(result){
           $scope.cmsTrades = result;
        });
        $scope.courseSubsGlobal = CourseSub.query();
        /*$scope.instEmplDesignation = InstEmplDesignation.query();*/
        /*$scope.instEmplDesignation = ActiveInstEmplDesignation.query();*/
        $scope.coursetechs = CourseTech.query();
        $scope.coursesubs = [];
        $scope.selectedTech = "Select a Technology";
        $scope.SelectedSubs = "Select a Subject";
        /*$scope.email = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;*/
        //$scope.email =/^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;
       /* $scope.email =/^[a-z0-9!#$%&'*+/=?^_`{|}~.-]+@[a-z]+\.[a-z.]{2,5}$/;*/

        $scope.CourseTechChange = function(data){
              $scope.coursesubs = [];
              angular.forEach($scope.courseSubsGlobal,function(subjectData){
                   if(data.id == subjectData.courseTech.id){
                         $scope.coursesubs.push(subjectData);
                   }
              });
        };

        $scope.CourseTradeChange = function(data){
            console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>>');
            console.log("trade id");
            console.log(data.id);
           CmsSubAssignByTrade.query({id: data.id}, function(result){

               $scope.cmsSubAssigns = result;
               console.log("subject by trade");
               console.log( $scope.cmsSubAssigns);
           });
        };


        var onSaveSuccess = function (result) {
             $scope.instEmployee.applyDate=new Date();
            $scope.$emit('stepApp:instEmployeeUpdate', result);
            $scope.isSaving = false;
            console.log(result);
            $rootScope.setSuccessMessage('Employee Added Successfully.');
           // $state.go('instituteInfo.PendingEmployeeList',{},{reload: true});
            if(result.category === 'Staff'){
                $state.go('instituteInfo.staffList',{},{reload: true});
            }else{
                $state.go('instituteInfo.employeeList',{},{reload: true});
            }

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            console.log(result);
        };

        $scope.save = function () {
            $scope.isSaving = true;
            $scope.instEmployee.status=2;
            $scope.instEmployee.level= $scope.instEmployee.instLevel.name;
            console.log("JOB QUOTA HERE");
            // console.log(instEmployee.jobQuota);
            //console.log("Job placement officer :"+$scope.instEmployee.isJPAdmin);
            if(typeof $scope.instEmployee.isJPAdmin == "undefined" || $scope.instEmployee.isJPAdmin == null){
                $scope.instEmployee.isJPAdmin = false;
            }
            //console.log("Job placement officer2 :"+$scope.instEmployee.isJPAdmin);
            $scope.instEmployee.institute = $scope.logInInstitute;
            console.log($scope.logInInstitute);
            console.log($scope.instEmployee);

            InstEmployee.save($scope.instEmployee, onSaveSuccess, onSaveError);
            $rootScope.setSuccessMessage('stepApp.instEmployee.created');
        };

        $scope.clear = function() {
             $scope.instEmployee = null;
        };


            $scope.setQuotaCert = function ($file, instEmployee) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function() {
                            instEmployee.quotaCert = base64Data;
                            instEmployee.quotaCertContentType = $file.type;
                            instEmployee.quotaCertName = $file.name;
                            var blob = b64toBlob(instEmployee.quotaCert, instEmployee.quotaCertContentType);
                            $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
                        });
                    };
                }
            };

        $scope.setDesignation = function(instLevel) {
            console.log(instLevel);
            $scope.instLevel = instLevel;
            if($scope.instEmployee.category == 'Teacher') {
                $scope.instEmplDesignation = InstDesignationByInstLevel.query({id: instLevel.id});
            }
        };

}]);
