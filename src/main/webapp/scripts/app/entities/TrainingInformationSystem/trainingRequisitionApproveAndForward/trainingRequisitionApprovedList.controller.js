'use strict';

angular.module('stepApp')
    .controller('TrainingRequisitionApprovedListController',
        function ($scope,$stateParams, TrainingRequisitionForm,ParseLinks,TrainingRequisitionByApproveStatus) {
        $scope.trainingRequisitionForms = [];
        $scope.page = 0;
        $scope.statusFind = '';
        $scope.loadAll = function() {
            TrainingRequisitionByApproveStatus.query({approveStatus:$stateParams.status}, function (result, headers) {
                $scope.trainingRequisitionForms = result;
                if($stateParams.status == 3){
                    $scope.statusFind = 3;
                }else if ($stateParams.status == 0){
                    $scope.statusFind = 0;
                }else if ($stateParams.status == -1){
                    $scope.statusFind = -1;
                }else{
                    $scope.statusFind = '';
                }
            });
        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.search = function () {
            TrainingRequisitionFormSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.trainingRequisitionForms = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };
        $scope.clear = function () {
            $scope.trainingRequisitionForm = {
                requisitionCode: null,
                trainingType: null,
                session: null,
                applyDate: null,
                reason: null,
                fileName: null,
                file: null,
                fileContentType: null,
                fileContentName: null,
                applyBy: null,
                instituteId: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };

        $scope.abbreviate = function (text) {
            if (!angular.isString(text)) {
                return '';
            }
            if (text.length < 30) {
                return text;
            }
            return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
        };
        //
        //$scope.byteSize = function (base64String) {
        //    if (!angular.isString(base64String)) {
        //        return '';
        //    }
        //    function endsWith(suffix, str) {
        //        return str.indexOf(suffix, str.length - suffix.length) !== -1;
        //    }
        //    function paddingSize(base64String) {
        //        if (endsWith('==', base64String)) {
        //            return 2;
        //        }
        //        if (endsWith('=', base64String)) {
        //            return 1;
        //        }
        //        return 0;
        //    }
        //    function size(base64String) {
        //        return base64String.length / 4 * 3 - paddingSize(base64String);
        //    }
        //    function formatAsBytes(size) {
        //        return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
        //    }
        //
        //    return formatAsBytes(size(base64String));
        //};
    });
