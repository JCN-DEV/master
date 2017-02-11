
'use strict';

angular.module('stepApp').controller('JobPlacementInfoDialogController',
    ['$scope','$filter','$rootScope','$state', '$stateParams', 'entity', 'JobPlacementInfo',
        function($scope,$filter,$rootScope,$state, $stateParams, entity, JobPlacementInfo) {

        $scope.jobPlacementInfo = entity;
        $scope.load = function(id) {
            JobPlacementInfo.get({id : id}, function(result) {
                $scope.jobPlacementInfo = result;
            });
        };
        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:jobPlacementInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('jobPlacementInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save=function() {
            $scope.isSaving = true;

            if ($scope.jobPlacementInfo.id != null) {
                JobPlacementInfo.update($scope.jobPlacementInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.jobPlacementInfo.updated');
            } else {
                JobPlacementInfo.save($scope.jobPlacementInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.jobPlacementInfo.created');
            }
        };
        $scope.clear = function() {
            $state.go('jobPlacementInfo');
        };

       $scope.duration=function (firstDate,secondDate) {
           var date = $filter('date')(new Date(firstDate), 'yyyy/MM/dd');
           if(secondDate==null){
                $scope.jobPlacementInfo.workTo=null;
                var today =$filter('date')(new Date(), 'yyyy/MM/dd');
                var year = new Date().getFullYear();
                var month = new Date().getMonth() + 1;
                var day = new Date().getDate();
                var time=new Date().getTime();
            }else{
               today =$filter('date')(new Date(secondDate), 'yyyy/MM/dd');
                var year = new Date(secondDate).getFullYear();
                var month = new Date(secondDate).getMonth() + 1;
                var day = new Date(secondDate).getDate();
                var time=  new Date(secondDate).getTime()
           }

            date = date.split('/');
            today=today.split('/');
            var yy = parseInt(date[0]);
            var mm = parseInt(date[1]);
            var dd = parseInt(date[2]);
            var years, months, days;
            // months
            months = month - mm;
            if (day < dd) {
                months = months - 1;
            }
            // years
            years = year - yy;
            if (month * 100 + day < mm * 100 + dd) {
                years = years - 1;
                months = months + 12;
            }
            // days
           $scope.days = Math.floor((time - (new Date(yy + years, mm + months - 1, dd)).getTime()) / (24 * 60 * 60 * 1000));
           $scope.dura=years+" Years, "+months+" Months, "+$scope.days+" Days"
           $scope.jobPlacementInfo.durationExp=$scope.dura;

        }

}]);

