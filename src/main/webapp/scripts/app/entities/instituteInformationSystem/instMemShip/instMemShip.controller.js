'use strict';

angular.module('stepApp')
    .controller('InstMemShipController',
    ['$scope', '$state', '$modal', 'InstMemShip','InstMemShipSearch', 'ParseLinks','CurrentInstCommitteeMembers','User','UserCustomUpdate',
    function ($scope, $state, $modal, InstMemShip, InstMemShipSearch, ParseLinks, CurrentInstCommitteeMembers,User,UserCustomUpdate) {

        $scope.instMemShips = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CurrentInstCommitteeMembers.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instMemShips = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        /*$scope.addClass = function(){
            console.log("+++++[[[]]]=====");
            $('.button4').addClass('active');
        }*/
        $scope.search = function () {
            InstMemShipSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instMemShips = result;
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

        $scope.deactivateMember = function (data) {
            data.user.activated = false;
            UserCustomUpdate.update(data.user, function () {
                $scope.loadAll();
            });
        };

        $scope.activateMember = function (data) {
            data.user.activated = true;
            UserCustomUpdate.update(data.user, function () {
                $scope.loadAll();
            });
        };
        $scope.clear = function () {
            $scope.instMemShip = {
                fullName: null,
                dob: null,
                gender: null,
                address: null,
                email: null,
                contact: null,
                designation: null,
                orgName: null,
                orgAdd: null,
                orgContact: null,
                date: null,
                id: null
            };
        };
    }]);
