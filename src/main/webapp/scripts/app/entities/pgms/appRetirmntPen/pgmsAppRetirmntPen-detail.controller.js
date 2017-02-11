'use strict';

angular.module('stepApp')
    .controller('PgmsAppRetirmntPenDetailController', function ($scope, $rootScope, $sce, $stateParams, entity, PgmsAppRetirmntPen, HrEmployeeInfo,PgmsAppRetirmntNmine, RetirementNomineeInfosByPenId, PgmsAppRetirementAttachByPenId, DataUtils) {
        $scope.pgmsAppRetirmntPen = entity;
        $scope.pgmsHrNomineeList = [];
        $scope.pgmsAppRetirementAttachList = [];

        $scope.loadAllDataView = function ()
        {
           // RetirementNomineeInfosByPenId.get({penId:$stateParams.id},function(result) {
              $scope.pgmsHrNomineeList = RetirementNomineeInfosByPenId.get({penId:$stateParams.id});
           // });
           //    $scope.pgmsAppRetirementAttachList = PgmsAppRetirementAttachByPenId.get({retirementPenId:$stateParams.id});

           PgmsAppRetirementAttachByPenId.get({retirementPenId:$stateParams.id},function(result) {

                 $scope.pgmsAppRetirementAttachList = result;
                // console.log("Nominee Length :"+$scope.totalList);
                 console.log("Nominee List :"+JSON.stringify($scope.pgmsAppRetirementAttachList));
           });
        };
        $scope.loadAllDataView();

        $scope.load = function (id) {
            PgmsAppRetirmntPen.get({id: id}, function(result) {
                $scope.pgmsAppRetirmntPen = result;
            });
        };

        var unsubscribe = $rootScope.$on('stepApp:pgmsAppRetirmntPenUpdate', function(event, result) {
            $scope.pgmsAppRetirmntPen = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.previewDoc = function (modelInfo)
        {
            console.log("Attachment: "+modelInfo.attachmentContentType);

            var blob = $rootScope.b64toBlob(modelInfo.attachment, modelInfo.attachmentContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.attachmentContentType;
            console.log("url: "+$rootScope.viewerObject.contentUrl);
            $rootScope.viewerObject.pageTitle = "Preview of Attachment Order Document";
            $rootScope.showPreviewModal();
        };

        $scope.abbreviate = DataUtils.abbreviate;
        $scope.byteSize = DataUtils.byteSize;


    });
