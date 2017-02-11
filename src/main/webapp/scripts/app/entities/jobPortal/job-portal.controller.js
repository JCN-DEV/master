'use strict';

angular.module('stepApp')
    .controller('JobPortalController',
     ['$scope', '$stateParams', '$filter', '$location', '$state','AvailableJobs', 'Principal', 'Job', 'JobSearch', 'Jobapplication', 'Cat', 'CurJobSearchLocation','ParseLinks','JobsByCategoryName','CatsByActiveJobs','JobCategoriesWithCounter',
     function ($scope, $stateParams, $filter, $location, $state,AvailableJobs, Principal, Job, JobSearch, Jobapplication, Cat, CurJobSearchLocation,ParseLinks,JobsByCategoryName, CatsByActiveJobs, JobCategoriesWithCounter) {
        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.time = 0;
        $scope.total = 0;
        $scope.page = 0;
        $scope.per_page = 20;
        $scope.jobs = [];
        $scope.cats = [];
         //JobCategoriesWithCounter.query();
         /*CatsByActiveJobs.query({page:0, size:100}, function(result){
             console.log(result);
         });
*/
        $scope.date = $filter('date')(new Date(),'MMM dd, yyyy');
        /*Cat.query({page: 0, size: 500}, function (result, headers) {
            $scope.cats = result;

        });*/
         JobCategoriesWithCounter.query({page: 0, size: 500}, function (result, headers) {
            $scope.cats = result;

        });

        $scope.submit = function () {
            var form = $scope.form;
            $scope.submitted = true;
            if (form.$valid) {
                var searchData = {'q': $scope.q};
                $state.go('search', searchData);
            }
        };
        AvailableJobs.query({page: 0, size: 2000}, function(result){
            console.log("result found");
            console.log(result);
            //$scope.jobs=result;
            if(result.length > 0){
                angular.forEach(result, function(data){
                    /*dateCompare(new Date(data.applicationDeadline));
                    console.log('lksdjsdflkjfsdjlkfsd');*/
                    //console.log(dateCompare(new Date(data.applicationDeadline)));
                    var leftDay = Math.round(dateCompare(new Date(data.applicationDeadline)));
                    data.leftDays = leftDay;
                    //show alert for jobs when deadline left 3 days
                    if(leftDay < 4){
                        console.log(data.leftDays+" days left");
                        data.showAlert = true;
                    }
                    console.log(data);
                    //$scope.jobs.push( { key :  value } );
                    $scope.jobs.push(data);
                })
            }
            console.log("after pushing to array");
            console.log($scope.jobs);
        });

     /*JobsByCategoryName.query({cat :'Designer'});
        $scope.loadAll = function () {

            *//*JobSearch.query({query: 'type:*'}, function (result) {
                $scope.jobs = result.slice(0, 8);
                $scope.total = result.length;
            });*//*
        };
*/
        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.sector = function () {
            JobSearch.query({query: 'type:*'}, function (result) {
                $scope.jobs = result.slice(0, 8);
                $scope.total = result.length;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.location = function () {
            CurJobSearchLocation.query({location: 'bangladesh'}, function (result) {
                $scope.jobs = result.slice(0, 8);
                $scope.total = result.length;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.entry = function () {
            JobSearch.query({query: 'type:entry'}, function (result) {
                $scope.jobs = result.slice(0, 8);
                $scope.total = result.length;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.part_time = function () {
            JobSearch.query({query: 'type:part_time'}, function (result) {
                $scope.jobs = result.slice(0, 8);
                $scope.total = result.length;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });

        };
        $scope.company = function () {
            JobSearch.query({query: 'employer.name:*'}, function (result) {
                $scope.jobs = result.slice(0, 8);
                $scope.total = result.length;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

         function  dateCompare (deadline) {
             var d2 = deadline;
             var d1 = new Date();
             var miliseconds = d2-d1;
             var seconds = miliseconds/1000;
             var minutes = seconds/60;
             var hours = minutes/60;
             var days = hours/24;
             console.log("difference :"+days);
             return days+1;
        };

    }]);
