'use strict';

angular.module('stepApp')
    .controller('TrainingSummaryController',
    ['$scope', '$state', 'TrainingSummary', 'TrainingSummarySearch', 'ParseLinks',
    function ($scope, $state, TrainingSummary, TrainingSummarySearch, ParseLinks) {
        $scope.trainingSummarys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TrainingSummary.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.trainingSummarys = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TrainingSummary.get({id: id}, function(result) {
                $scope.trainingSummary = result;
                $('#deleteTrainingSummaryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TrainingSummary.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTrainingSummaryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            TrainingSummarySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.trainingSummarys = result;
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
            $scope.trainingSummary = {
                title: null,
                topicsCovered: null,
                institute: null,
                country: null,
                location: null,
                startedDate: null,
                endedDate: null,
                id: null
            };
        };

		//==================|
		// bulk actions		|
		//==================|
        $scope.areAllTrainingSummarysSelected = false;

        $scope.updateTrainingSummarysSelection = function (trainingSummaryArray, selectionValue) {
            for (var i = 0; i < trainingSummaryArray.length; i++)
            {
                trainingSummaryArray[i].isSelected = selectionValue;
            }
        };

        $scope.export = function () {
            for (var i = 0; i < $scope.trainingSummarys.length; i++) {
                var trainingSummary = $scope.trainingSummarys[i];
                console.info('TODO: export selected item with id: ' + trainingSummary.id);
            }
        };

        $scope.import = function () {
            for (var i = 0; i < $scope.trainingSummarys.length; i++) {
                var trainingSummary = $scope.trainingSummarys[i];
                console.info('TODO: import selected item with id: ' + trainingSummary.id);
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.trainingSummarys.length; i++){
                var trainingSummary = $scope.trainingSummarys[i];
                if(trainingSummary.isSelected){
                    TrainingSummary.delete(trainingSummary);
                }
            }
            $state.go('trainingSummary', null, {reload: true});
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.trainingSummarys.length; i++){
                var trainingSummary = $scope.trainingSummarys[i];
                if(trainingSummary.isSelected){
                    TrainingSummary.update(trainingSummary);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            TrainingSummary.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
				$scope.trainingSummarys = result;
                $scope.total = headers('x-total-count');
            });
        };


    }]);
