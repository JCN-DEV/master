'use strict';

angular.module('stepApp').controller('CmsSubAssignDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'CmsSubAssign', 'CmsCurriculum', 'CmsTrade', 'CmsSemester', 'CmsSyllabus', 'CmsSubject','FindActivecmsCurriculums','FindActivecmstrades','CmsTradesByCurriculum',
        function($scope, $stateParams, $state, entity, CmsSubAssign, CmsCurriculum, CmsTrade, CmsSemester,CmsSyllabus,CmsSubject,FindActivecmsCurriculums,FindActivecmstrades,CmsTradesByCurriculum) {

        $scope.cmsSubAssign = entity;
        $scope.cmsSubAssign.status=true;
        $scope.cmscurriculums = FindActivecmsCurriculums.query({size:100});

        $scope.cmstrades = FindActivecmstrades.query({size:100});
        $scope.cmssemesters = CmsSemester.query({size:100});
        $scope.cmssyllabuss = CmsSyllabus.query({size:100});
        $scope.cmssubjects = CmsSubject.query({size:100});

console.log("trades");
console.log($scope.cmstrades);
        $scope.load = function(id) {

            CmsSubAssign.get({id : id}, function(result) {
                $scope.cmsSubAssign = result;
            });

        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsSubAssignUpdate', result);
            /*$modalInstance.close(result);*/
            $scope.isSaving = false;
            $state.go('courseInfo.subAssign',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

            $scope.filterActiveSyllabus = function () {
                return function (item) {
                    if (item.status == true)
                    {
                        return true;
                    }
                    return false;
                };
            };

        /*$scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsSubAssign.id != null) {
                CmsSubAssign.update($scope.cmsSubAssign, onSaveSuccess, onSaveError);
            } else {
                CmsSubAssign.save($scope.cmsSubAssign, onSaveSuccess, onSaveError);
            }
        };*/

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.cmsSubAssign.id != null) {
                    CmsSubAssign.update($scope.cmsSubAssign, onSaveSuccess, onSaveError);
                } else {
                    CmsSubAssign.save($scope.cmsSubAssign, onSaveSuccess, onSaveError);
                }


            };

                    $scope.lions = false;
                    $scope.cranes = false;
                    $scope.dSemester = true;

              $scope.enableCodeAndName = function (curriculum) {
                       // $scope.dSemester = true;

                                   CmsTradesByCurriculum.query({id:curriculum.id}, function(result) {
                                   console.log("curriculum id");
                                   console.log(curriculum.id);
                                       $scope.cmsTradesActive = result;
                                   });
                        if ($scope.cmsSubAssign.cmsCurriculum.duration_type == 'Semester')
                            $scope.dSemester = false;
                        else
                            $scope.dSemester = true;


                    };




        $scope.clear = function() {
            $scope.cmsSubAssign=null;
        };
       /* $scope.AssetValueChange = function(CodeOfAsset){

                angular.forEach($scope.assetrecords, function(code){

                    if(CodeOfAsset == code.recordCode){
                        $scope.assetrecords.assetName = code.assetName;
                        $scope.assetrecords.assetStatus = code.status;
                    }

                })
                console.log($scope.assetrecords.assetStatus);
            };*/
         /* $scope.subjectf = function(sub){

                angular.forEach($scope.courseSubjects, function(input){

                    if(sub == input.code){
                        $scope.courseSubjects.name = input.name;
                       // $scope.courseSubjects.code = input.code;

                    }

                })
          };*/


}]);

/*
'use strict';

angular.module('stepApp').controller('CmsSubAssignDialogController',

        function($scope, $stateParams, $state, entity, CmsSubAssign, CmsCurriculum, CmsTrade, CmsSemester,CmsSyllabus,CmsSubject) {

        $scope.cmsSubAssign = entity;
        $scope.cmsSubAssign.status=true;
        $scope.cmscurriculums = CmsCurriculum.query({size:100});
        $scope.cmstrades = CmsTrade.query();
        $scope.cmssemesters = CmsSemester.query();
        $scope.cmssyllabuss = CmsSyllabus.query();
        $scope.cmssubjects = CmsSubject.query();

        $scope.load = function(id) {

            CmsSubAssign.get({id : id}, function(result) {
                $scope.cmsSubAssign = result;
            });

        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsSubAssignUpdate', result);
            */
/*$modalInstance.close(result);*//*

            $scope.isSaving = false;
            $state.go('courseInfo.subAssign',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

            $scope.filterActiveSyllabus = function () {
                return function (item) {
                    if (item.status == true)
                    {
                        return true;
                    }
                    return false;
                };
            };

        */
/*$scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsSubAssign.id != null) {
                CmsSubAssign.update($scope.cmsSubAssign, onSaveSuccess, onSaveError);
            } else {
                CmsSubAssign.save($scope.cmsSubAssign, onSaveSuccess, onSaveError);
            }
        };*//*


            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.cmsSubAssign.id != null) {
                    CmsSubAssign.update($scope.cmsSubAssign, onSaveSuccess, onSaveError);
                } else {
                    CmsSubAssign.save($scope.cmsSubAssign, onSaveSuccess, onSaveError);
                }


            };

            $scope.dSemester = false;
             $scope.lions = false;
             $scope.cranes = false;


            $scope.enableCodeAndName = function () {
                        $scope.dSemester = true;
                        if ($scope.cmsSubAssign.cmsCurriculum.duration_type = 'Semester' )
                            $scope.dSemester = false;
                        else
                            $scope.dSemester = true;
                    };

 */
/*$scope.enableCodeAndName = function () {
            $scope.dSemester = true;
            //if ($scope.cmsSemester.cmsCurriculum.code.toLowerCase() == 'diploma01')
            if ($scope.cmsSemester.cmsCurriculum.duration_type == 'Semester')
                $scope.dSemester = false;
            else
                $scope.dSemester = true;
        };*//*



        $scope.clear = function() {
            $scope.cmsSubAssign=null;
        };
       */
/* $scope.AssetValueChange = function(CodeOfAsset){

                angular.forEach($scope.assetrecords, function(code){

                    if(CodeOfAsset == code.recordCode){
                        $scope.assetrecords.assetName = code.assetName;
                        $scope.assetrecords.assetStatus = code.status;
                    }

                })
                console.log($scope.assetrecords.assetStatus);
            };*//*

         */
/* $scope.subjectf = function(sub){

                angular.forEach($scope.courseSubjects, function(input){

                    if(sub == input.code){
                        $scope.courseSubjects.name = input.name;
                       // $scope.courseSubjects.code = input.code;

                    }

                })
          };*//*



});


*/
