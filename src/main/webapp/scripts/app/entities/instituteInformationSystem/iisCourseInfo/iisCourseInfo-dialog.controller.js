'use strict';

angular.module('stepApp').controller('IisCourseInfoDialogController',
    ['$scope','$rootScope','$state','InstituteByLogin', '$stateParams', 'entity', 'IisCourseInfo', 'IisCurriculumInfo', 'CmsTrade', 'CmsSubject','FindCurriculumByInstId','CmsCurriculum','IisCurriculumsOfCurrentInstitute','CmsSubByCurriculum','CmsTradesByCurriculum','IisCourseInfoTemp',
        function($scope,$rootScope,$state,InstituteByLogin, $stateParams, entity, IisCourseInfo, IisCurriculumInfo, CmsTrade, CmsSubject,FindCurriculumByInstId,CmsCurriculum,IisCurriculumsOfCurrentInstitute,CmsSubByCurriculum,CmsTradesByCurriculum, IisCourseInfoTemp) {

        $scope.iisCourseInfo = {};
        $scope.FindCurriculumByInstId ={};
            IisCourseInfoTemp.get({id : $stateParams.id}, function(result) {
                $scope.iisCourseInfo = result;
                CmsTradesByCurriculum.query({id:result.cmsTrade.cmsCurriculum.id}, function(data){
                    $scope.cmstrades = data;

                });
            });
        $scope.iiscurriculuminfos = IisCurriculumInfo.query();
            console.log("course list");
            console.log($scope.iiscurriculuminfos);
       // $scope.cmstrades = CmsTrade.query();
        $scope.cmsCurriculums = IisCurriculumsOfCurrentInstitute.query();
            console.log("cur list");
            console.log($scope.cmsCurriculums);




        $scope.iisCourseInfo.mpoEnlisted=true;
         InstituteByLogin.query({},function(result){
                            $scope.logInInstitute = result;
                            console.log(result);
                        });


        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:iisCourseInfoUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('instituteInfo.iisCourseInfo',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
        //$scope.iisCourseInfo.iisCurriculumInfo =iisCurriculumInfo;
            $scope.isSaving = true;
           /* IisCurriculumInfo.get({id: $scope.iisCurriculumInfo.ID}, function (result){
            $scope.iisCourseInfo.iisCurriculumInfo =result;*/
            if ($scope.iisCourseInfo.id != null) {
                            IisCourseInfoTemp.update($scope.iisCourseInfo, onSaveSuccess, onSaveError);
                            $rootScope.setWarningMessage('stepApp.iisCourseInfo.updated');
                        } else {
                            $scope.iisCourseInfo.institute = $scope.logInInstitute;
                            IisCourseInfoTemp.save($scope.iisCourseInfo, onSaveSuccess, onSaveError);
                            $rootScope.setSuccessMessage('stepApp.iisCourseInfo.created');
                        }
           /* });*/

        };
        /*FindCurriculumByInstId.query({page: $scope.page, size: 20}, function(result, headers) {

                                $scope.findCurriculumByInstIds = result;

                                console.log("=======================Amanur Rahman=================");
                                console.log(result);
                                console.log("=======================Amanur Rahman=================");

                            });*/

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };

     $scope.setTrades = function() {
         CmsTradesByCurriculum.query({id:$scope.iisCurriculumInfo.cmsCurriculum.id}, function(result){

             console.log("cms trade");

             $scope.cmstrades = result;
             console.log($scope.cmstrades);
         });
     };


}]);
