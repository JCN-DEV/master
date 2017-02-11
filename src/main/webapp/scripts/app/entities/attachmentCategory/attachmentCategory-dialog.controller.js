'use strict';

angular.module('stepApp').controller('AttachmentCategoryDialogController',
['$scope', '$stateParams', 'entity', 'AttachmentCategory', 'Module', '$state','InstDesignationByInstLevel', 'InstLevel','HrDesignationSetup',
function($scope, $stateParams, entity, AttachmentCategory, Module, $state,InstDesignationByInstLevel, InstLevel,HrDesignationSetup) {

        $scope.attachmentCategory = entity;
        $scope.instLevel = {};
        $scope.modules = Module.query();
        $scope.instLevels = InstLevel.query();
        //$scope.load = function(id) {

        HrDesignationSetup.query({},function(result){
            $scope.designationSetups=result;
        });

        if($stateParams.id != null){
            AttachmentCategory.get({id : $stateParams.id}, function(result) {
                $scope.attachmentCategory = result;

            });
        }

        //AttachmentCategory.get({id : $stateParams.id}, function(result) {
        //    $scope.attachmentCategory = result;
        //});






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

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:attachmentCategoryUpdate', result);
            $state.go('attachmentCategory');
            //$modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.attachmentCategory.id != null) {
                if($scope.attachmentCategory.status !=null){
                    $scope.attachmentCategory.status = true;
                }
                AttachmentCategory.update($scope.attachmentCategory, onSaveFinished);
            } else {
                if($scope.attachmentCategory.status !=null){
                    $scope.attachmentCategory.status = true;
                }
                AttachmentCategory.save($scope.attachmentCategory, onSaveFinished);
            }
        };

        //$scope.setDesignation = function(instLevel) {
        //    console.log(instLevel);
        //    $scope.instLevel = instLevel;
        //    $scope.instEmplDesignations = InstDesignationByInstLevel.query({id: instLevel.id});
        //};
}]);
