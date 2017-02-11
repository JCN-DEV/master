'use strict';

angular.module('stepApp')
    .controller('TrainingRequisitionApproveAndForwardController', function ($scope, TrainingRequisitionApproveAndForward, TrainingRequisitionApproveAndForwardSearch, ParseLinks) {
        $scope.trainingRequisitionApproveAndForwards = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TrainingRequisitionApproveAndForward.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.trainingRequisitionApproveAndForwards = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TrainingRequisitionApproveAndForward.get({id: id}, function(result) {
                $scope.trainingRequisitionApproveAndForward = result;
                $('#deleteTrainingRequisitionApproveAndForwardConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TrainingRequisitionApproveAndForward.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTrainingRequisitionApproveAndForwardConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            TrainingRequisitionApproveAndForwardSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.trainingRequisitionApproveAndForwards = result;
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
            $scope.trainingRequisitionApproveAndForward = {
                approveStatus: null,
                approveByAuthority: null,
                forwardToAuthority: null,
                logComments: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
