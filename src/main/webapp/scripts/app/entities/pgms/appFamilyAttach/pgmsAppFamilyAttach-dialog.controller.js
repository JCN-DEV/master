'use strict';

angular.module('stepApp').controller('PgmsAppFamilyAttachDialogController',
    ['$rootScope','$sce','$scope', '$stateParams', '$state', 'entity', 'PgmsAppFamilyAttach', 'PgmsRetirmntAttachInfo','PgmsAppFamilyAttachByTypeAndPension','DataUtils',
        function($rootScope,$sce, $scope, $stateParams, $modalInstance, entity, PgmsAppFamilyAttach, PgmsRetirmntAttachInfo, PgmsAppFamilyAttachByTypeAndPension,DataUtils) {

        $scope.pgmsAppFamilyAttach = {};
        $scope.pgmsretirmntattachinfos = PgmsRetirmntAttachInfo.query();
        $scope.appFamilyPenId = 1;

        $scope.pgmsAppFamilyAttachList = [];
        $scope.load = function(id) {
            PgmsAppFamilyAttach.get({id : id}, function(result) {
                $scope.pgmsAppFamilyAttach = result;
            });
        };
        $scope.loadAll = function()
        {
            PgmsAppFamilyAttachByTypeAndPension.get({attacheType:'family',familyPensionId:$scope.appFamilyPenId},
                function(result) {
                            $scope.pgmsAppFamilyAttachList = result;
                            console.log("Len: "+$scope.pgmsAppFamilyAttachList.length);
                            console.log(JSON.stringify($scope.pgmsAppFamilyAttachList));
                });

        };
        $scope.loadAll();

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:pgmsAppFamilyAttachUpdate', result);
            $scope.isSaving = false;
            $scope.pgmsAppFamilyAttachList[$scope.selectedIndex].id=result.id;
        };
        var onSaveError = function (result) {
             $scope.isSaving = false;
        };

        $scope.saveAttachment = function (modelInfo, index)
        {
            $scope.selectedIndex = index;
            modelInfo.appFamilyPenId = $scope.appFamilyPenId;
            if (modelInfo.id != null)
            {
                PgmsAppFamilyAttach.update(modelInfo, onSaveSuccess, onSaveError);
            }
            else
            {
                PgmsAppFamilyAttach.save(modelInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };

       $scope.abbreviate = DataUtils.abbreviate;

       $scope.byteSize = DataUtils.byteSize;

       $scope.setAttachment = function ($file, pgmsAppFamilyAttach) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        pgmsAppFamilyAttach.attachment = base64Data;
                        pgmsAppFamilyAttach.attachmentContentType = $file.type;
                        pgmsAppFamilyAttach.attachDocName = $file.name;
                    });
                };
            }
        };


        $scope.previewDoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.attachment, modelInfo.attachmentContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.attachmentContentType;
            $rootScope.viewerObject.pageTitle = "Preview of Attachment Order Document";
            $rootScope.showPreviewModal();
        };

         $scope.delete = function (modelInfo) {
            $scope.pgmsAppFamilyAttach=modelInfo;
            $('#deletePgmsAppFamilyAttachConfirmation').modal('show');
         };

         $scope.confirmDelete = function (modelInfo)
         {
            PgmsAppFamilyAttach.delete({id: modelInfo.id},
                function () {
                    $('#deletePgmsAppFamilyAttachConfirmation').modal('hide');
                    $scope.clear(modelInfo);
                });

         };

         $scope.clear = function (modelInfo) {
            modelInfo.attachment= null;
            modelInfo.attachmentContentType= null;
            modelInfo.attachDocName= null;
            modelInfo.id= null;
         };
}]);
