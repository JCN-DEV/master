'use strict';

angular.module('stepApp')
    .controller('MpoCommitteeMembersOfCommitteeController',
     ['$scope', '$state', '$modal', 'MpoCommitteePersonInfo', 'MpoCommitteePersonInfoSearch','User', 'ParseLinks','MpoCommitteeHistoryMonthYear', 'MpoCommitteeHistory','$rootScope',
     function ($scope, $state, $modal, MpoCommitteePersonInfo, MpoCommitteePersonInfoSearch,User, ParseLinks,MpoCommitteeHistoryMonthYear, MpoCommitteeHistory,$rootScope) {

        $scope.mpoCommitteePersonInfos = [];
        $scope.mpoCommitteeHistorys = [];
        $scope.page = 0;
        $scope.years = [];
        var currentYear = new Date().getFullYear();
        $scope.searchCommittee = function () {
            console.log('month :'+$scope.mpoCommitteeHistory.month +' year :'+$scope.mpoCommitteeHistory.year);
            MpoCommitteeHistoryMonthYear.query({month:$scope.mpoCommitteeHistory.month, year: $scope.mpoCommitteeHistory.year}, function(result){
                $scope.mpoCommitteeHistorys = result;
            });
            /*MpoCommitteePersonInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoCommitteePersonInfos = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });*/
        };

        $scope.initDates = function() {
            var i;
            for (i = currentYear+1 ;i >= currentYear-25; i--) {
                $scope.years.push(i);
                //console.log( $scope.years);
            }
        };
        $scope.initDates();

        $scope.deactivateCommitteeMembers = function () {
            if($scope.mpoCommitteeHistorys.length > 0 ){
                 angular.forEach($scope.mpoCommitteeHistorys, function(data){
                 if (data.id != null) {
                     data.mpoCommitteePersonInfo.user.activated = false;
                     User.update(data.mpoCommitteePersonInfo.user, function () {
                         data.mpoCommitteePersonInfo.activated= false;
                         MpoCommitteePersonInfo.update(data.mpoCommitteePersonInfo, function(result){
                             data.activated= false;
                             MpoCommitteeHistory.update(data);
                             $rootScope.setSuccessMessage('stepApp.mpoCommitteeHistory.successDeactivation');
                         });
                     });
                 }
                 });
            }

        };

        $scope.activateCommitteeMembers = function () {
            if($scope.mpoCommitteeHistorys.length > 0 ){
                 angular.forEach($scope.mpoCommitteeHistorys, function(data){
                 if (data.id != null) {
                     data.mpoCommitteePersonInfo.activated= true;
                     MpoCommitteePersonInfo.update(data.mpoCommitteePersonInfo, function(result){
                         data.activated= true;
                         MpoCommitteeHistory.update(data);
                         $rootScope.setSuccessMessage('stepApp.mpoCommitteeHistory.successActivation');
                     });
                 }
                 });
            }

        };



        $scope.deactivateMember = function (data) {

            if (data.id != null) {
                data.mpoCommitteePersonInfo.activated= false;
                MpoCommitteePersonInfo.update(data.mpoCommitteePersonInfo, function(result){
                    data.activated= false;
                    MpoCommitteeHistory.update(data);
                    $rootScope.setSuccessMessage('stepApp.mpoCommitteeHistory.successDeactivation');
                });
            }

        };

        $scope.activateMember = function (data) {
            if (data.id != null) {
                data.mpoCommitteePersonInfo.activated= true;
                MpoCommitteePersonInfo.update(data.mpoCommitteePersonInfo, function(result){
                    data.activated= true;
                    MpoCommitteeHistory.update(data);
                    $rootScope.setSuccessMessage('stepApp.mpoCommitteeHistory.successActivation');
                });
            }
                };

    }]);
