'use strict';

angular.module('stepApp')
    .controller('JobPostingInfoController',
    ['$scope','$rootScope','$sce', '$state', '$modal', 'DataUtils', 'JobPostingInfo', 'JobPostingInfoSearch', 'ParseLinks','getbyCategory','CatByJobs',
    function ($scope,$rootScope,$sce, $state, $modal, DataUtils, JobPostingInfo, JobPostingInfoSearch, ParseLinks,getbyCategory,CatByJobs) {

        $scope.jobPostingInfos = [];
        $scope.getCatbyCategory=[];
        $scope.getbyJobs =[];
        $scope.page = 0;
        $scope.loadAll = function() {
            JobPostingInfo.query({page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jobPostingInfos = result;
            });
            getbyCategory.query({page: $scope.page, size:10},function(result, headers) {
                $scope.cats = result;
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.getCatbyCategory = result;
                console.log($scope.getCatbyCategory);
            });

        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        /*var roles=$rootScope.userRoles;

        if(roles.indexOf("ROLE_ADMIN") != -1){
            $scope.showIt=false;
        }else{
            $scope.showIt=true;
           // console.log($scope.showIt);

        }*/
        $scope.previewCategory = function ()
        {
            getbyCategory.query({}, function(result){
                $rootScope.showCategoryModal(result);
            });
        };

        $rootScope.showCategoryModal = function(result)
        {
            $modal.open({
                templateUrl: 'scripts/app/entities/jobPostingInfo/category.html',
                controller: [
                    '$scope', '$modalInstance', function($scope, $modalInstance) {
                        $scope.cats = result;
                        $scope.ok = function() {
                            $rootScope.viewerObject = {};
                            $modalInstance.close();
                        };
                        $scope.closeModal = function() {
                            $rootScope.viewerObject = {};
                            $modalInstance.dismiss();
                        };
                    }
                ]
            });
        };

        $rootScope.areAllcatsSelected = false;
        $rootScope.updateCatsSelection = function (catArray, selectionValue) {
            for (var i = 0; i < catArray.length; i++)
            {
                catArray[i].isSelected = selectionValue;
            }
        };

        $rootScope.jobs = [];
        $rootScope.JobsShow = function(catArray) {
            for (var i = 0; i < catArray.length; i++)
            {
                if(catArray[i].isSelected){
                    console.log(catArray[i]);
                    CatByJobs.query({catId: catArray[i].id, page: $rootScope.page, size:10},function(result, headers) {
                        $rootScope.links = ParseLinks.parse(headers('link'));
                        if(result.length >0 ){
                            for(var j= 0; j< result.length; j++){
                                $rootScope.jobs.push(result[j]);
                            }
                        }

                    });
                }
            }
            $state.go('jobSuggestions');
        };
        $rootScope.NoData = function() {
            $rootScope.jobs = [];
        };

        $rootScope.closeModal = function() {
            $modalInstance.dismiss();
        };

        $scope.previewImage = function (content, contentType, name)
        {
            console.log(content)
            console.log(contentType)
            console.log(name)
            var blob = $rootScope.b64toBlob(content, contentType);
            $rootScope.viewerObject.content =$sce.trustAsResourceUrl( (window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = contentType;
            $rootScope.viewerObject.pageTitle = name;
            // call the modal
            $rootScope.showFilePreviewModal();
        };

        $scope.search = function () {
            JobPostingInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jobPostingInfos = result;
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
            $scope.jobPostingInfo = {
                jobPostId: null,
                jobTitle: null,
                organizatinName: null,
                jobVacancy: null,
                salaryRange: null,
                jobSource: null,
                publishedDate: null,
                applicationDateLine: null,
                jobDescription: null,
                jobFileName: null,
                upload: null,
                uploadContentType: null,
                createDate: null,
                createBy: null,
                updateBy: null,
                updateDate: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    }])
