'use strict';

angular.module('stepApp').controller('NewJobController',
['$scope', '$rootScope', '$stateParams', 'DateUtils', 'CountrysByName', 'CatByOrganizationCat', 'JobTypeAllActive','JobType', 'entity', 'Job', 'Cat', 'Employer', 'Country', 'User', '$state',
    function ($scope, $rootScope, $stateParams, DateUtils, CountrysByName, CatByOrganizationCat, JobTypeAllActive,JobType, entity, Job, Cat, Employer, Country, User, $state) {

        $scope.job = {};
       // $scope.job = entity;
        $scope.typeMinSalary = null;
        $scope.typeMaxSalary = null;
        $scope.salaryError = false;
        $scope.dateError = false;
        $scope.updateJob = false;
        $scope.organizationCategory = {};
        $scope.createJob = false;
        $scope.category = {};
        $scope.addNewCategory = false;
        Job.get({id: $stateParams.id}, function (result) {
            $scope.job = result;
        });
        $scope.jobTypes = JobTypeAllActive.query({size: 50});
        console.log(entity.publishedAt);


        if ($stateParams.id != null) {
            console.log('id existing');
            $scope.updateJob = true;
            $scope.createJob = false;
        } else {
            $scope.job.publishedAt = new Date().toISOString().substring(0,10);
            $scope.createJob = true;
        }

        $scope.isNewProfile = false;
        $scope.cats = [];
        Employer.get({id: 'my'}, function (result) {
            $scope.isNewProfile = false;
            $scope.employer = result;
            $scope.tempEmployer = result;
            $scope.organizationCategory = result.organizationCategory;
            console.log('----------------');
            console.log(result.organizationCategory.id);
            $scope.cats2 = CatByOrganizationCat.query({id: result.organizationCategory.id}, function () {
                //console.log($scope.cats2);
                angular.forEach($scope.cats2, function (value, key) {
                    {
                        $scope.cats.push({
                            description: value.description,
                            id: value.id,
                            cat: value.cat,
                            status: value.status
                        });
                    };
                });
                $scope.cats.push(
                    {
                        description: 'Other',
                        id: -1,
                        cat: 'Other',
                        status: true
                    }
                );
            });

            //console.log($scope.cats);
            // console.log("sdfasdfasdljfsdajf :"+$scope.cats);
        }, function (response) {
            if (response.status == 404) {
                $scope.isNewProfile = true;
            }
        });

        $scope.salaryValidation = function () {
            /* job.minimumSalary,job.maximumSalary
             $scope.typeMinSalary=min;
             $scope.typeMaxSalary=max;*/


            if ($scope.job.maximumSalary != null && $scope.job.maximumSalary > $scope.job.minimumSalary) {
                $scope.salaryError = false;
            } else {
                $scope.salaryError = true;
            }
        };
        $scope.deadlineValidation = function () {
            var d1 = Date.parse($scope.job.publishedAt);
            var d2 = Date.parse($scope.job.applicationDeadline);
            if (d1 >= d2) {
                $scope.dateError = true;
            }
            else {
                $scope.dateError = false;
            }
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

        //$scope.cats = Cat.query({size: 500});
        //$scope.countries = Country.query({size: 500});
        $scope.countries = CountrysByName.query();
        $scope.showPreview = false;
        Employer.get({id: 'my'}, function (result) {
            $scope.employer = result;
        });
        /*$scope.load = function(id) {
         Job.get({id : id}, function(result) {
         $scope.job = result;
         });
         }*/

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:jobUpdate', result);
            $scope.isSaving = false;
        };

        $scope.job.publishedAt = new Date();

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.changeCat = function () {
            console.log("cat :" + $scope.job.cat);
            if ($scope.job.cat.id == -1) {
                console.log("Other found");
                $scope.addNewCategory = true;
            } else {
                $scope.addNewCategory = false;
            }
        };

        $scope.save = function () {
            if ($scope.job.cat.id == -1) {
                //console.log("Other found");
                $scope.category.id = null;
                $scope.category.status = true;
                $scope.category.organizationCategory = $scope.organizationCategory;
                Cat.save($scope.category, onSaveNewCat);

            } else {
                $scope.job.location = $scope.job.location + ', ' + $scope.job.country.name;
                $scope.job.user = {};
                $scope.isSaving = true;
                $scope.job.employer = $scope.employer;
                $scope.job.user.id = $scope.employer.user.id;
                console.log($scope.job);
                if ($scope.job.id != null) {
                    Job.update($scope.job, onSaveSuccess, onSaveError);
                    $state.go('employer.job-list');
                } else {
                    $scope.job.status = 'awaiting';
                    console.log($scope.job);
                    Job.save($scope.job, onSaveSuccess, onSaveError);
                    $state.go('employer.job-list');
                }
            }

        };

        $scope.previewJob = function (value) {
            $scope.showPreview = value;

        }

        var onSaveNewCat = function (result) {
            $scope.job.cat = result;
            $scope.job.location = $scope.job.location + ', ' + $scope.job.country.name;
            $scope.job.user = {};
            $scope.isSaving = true;
            $scope.job.employer = $scope.employer;
            $scope.job.user.id = $scope.employer.user.id;
            console.log($scope.job);
            if ($scope.job.id != null) {
                Job.update($scope.job, onSaveSuccess, onSaveError);
                $state.go('employer.job-list');
            } else {
                $scope.job.status = 'awaiting';
                console.log($scope.job);
                Job.save($scope.job, onSaveSuccess, onSaveError);
                $state.go('employer.job-list');
            }

        }
    }]);
