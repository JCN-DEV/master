'use strict';

angular.module('stepApp')
    .controller('HrEmpForeignTourInfoDetailController',
    ['$scope', '$rootScope', '$sce', '$stateParams', 'DataUtils', 'entity', 'HrEmpForeignTourInfo', 'HrEmployeeInfo', 'MiscTypeSetup',
    function ($scope, $rootScope, $sce, $stateParams, DataUtils, entity, HrEmpForeignTourInfo, HrEmployeeInfo, MiscTypeSetup) {
        $scope.hrEmpForeignTourInfo = entity;
        $scope.load = function (id) {
            HrEmpForeignTourInfo.get({id: id}, function(result) {
                $scope.hrEmpForeignTourInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpForeignTourInfoUpdate', function(event, result) {
            $scope.hrEmpForeignTourInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;

        $scope.previewImage = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.goDoc, modelInfo.goDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.goDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Foreign Tour Document : "+modelInfo.countryName;
            $rootScope.showPreviewModal();
        };
    }]);
