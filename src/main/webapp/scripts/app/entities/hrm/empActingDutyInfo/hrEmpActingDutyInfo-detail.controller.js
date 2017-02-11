'use strict';

angular.module('stepApp')
    .controller('HrEmpActingDutyInfoDetailController',
    ['$scope', '$rootScope', '$sce', '$stateParams', 'DataUtils', 'entity','HrEmpActingDutyInfo', 'HrEmployeeInfo',
    function ($scope, $rootScope, $sce, $stateParams, DataUtils, entity, HrEmpActingDutyInfo, HrEmployeeInfo) {
        $scope.hrEmpActingDutyInfo = entity;
        $scope.load = function (id) {
            HrEmpActingDutyInfo.get({id: id}, function(result) {
                $scope.hrEmpActingDutyInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrEmpActingDutyInfoUpdate', function(event, result) {
            $scope.hrEmpActingDutyInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;

        $scope.previewImage = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.goDoc, modelInfo.goDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.goDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Acting Duty Document - "+modelInfo.toInstitution;
            $rootScope.showPreviewModal();
        };
    }]);
