'use strict';

angular.module('stepApp')
    .controller('IisCourseInfoController', function ($scope, $state, $modal, IisCourseInfo, IisCourseInfoSearch, ParseLinks, $stateParams,  IisCurriculumInfo, CmsTrade, CmsSubject,FindCourseByInstId,IisCourseInfosTempOfCurrentInst, InstVacancySpecialRole) {
               $scope.FindCourseByInstId ={};

        $scope.iisCourseInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {


            IisCourseInfosTempOfCurrentInst.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.iisCourseInfos = result;
                console.log("===================================");
                console.log($scope.iisCourseInfos);
                if($scope.iisCourseInfos.length < 1){
                    $state.go('instituteInfo.iisCourseInfo.new',{},{reload:true});
                }


            });
           /* IisCourseInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.iisCourseInfos = result;
                console.log("===================================");
                console.log($scope.iisCourseInfos);
                if($scope.iisCourseInfos.length < 1){
                    $state.go('instituteInfo.iisCourseInfo.new',{},{reload:true});
                }
            });*/
        };

 FindCourseByInstId.query({page: $scope.page, size: 20}, function(result, headers) {

                        $scope.findCourseByInstIds = result;

                        console.log("=======================Amanur Rahman=================");
                        console.log(result);
                        console.log("=======================Amanur Rahman=================");

                    });
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            IisCourseInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.iisCourseInfos = result;
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
            $scope.iisCourseInfo = {
                perDateEdu: null,
                perDateBteb: null,
                mpoEnlisted: null,
                dateMpo: null,
                seatNo: null,
                shift: null,
                id: null
            };
        };

               //$scope.iisCourseInfo = entity;
                $scope.iiscurriculuminfos = IisCurriculumInfo.query();
                $scope.cmstrades = CmsTrade.query();
                $scope.cmssubjects = CmsSubject.query();
               // $scope.iisCourseInfo.mpoEnlisted=true;

                    $scope.load = function(id) {
                    IisCourseInfo.get({id : id}, function(result) {
                        $scope.iisCourseInfo = result;
                    });
                };

                var onSaveSuccess = function (result) {
                    $scope.$emit('stepApp:iisCourseInfoUpdate', result);
                    //$modalInstance.close(result);
                    $scope.isSaving = false;
                    $state.go('instituteInfo.iisCourseInfo',{},{reload:true});
                };

                var onSaveError = function (result) {
                    $scope.isSaving = false;
                };

                $scope.save = function () {
                    $scope.isSaving = true;
                    if ($scope.iisCourseInfo.id != null) {
                        IisCourseInfo.update($scope.iisCourseInfo, onSaveSuccess, onSaveError);
                    } else {
                        IisCourseInfo.save($scope.iisCourseInfo, onSaveSuccess, onSaveError);
                    }
                };

                $scope.clear = function() {
                    //$modalInstance.dismiss('cancel');
                };

    });
