'use strict';

angular.module('stepApp')
    .controller('JobPostingInfoDetailController',
    ['$scope', '$rootScope','$sce', 'DataUtils', 'entity', 'JobPostingInfo', 'JobType',
    function ($scope, $rootScope,$sce, DataUtils, entity, JobPostingInfo, JobType) {
        $scope.jobPostingInfo = entity;
        $scope.load = function (id) {
            JobPostingInfo.get({id: id}, function(result) {
                $scope.jobPostingInfo = result;
            });
        };

        /*$scope.previewImage = function (jobPostId, upload, jobFileName)
        {
            console.log(jobPostId)
            console.log(jobTitle)
            console.log(jobFileName)
            var blob = $rootScope.b64toBlob(upload, jobPostId);
            $rootScope.viewerObject.upload = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.content = jobPostId;
            $rootScope.viewerObject.contentType = jobTitle;
            $rootScope.viewerObject.pageTitle = jobFileName;
            // call the modal
            $rootScope.showFilePreviewModal();
        };*/

        $scope.previewImage = function (content, contentType, name)
        {
            console.log(content)
            console.log(contentType)
            console.log(name)
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = contentType;
            $rootScope.viewerObject.pageTitle = name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };

        var unsubscribe = $rootScope.$on('stepApp:jobPostingInfoUpdate', function(event, result) {
            $scope.jobPostingInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
