'use strict';

angular.module('stepApp')
    .controller('MpoApplicationController',
    ['$scope','$state','MpoApplication','MpoApplicationSearch','ParseLinks',
    function ($scope, $state, MpoApplication, MpoApplicationSearch, ParseLinks) {
        $scope.mpoApplications = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MpoApplication.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.mpoApplications = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            MpoApplication.get({id: id}, function(result) {
                $scope.mpoApplication = result;
                $('#deleteMpoApplicationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MpoApplication.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMpoApplicationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            MpoApplicationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoApplications = result;
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
            $scope.mpoApplication = {
                name: null,
                date: null,
                id: null
            };
        };

		//==================|
		// bulk actions		|
		//==================|
        $scope.areAllMpoApplicationsSelected = false;

        $scope.updateMpoApplicationsSelection = function (mpoApplicationArray, selectionValue) {
            for (var i = 0; i < mpoApplicationArray.length; i++)
            {
                mpoApplicationArray[i].isSelected = selectionValue;
            }
        };

        $scope.export = function () {
            for (var i = 0; i < $scope.mpoApplications.length; i++) {
                var mpoApplication = $scope.mpoApplications[i];
                console.info('TODO: export selected item with id: ' + mpoApplication.id);
            }
        };

        $scope.import = function () {
            for (var i = 0; i < $scope.mpoApplications.length; i++) {
                var mpoApplication = $scope.mpoApplications[i];
                console.info('TODO: import selected item with id: ' + mpoApplication.id);
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.mpoApplications.length; i++){
                var mpoApplication = $scope.mpoApplications[i];
                if(mpoApplication.isSelected){
                    MpoApplication.delete(mpoApplication);
                }
            }
            $state.go('mpoApplication', null, {reload: true});
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.mpoApplications.length; i++){
                var mpoApplication = $scope.mpoApplications[i];
                if(mpoApplication.isSelected){
                    MpoApplication.update(mpoApplication);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            MpoApplication.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
				$scope.mpoApplications = result;
                $scope.total = headers('x-total-count');
            });
        };


    }]);
