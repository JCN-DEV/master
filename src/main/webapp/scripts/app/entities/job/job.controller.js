'use strict';

angular.module('stepApp')
    .controller('JobController',
     ['$scope', '$state', '$modal', 'Job', 'JobSearch', 'ParseLinks', 'ArchiveJobs','ArchiveJobsForJpadmin','Principal',
     function ($scope, $state, $modal, Job, JobSearch, ParseLinks, ArchiveJobs, ArchiveJobsForJpadmin, Principal) {

        $scope.jobs = [];
        $scope.page = 0;

        $scope.loadAll = function () {
            if(Principal.hasAnyAuthority(['ROLE_JPADMIN'])){
                ArchiveJobsForJpadmin.query({page: $scope.page, size: 5000}, function (result, headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                    $scope.jobs = result;
                    $scope.total = headers('x-total-count');
                });
            }else{
                ArchiveJobs.query({page: $scope.page, size: 5000}, function (result, headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                    $scope.jobs = result;
                    $scope.total = headers('x-total-count');
                });
            }

        };

        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            JobSearch.query({query: $scope.searchQuery}, function (result) {
                $scope.jobs = result;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.job = {
                title: null,
                type: null,
                minimumSalary: null,
                maximumSalary: null,
                vacancy: null,
                description: null,
                benefits: null,
                educationRequirements: null,
                experienceRequirements: null,
                otherRequirements: null,
                publishedAt: null,
                applicationDeadline: null,
                location: null,
                status: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllJobsSelected = false;

        $scope.updateJobsSelection = function (jobArray, selectionValue) {
            for (var i = 0; i < jobArray.length; i++) {
                jobArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function () {
            for (var i = 0; i < $scope.jobs.length; i++) {
                var job = $scope.jobs[i];
                if (job.isSelected) {
                    //Job.update(job);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function () {
            for (var i = 0; i < $scope.jobs.length; i++) {
                var job = $scope.jobs[i];
                if (job.isSelected) {
                    //Job.update(job);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function () {
            for (var i = 0; i < $scope.jobs.length; i++) {
                var job = $scope.jobs[i];
                if (job.isSelected) {
                    Job.delete(job);
                }
            }
        };

        $scope.sync = function () {
            for (var i = 0; i < $scope.jobs.length; i++) {
                var job = $scope.jobs[i];
                if (job.isSelected) {
                    Job.update(job);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Job.query({page: $scope.page, size: 5000}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jobs = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
