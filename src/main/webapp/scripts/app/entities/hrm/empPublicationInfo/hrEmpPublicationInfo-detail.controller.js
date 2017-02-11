'use strict';

angular.module('stepApp')
    .controller('HrEmpPublicationInfoDetailController',
    ['$scope', '$rootScope','$sce','$stateParams', 'DataUtils', 'entity','HrEmpPublicationInfo', 'HrEmployeeInfo',
    function ($scope, $rootScope,$sce, $stateParams, DataUtils, entity, HrEmpPublicationInfo, HrEmployeeInfo) {
        $scope.hrEmpPublicationInfo = entity;
        $scope.load = function (id) {
            HrEmpPublicationInfo.get({id: id}, function(result) {
                $scope.hrEmpPublicationInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpPublicationInfoUpdate', function(event, result) {
            $scope.hrEmpPublicationInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;

        $scope.previewImage = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.publicationDoc, modelInfo.publicationDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.publicationDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Publication Document";
            $rootScope.showPreviewModal();
        };
    }]);
