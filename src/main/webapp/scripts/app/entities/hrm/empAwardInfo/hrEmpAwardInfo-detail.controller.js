'use strict';

angular.module('stepApp')
    .controller('HrEmpAwardInfoDetailController',
    ['$scope', '$rootScope', '$sce', '$stateParams', 'DataUtils', 'entity', 'HrEmpAwardInfo', 'HrEmployeeInfo',
    function ($scope, $rootScope, $sce, $stateParams, DataUtils, entity, HrEmpAwardInfo, HrEmployeeInfo) {
        $scope.hrEmpAwardInfo = entity;
        $scope.load = function (id) {
            HrEmpAwardInfo.get({id: id}, function(result) {
                $scope.hrEmpAwardInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpAwardInfoUpdate', function(event, result) {
            $scope.hrEmpAwardInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;

        $scope.previewGODoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.goOrderDoc, modelInfo.goOrderDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.goOrderDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Award GO Order Document";
            $rootScope.showPreviewModal();
        };

        $scope.previewCertDoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.certDoc, modelInfo.certDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.certDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Award Certificate Document";
            $rootScope.showPreviewModal();
        };

    }]);
