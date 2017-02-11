'use strict';

angular.module('stepApp')
    .controller('PgmsAppFamilyAttachDetailController',
    ['$scope', '$rootScope', '$sce', '$stateParams', 'entity', 'PgmsAppFamilyAttach', 'PgmsRetirmntAttachInfo', 'DataUtils',
    function ($scope, $rootScope, $sce, $stateParams, entity, PgmsAppFamilyAttach, PgmsRetirmntAttachInfo, DataUtils) {
        $scope.pgmsAppFamilyAttach = entity;
        $scope.load = function (id) {
            PgmsAppFamilyAttach.get({id: id}, function(result) {
                $scope.pgmsAppFamilyAttach = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pgmsAppFamilyAttachUpdate', function(event, result) {
            $scope.pgmsAppFamilyAttach = result;
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
