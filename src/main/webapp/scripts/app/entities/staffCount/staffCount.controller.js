'use strict';

angular.module('stepApp')
    .controller('StaffCountController',
    ['$scope', '$state', '$modal', 'StaffCount', 'StaffCountSearch', 'ParseLinks',
    function ($scope, $state, $modal, StaffCount, StaffCountSearch, ParseLinks) {

        $scope.staffCounts = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            StaffCount.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.staffCounts = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            StaffCountSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.staffCounts = result;
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
            $scope.staffCount = {
                type: null,
                numberOfPrincipal: null,
                numberOfMaleTeacher: null,
                numberOfFemaleTeacher: null,
                numberOfDemonstrator: null,
                numberOfAssistantLibrarian: null,
                numberOfLabAssistant: null,
                numberOfScienceLabAssistant: null,
                thirdClass: null,
                fourthClass: null,
                numberOfFemaleAvailableByQuota: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllStaffCountsSelected = false;

        $scope.updateStaffCountsSelection = function (staffCountArray, selectionValue) {
            for (var i = 0; i < staffCountArray.length; i++)
            {
            staffCountArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.staffCounts.length; i++){
                var staffCount = $scope.staffCounts[i];
                if(staffCount.isSelected){
                    //StaffCount.update(staffCount);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.staffCounts.length; i++){
                var staffCount = $scope.staffCounts[i];
                if(staffCount.isSelected){
                    //StaffCount.update(staffCount);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.staffCounts.length; i++){
                var staffCount = $scope.staffCounts[i];
                if(staffCount.isSelected){
                    StaffCount.delete(staffCount);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.staffCounts.length; i++){
                var staffCount = $scope.staffCounts[i];
                if(staffCount.isSelected){
                    StaffCount.update(staffCount);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            StaffCount.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.staffCounts = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
