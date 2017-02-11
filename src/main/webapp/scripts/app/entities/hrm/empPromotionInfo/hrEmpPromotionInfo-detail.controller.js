'use strict';

angular.module('stepApp')
    .controller('HrEmpPromotionInfoDetailController',
    ['$scope', '$rootScope', '$sce', '$stateParams', 'DataUtils', 'entity', 'HrEmpPromotionInfo', 'HrEmployeeInfo', 'MiscTypeSetup',
    function ($scope, $rootScope, $sce, $stateParams, DataUtils, entity, HrEmpPromotionInfo, HrEmployeeInfo, MiscTypeSetup) {
        $scope.hrEmpPromotionInfo = entity;
        $scope.load = function (id) {
            HrEmpPromotionInfo.get({id: id}, function(result) {
                $scope.hrEmpPromotionInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpPromotionInfoUpdate', function(event, result) {
            $scope.hrEmpPromotionInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;

        $scope.previewImage = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.goDoc, modelInfo.goDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.goDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Promotion Document : "+modelInfo.instituteTo;
            $rootScope.showPreviewModal();
        };
    }]);
