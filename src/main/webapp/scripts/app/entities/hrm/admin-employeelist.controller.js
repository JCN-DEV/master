'use strict';

angular.module('stepApp')
    .controller('HrEmployeeInfoListController',
    ['$rootScope', '$sce', '$scope', '$state','HrEmployeeInfo', 'HrEmployeeInfoSearch', 'ParseLinks', 'HrEmployeeInfoDetail', 'HrEmployeeInfoOnOff','HrEmployeeInfoByDateRange',
    function ($rootScope,$sce, $scope, $state, HrEmployeeInfo, HrEmployeeInfoSearch, ParseLinks, HrEmployeeInfoDetail, HrEmployeeInfoOnOff,HrEmployeeInfoByDateRange) {

        $scope.hrEmployeeInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function()
        {
            /*
            HrEmployeeInfo.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmployeeInfos = result;
            });
            */

            //console.log("pg: "+$scope.page+", pred:"+$scope.predicate);
            HrEmployeeInfoDetail.query({page: $scope.page, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmployeeInfos = result;

                angular.forEach($scope.hrEmployeeInfos,function(modelInfo)
                {
                    if (modelInfo.empPhoto) {
                        console.log("modelInfo:display");
                        var blob = $rootScope.b64toBlob(modelInfo.empPhoto, modelInfo.empPhotoContentType);
                        modelInfo.filePath = (window.URL || window.webkitURL).createObjectURL(blob);
                        modelInfo.fileContentUrl = $sce.trustAsResourceUrl(modelInfo.filePath);
                    }
                    //$scope.processImage(modelInfo);
                });
            });
        };

        $scope.miscParamDto = {};

        $scope.miscParamDto = {
            dataType: 'date',
            minDate:null,
            maxDate:null
        };

        $scope.searchEmployeeByDateRange = function (miscParamDto)
        {
            console.log(JSON.stringify(miscParamDto));
            if(miscParamDto.minDate!=null && miscParamDto.maxDate != null)
            {
                $scope.dateRangeSearchMsg = "";
                HrEmployeeInfoByDateRange.update(miscParamDto, function(result)
                {
                    //console.log(JSON.stringify(result));
                    $scope.hrEmployeeInfos = result;
                });
            }
            else
            {
                $scope.dateRangeSearchMsg = "Please enter dates";
            }

        };


        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.search = function () {
            HrEmployeeInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmployeeInfos = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        // Daterange filter
        // https://pterkildsen.com/2015/03/22/filtering-angularjs-items-based-on-start-and-end-date/
        $scope.dateRangeFilter = function (property, startDate, endDate) {
            return function (item) {
                if (item[property] === null) return false;

                var itemDate = moment(item[property]);
                var s = moment(startDate, "dd-MM-yyyy");
                var e = moment(endDate, "dd-MM-yyyy");

                if (itemDate >= s && itemDate <= e) return true;
                return false;
            }
        }

        $scope.calendar = {
            opened: {},
            dateFormat: 'dd-MM-yyyy',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        //$scope.byteSize = DataUtils.byteSize;

        $scope.processImage = function (modelInfo)
        {
            if (modelInfo.empPhoto) {
                var blob = $rootScope.b64toBlob(modelInfo.empPhoto, modelInfo.empPhotoContentType);
                modelInfo.filePath = (window.URL || window.webkitURL).createObjectURL(blob);
                modelInfo.fileContentUrl = $sce.trustAsResourceUrl(modelInfo.filePath);
            }
        };

        $scope.initApprovarDto = function(entityId, actionType, logComments,entityName, entityObject )
        {
            return {
                entityId: entityId,
                actionType:actionType,
                logComments:logComments,
                entityName:entityName
                //entityObject: entityObject
            };
        };

        $scope.approvalObj = {};
        $scope.activateDeactivateConfirmation = function (actionType, modelInfo)
        {
            $scope.approvalObj = $scope.initApprovarDto(modelInfo.id, actionType, '', modelInfo.fullName ,modelInfo);
            //console.log(JSON.stringify($scope.approvalObj));
            $('#employeeActivateDeactiveConfForm').modal('show');
        };

        $scope.activateDeactivateEmployee = function (requestObj)
        {
            //console.log(JSON.stringify(requestObj));
            HrEmployeeInfoOnOff.update(requestObj, function(result)
            {
                $('#employeeActivateDeactiveConfForm').modal('hide');
                $scope.loadAll();
            });

        };

        $scope.viewShortPDA = function (modelInfo)
        {
            //console.log(JSON.stringify(modelInfo));
            $scope.hrEmployeeInfo = modelInfo;
            //$scope.updatePrlDate();
            $('#employeeShortPDAModal').modal('show');
        };

        $scope.updatePrlDate = function()
        {
            //console.log(JSON.stringify($scope.hrEmployeeInfo));
            var prlYear = 61;
            if($scope.hrEmployeeInfo.quota=='Others' || $scope.hrEmployeeInfo.quota == 'Other' || $scope.hrEmployeeInfo.quota == 'others' || $scope.hrEmployeeInfo.quota == 'other')
            {
                prlYear = 59;
            }
            var brDt = new Date($scope.hrEmployeeInfo.birthDate);
            var prlDt = new Date(brDt.getFullYear() + prlYear, brDt.getMonth(), brDt.getDate());
            $scope.hrEmployeeInfo.prlDate = prlDt;
        };

        $scope.previewImage = function (modelInfo)
        {
            //console.log(JSON.stringify(modelInfo));
            //var blob = $rootScope.b64toBlob(modelInfo.empPhoto, modelInfo.empPhotoContentType);
            //$rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentUrl = modelInfo.fileContentUrl;
            $rootScope.viewerObject.contentType = modelInfo.empPhotoContentType;
            $rootScope.viewerObject.pageTitle = "Employee Photo : "+modelInfo.fullName;
            $rootScope.showPreviewModal();
        };

        $scope.clear = function()
        {
            $('#employeeShortPDAModal').modal('hide');
            $scope.hrEmployeeInfo = null;
        };

        $scope.clearConf = function () {
            $scope.approvalObj = {
                entityId: null,
                employeeId:null,
                entityName:null,
                requestFrom:null,
                requestSummary: null,
                requestDate:null,
                approveState: null,
                logStatus:null,
                logComments:null,
                actionType:'',
                entityObject: null
            };
        };

    }]);
