'use strict';

angular.module('stepApp')
    .controller('HrEmpServiceHistoryDetailController',
    ['$scope', '$rootScope', '$sce', '$stateParams', 'DataUtils', 'entity', 'HrEmpServiceHistory', 'HrEmployeeInfo',
    function ($scope, $rootScope, $sce, $stateParams, DataUtils, entity, HrEmpServiceHistory, HrEmployeeInfo) {
        $scope.hrEmpServiceHistory = entity;
        $scope.load = function (id) {
            HrEmpServiceHistory.get({id: id}, function(result) {
                $scope.hrEmpServiceHistory = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpServiceHistoryUpdate', function(event, result) {
            $scope.hrEmpServiceHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;

        $scope.previewImage = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.goDoc, modelInfo.goDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.goDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Service History Document";
            $rootScope.showPreviewModal();
        };
    }]);
