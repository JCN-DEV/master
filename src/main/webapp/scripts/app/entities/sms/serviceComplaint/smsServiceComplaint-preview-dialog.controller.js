'use strict';

angular.module('stepApp')
	.controller('SmsServiceComplaintPreviewController',
	 ['$sce', '$location', '$stateParams', '$rootScope', '$scope', '$modalInstance', 'entity', 'SmsServiceComplaint',
	 function($sce, $location, $stateParams, $rootScope, $scope, $modalInstance, entity, SmsServiceComplaint) {

        $scope.content = "";
        $scope.smsServiceComplaint = entity;
        //console.log($scope.smsServiceComplaint);
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        SmsServiceComplaint.get({id: $stateParams.id}, function (result)
        {
            $scope.smsServiceComplaint = result;
            var contextPath = $location.absUrl().split('#')[0];
            //$scope.filePath = contextPath+"uploaded-resource/sms/"+$scope.smsServiceComplaint.complaintDocFileName;

            if ($scope.smsServiceComplaint.complaintDoc) {
                var blob = $rootScope.b64toBlob($scope.smsServiceComplaint.complaintDoc, $scope.smsServiceComplaint.complaintDocContentType);
                $scope.filePath = (window.URL || window.webkitURL).createObjectURL(blob);
                $scope.fileContentUrl = $sce.trustAsResourceUrl($scope.filePath);
            }

            console.log($scope.smsServiceComplaint.complaintDocContentType+", filePath: "+$scope.fileContentUrl);

        });
    }]);
