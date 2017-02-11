'use strict';

angular.module('stepApp')
    .controller('PgmsAppFamilyPensionDetailController',
    ['$scope', '$rootScope', '$sce', '$stateParams', 'entity', 'PgmsAppFamilyPension', 'HrEmployeeInfo', 'PgmsAppFamilyAttachByPenId','PgmsAppFamilyAttach', 'PgmsRetirmntAttachInfo','DataUtils',
    function ($scope, $rootScope, $sce , $stateParams, entity, PgmsAppFamilyPension, HrEmployeeInfo, PgmsAppFamilyAttachByPenId, PgmsAppFamilyAttach, PgmsRetirmntAttachInfo, DataUtils) {
        $scope.pgmsAppFamilyPension = entity;
         $scope.pgmsAppFamilyAttachList = [];
         var appPenId = $stateParams.id;

        $scope.loadAll = function()
        {
            PgmsAppFamilyAttachByPenId.get({familyPensionId:appPenId}, function(result) {
                    $scope.pgmsAppFamilyAttachList = result;
                    console.log("Len: "+$scope.pgmsAppFamilyAttachList.length);
            });
        };
        $scope.loadAll();
        $scope.load = function (id) {
            PgmsAppFamilyPension.get({id: id}, function(result) {
                $scope.pgmsAppFamilyPension = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pgmsAppFamilyPensionUpdate', function(event, result) {
            $scope.pgmsAppFamilyPension = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.previewDoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.attachment, modelInfo.attachmentContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.attachmentContentType;
            $rootScope.viewerObject.pageTitle = "Preview of Attachment Order Document";
            $rootScope.showPreviewModal();
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

    }]);
