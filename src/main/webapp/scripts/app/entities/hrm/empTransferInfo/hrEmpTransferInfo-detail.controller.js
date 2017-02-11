'use strict';

angular.module('stepApp')
    .controller('HrEmpTransferInfoDetailController',
     ['$scope', '$rootScope','$sce', '$stateParams', 'DataUtils', 'entity', 'HrEmpTransferInfo', 'HrEmployeeInfo', 'MiscTypeSetup',
     function ($scope, $rootScope, $sce, $stateParams, DataUtils, entity, HrEmpTransferInfo, HrEmployeeInfo, MiscTypeSetup) {
        $scope.hrEmpTransferInfo = entity;
        $scope.load = function (id) {
            HrEmpTransferInfo.get({id: id}, function(result) {
                $scope.hrEmpTransferInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpTransferInfoUpdate', function(event, result) {
            $scope.hrEmpTransferInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;

         $scope.previewImage = function (modelInfo)
         {
             var blob = $rootScope.b64toBlob(modelInfo.goDoc, modelInfo.goDocContentType);
             $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
             $rootScope.viewerObject.contentType = modelInfo.goDocContentType;
             $rootScope.viewerObject.pageTitle = "Employee Transfer Document";
             $rootScope.showPreviewModal();
         };
    }]);
