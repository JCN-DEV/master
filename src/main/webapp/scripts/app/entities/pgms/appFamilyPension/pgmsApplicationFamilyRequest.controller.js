'use strict';

angular.module('stepApp')
    .controller('PgmsApplicationFamilyRequestController',
    ['$scope', '$state','$sce','$rootScope', '$timeout', 'DataUtils','PgmsAppFamilyPension','PgmsAppFamilyPending','PgmsAppFamilyAttachByTypeAndPen','Principal', 'DateUtils',
    function($scope, $state, $sce, $rootScope, $timeout, DataUtils, PgmsAppFamilyPension,  PgmsAppFamilyPending, PgmsAppFamilyAttachByTypeAndPen,Principal, DateUtils)
    {

        $scope.newRequestList = [];
        $scope.approvedList = [];
        $scope.rejectedList = [];
        $scope.pgmsAppFamilyAttachList = [];
        $scope.loadingInProgress = true;
        $scope.requestEntityCounter = 0;

        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                });
            });
        };

        $scope.loadAll = function()
        {
            $scope.requestEntityCounter = 1;
            $scope.newRequestList = [];
            PgmsAppFamilyPending.get({statusType:'Pending'},function(result) {
                $scope.newRequestList = result;
                //console.log("App Family Pending List:"+JSON.stringify($scope.newRequestList));
            });

            $scope.newRequestList.sort($scope.sortById);
            $scope.loadApprovedRejectList();
        };

        $scope.sortById = function(a,b){
            return b.id - a.id;
        };

        $scope.approvalViewAction = function ()
        {
            if($scope.approvalObj.isApproved)
                $scope.approvalObj.aprvStatus='Approved';
            else $scope.approvalObj.aprvStatus='Reject';
            $scope.approvalObj.aprvDate = DateUtils.convertLocaleDateToServer(new Date());
            $scope.approvalObj.aprvBy = $scope.loggedInUser.id;
            $scope.approvalAction($scope.approvalObj);
        };

        $scope.approvalActionDirect = function ()
        {
            if($scope.approvalObj.actionType == 'accept') {
                $scope.approvalObj.aprvStatus = 'Approved';
            }
            else {
                $scope.approvalObj.aprvStatus='Reject';
            }
            $scope.approvalObj.aprvDate = DateUtils.convertLocaleDateToServer(new Date());
            $scope.approvalObj.aprvBy = $scope.loggedInUser.id;

            $scope.approvalAction($scope.approvalObj);
        };

        $scope.approvalAction = function (requestObj){
            PgmsAppFamilyPension.update(requestObj, function(result)
            {
                $('#approveRejectConfirmation').modal('hide');
                $('#approveViewDetailForm').modal('hide');
                $scope.loadAll();
            });
        };

        $scope.loadApprovedRejectList = function ()
        {
            $scope.approvedList = [];
            $scope.rejectedList = [];
            PgmsAppFamilyPending.get({statusType:'Approved'},function(result) {
                  $scope.approvedList= result;
            },function(response)
              {
                  console.log("data from view load failed");
              }
            );
            PgmsAppFamilyPending.get({statusType:'Reject'},function(result) {
                              $scope.rejectedList= result;
            },function(response)
              {
                  console.log("data from view load failed");
              }
            );
        };

        $scope.searchText = "";
        $scope.updateSearchText = "";

        $scope.clearSearchText = function (source)
        {
            if(source=='request')
            {
                $timeout(function(){
                    $('#searchText').val('');
                    angular.element('#searchText').triggerHandler('change');
                });
            }
        };

        $scope.searchTextApp = "";

        $scope.clearSearchTextApp = function (source)
        {
            if(source=='approved')
            {
                $timeout(function(){
                    $('#searchTextApp').val('');
                    angular.element('#searchTextApp').triggerHandler('change');
                });
            }
        };

        $scope.searchTextRej = "";

        $scope.clearSearchTextRej = function (source)
        {
            if(source=='approved')
            {
                $timeout(function(){
                    $('#searchTextRej').val('');
                    angular.element('#searchTextRej').triggerHandler('change');
                });
            }
        };

        $scope.approvalViewDetail = function (dataObj)
        {
            $scope.approvalObj = dataObj;
            PgmsAppFamilyAttachByTypeAndPen.get({attacheType:'family',familyPensionId:dataObj.id},
            function(result) {
                $scope.pgmsAppFamilyAttachList = result;
               // console.log("Len: "+$scope.pgmsAppFamilyAttachList.length);
            });
            $('#approveViewDetailForm').modal('show');
        };

        $scope.approvalConfirmation = function (dataObj, actionType){
            $scope.approvalObj = dataObj;
            $scope.approvalObj.actionType = actionType;
            $('#approveRejectConfirmation').modal('show');
        };

        $scope.clear = function () {
            $scope.approvalObj = {
                entityId: null,
                entityName:null,
                requestFrom:null,
                requestSummary: null,
                requestDate:null,
                approveState: null,
                aprvStatus:null,
                logComments:null,
                actionType:'',
                entityObject: null
            };
        };

        $rootScope.$on('onEntityApprovalProcessCompleted', function(event, data)
        {
            $scope.loadAll();
        });


        $scope.loadEmployee = function () {
            HrEmployeeInfo.get({id: 'my'}, function (result) {
                $scope.hrEmployeeInfo = result;

            }, function (response) {
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
            })
        };

        $scope.sort = function(keyname, source){
            if(source=='request')
            {
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }

            else if(source=='approved')
            {
                $scope.sortKey3 = keyname;
                $scope.reverse3 = !$scope.reverse3;
            }
            else if(source=='rejected')
            {
                $scope.sortKey4 = keyname;
                $scope.reverse4 = !$scope.reverse4;
            }

        };
        $scope.abbreviate = DataUtils.abbreviate;
        $scope.byteSize = DataUtils.byteSize;
        $scope.previewDoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.attachment, modelInfo.attachmentContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.attachmentContentType;
            $rootScope.viewerObject.pageTitle = "Preview of Attachment Order Document";
            $rootScope.showPreviewModal();
        };

        $scope.loadAll();

    }])
;

