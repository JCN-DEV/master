'use strict';

angular.module('stepApp').controller('CmsSubAssignDialogController',
['$scope', '$stateParams', '$state', 'entity', 'CmsSubAssign', 'CmsCurriculum', 'CmsTrade', 'CmsSemester','CmsSyllabus','CmsSubject','CmsTradesByCurriculum',
        function($scope, $stateParams, $state, entity, CmsSubAssign, CmsCurriculum, CmsTrade, CmsSemester,CmsSyllabus,CmsSubject,CmsTradesByCurriculum) {

        $scope.cmsSubAssign = entity;
        $scope.cmsSubAssign.status=true;
        $scope.cmscurriculums = CmsCurriculum.query();
        //$scope.cmstrades = CmsTrade.query({page: 0, size: 100});
        $scope.cmssemesters = CmsSemester.query();
        $scope.cmssyllabuss = CmsSyllabus.query();
        $scope.cmssubjects = CmsSubject.query();

        $scope.load = function(id) {

            CmsSubAssign.get({id : id}, function(result) {
                $scope.cmsSubAssign = result;
            });

        };

        $scope.setCourse = function() {
            $scope.cmstrades = CmsTradesByCurriculum.query({id:  $scope.cmsSubAssign.cmsCurriculum.id ,page: 0, size: 100});

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


