'use strict';

angular.module('stepApp')
    .controller('LecturerSeniorityController',
    ['$scope','$state','$modal','LecturerSeniority','LecturerSenioritySearch','ParseLinks',
    function ($scope, $state, $modal, LecturerSeniority, LecturerSenioritySearch, ParseLinks) {

        $scope.lecturerSenioritys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            LecturerSeniority.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.lecturerSenioritys = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            LecturerSenioritySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lecturerSenioritys = result;
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
            $scope.lecturerSeniority = {
                serial: null,
                name: null,
                subject: null,
                firstMPOEnlistingDate: null,
                joiningDateAsLecturer: null,
                dob: null,
                remarks: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllLecturerSenioritysSelected = false;

        $scope.updateLecturerSenioritysSelection = function (lecturerSeniorityArray, selectionValue) {
            for (var i = 0; i < lecturerSeniorityArray.length; i++)
            {
            lecturerSeniorityArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.lecturerSenioritys.length; i++){
                var lecturerSeniority = $scope.lecturerSenioritys[i];
                if(lecturerSeniority.isSelected){
                    //LecturerSeniority.update(lecturerSeniority);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.lecturerSenioritys.length; i++){
                var lecturerSeniority = $scope.lecturerSenioritys[i];
                if(lecturerSeniority.isSelected){
                    //LecturerSeniority.update(lecturerSeniority);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.lecturerSenioritys.length; i++){
                var lecturerSeniority = $scope.lecturerSenioritys[i];
                if(lecturerSeniority.isSelected){
                    LecturerSeniority.delete(lecturerSeniority);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.lecturerSenioritys.length; i++){
                var lecturerSeniority = $scope.lecturerSenioritys[i];
                if(lecturerSeniority.isSelected){
                    LecturerSeniority.update(lecturerSeniority);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            LecturerSeniority.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.lecturerSenioritys = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
