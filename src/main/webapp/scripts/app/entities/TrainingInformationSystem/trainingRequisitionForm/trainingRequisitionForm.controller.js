'use strict';

angular.module('stepApp')
    .controller('TrainingRequisitionFormController',
        ['$scope', 'TrainingRequisitionForm', 'TrainingRequisitionFormSearch','ParseLinks','Principal','TrainingRequisitionByCurrentUser',
        function ($scope, TrainingRequisitionForm, TrainingRequisitionFormSearch, ParseLinks,Principal,TrainingRequisitionByCurrentUser) {
        $scope.trainingRequisitionForms = [];
        $scope.page = 0;
        $scope.loadAll = function() {

            if(Principal.hasAnyAuthority(['ROLE_ADMIN'])) {
                TrainingRequisitionForm.query({page: $scope.page, size: 20}, function (result) {
                    //$scope.links = ParseLinks.parse(headers('link'));
                    $scope.trainingRequisitionForms = result;
                });
            };
            if(Principal.hasAnyAuthority(['ROLE_USER']) || Principal.hasAnyAuthority(['ROLE_INSTITUTE'])) {
                TrainingRequisitionByCurrentUser.query({page: $scope.page, size: 20}, function (result) {
                    //$scope.links = ParseLinks.parse(headers('link'));
                    $scope.trainingRequisitionForms = result;
                });
            };
            //if(Principal.hasAnyAuthority(['ROLE_INSTITUTE'])) {
            //    TrainingRequisitionForm.query({page: $scope.page, size: 20}, function (result, headers) {
            //        $scope.links = ParseLinks.parse(headers('link'));
            //        $scope.trainingRequisitionForms = result;
            //    });
            //};

        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TrainingRequisitionForm.get({id: id}, function(result) {
                $scope.trainingRequisitionForm = result;
                $('#deleteTrainingRequisitionFormConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TrainingRequisitionForm.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTrainingRequisitionFormConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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

        $scope.byteSize = function (base64String) {
            if (!angular.isString(base64String)) {
                return '';
            }
            function endsWith(suffix, str) {
                return str.indexOf(suffix, str.length - suffix.length) !== -1;
            }
            function paddingSize(base64String) {
                if (endsWith('==', base64String)) {
                    return 2;
                }
                if (endsWith('=', base64String)) {
                    return 1;
                }
                return 0;
            }
            function size(base64String) {
                return base64String.length / 4 * 3 - paddingSize(base64String);
            }
            function formatAsBytes(size) {
                return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
            }

            return formatAsBytes(size(base64String));
        };
    }]);
