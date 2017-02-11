'use strict';

angular.module('stepApp').controller('IisCurriculumInfoDialogController',
    ['$scope','$rootScope','$state', 'InstituteByLogin','$stateParams', 'entity', 'IisCurriculumInfo', 'CmsCurriculum','IisCurriculumInfoTemp','FindActivecmsCurriculums',
        function($scope,$rootScope,$state,InstituteByLogin, $stateParams, entity, IisCurriculumInfo, CmsCurriculum, IisCurriculumInfoTemp,FindActivecmsCurriculums) {
console.log("comes to myu comtroller");
        $scope.iisCurriculumInfo = entity;
        $scope.cmscurriculums = FindActivecmsCurriculums.query();


          InstituteByLogin.query({},function(result){
                    $scope.logInInstitute = result;
                    console.log(result);
                });
        $scope.load = function(id) {
            IisCurriculumInfoTemp.get({id : id}, function(result) {
                $scope.iisCurriculumInfo = result;
            });
        };


            $scope.deadlineValidation = function () {
                console.log("come to date vallidation");

                var d1 = Date.parse($scope.iisCurriculumInfo.lastDate);
                var d2 = Date.parse($scope.iisCurriculumInfo.firstDate);
                if (d1 <= d2) {

                    console.log("d1 less than d2");
                    $scope.dateError = true;
                }else {
                    console.log("d1 greater than d2");

                    $scope.dateError = false;
                }
            };

        $scope.iisCurriculumInfo.mpoEnlisted=true;
            var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:iisCurriculumInfoUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('instituteInfo.iisCurriculumInfo',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.iisCurriculumInfo.id != null) {
                IisCurriculumInfoTemp.update($scope.iisCurriculumInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.iisCurriculumInfo.updated');
            } else {
                console.log("comes to temp save");
                $scope.iisCurriculumInfo.institute = $scope.logInInstitute;
                IisCurriculumInfoTemp.save($scope.iisCurriculumInfo, onSaveSuccess, onSaveError);
                console.log($scope.iisCurriculumInfo);
                $rootScope.setSuccessMessage('stepApp.iisCurriculumInfo.created');
            }
        };



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


        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
