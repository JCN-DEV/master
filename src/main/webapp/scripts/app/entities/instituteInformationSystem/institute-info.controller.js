'use strict';


angular.module('stepApp')
    .controller('InstituteInfoController',
    ['$scope','$location', 'instituteList', 'Principal', 'InstGenInfoByStatus', 'ParseLinks','InstituteByLogin',
    function ($scope, $location, instituteList, Principal, InstGenInfoByStatus, ParseLinks,InstituteByLogin) {

        Principal.identity().then(function (account) {
            $scope.account = account;
        });

        $scope.instCurriculums = [];
        $scope.instCources = [];
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.currentPageNumber = 'Page Number ' + 1;

        $scope.pageChangeHandler = function(num) {
              console.log('meals page changed to ' + num);
              $scope.currentPageNumber = 'Page Number ' + num;
          };

        InstituteByLogin.query({},function(result){
            $scope.logInInstitute = result;
        });

        $scope.location = $location.path();
        $scope.instGenInfos = [];
        $scope.page = 0;
        $scope.showapprove = false;
        $scope.loadAll = function() {
            InstGenInfoByStatus.query({status:instituteList ,page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instGenInfos = result;
                if($scope.instGenInfos[0].status === 1){
                    $scope.showapprove = true;
                }
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

    }]).controller('InstitutePendingInfoController',
    ['$scope','$location', 'instituteList', 'Principal', 'InstGenInfoByStatus', 'ParseLinks','InstituteByLogin','InstGenInfosPendingInfoList',
    function ($scope, $location, instituteList, Principal, InstGenInfoByStatus, ParseLinks, InstituteByLogin, InstGenInfosPendingInfoList) {

        Principal.identity().then(function (account) {
            $scope.account = account;
        });

        InstituteByLogin.query({},function(result){
            $scope.logInInstitute = result;
        });

        $scope.location = $location.path();
        $scope.instGenInfos = [];
        $scope.page = 0;
        $scope.showapprove = false;
        $scope.loadAll = function() {
            InstGenInfosPendingInfoList.query({page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instGenInfos = result;
                if($scope.instGenInfos[0].status === 1){
                    $scope.showapprove = true;
                }
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

    }]).controller('InstituteInfoRejectedListController',
    ['$scope','$location', 'instituteList', 'Principal', 'InstGenInfoByStatus', 'ParseLinks','InstGenInfosRejectedList',
    function ($scope, $location, instituteList, Principal, InstGenInfoByStatus, ParseLinks, InstGenInfosRejectedList) {

        Principal.identity().then(function (account) {
            $scope.account = account;
        });

        $scope.location = $location.path();
        $scope.instGenInfos = [];
        $scope.page = 0;
        $scope.showapprove = false;
        $scope.loadAll = function() {
            InstGenInfosRejectedList.query({page: $scope.page, size: 1000}, function(result, headers) {
                  $scope.links = ParseLinks.parse(headers('link'));
                  $scope.instGenInfos = result;

                  console.log("=================");
                  console.log($scope.instGenInfos);
                  console.log("=================");

                if($scope.instGenInfos[0].status === 1){
                    $scope.showapprove = true;
                }
                  $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

    }]).controller('InstituteMpoListController',
    ['$scope','$location', 'instituteList', 'Principal', 'InstGenInfoByStatus', 'ParseLinks','InstGenInfosRejectedList','MpoListedInstitutes','InstituteByInstLevelName',
    function ($scope,$location, instituteList, Principal, InstGenInfoByStatus, ParseLinks,InstGenInfosRejectedList, MpoListedInstitutes,InstituteByInstLevelName) {

        Principal.identity().then(function (account) {
            $scope.account = account;
        });

        $scope.location = $location.path();
        $scope.instGenInfos = [];
        $scope.page = 0;
        $scope.showapprove = false;
        $scope.loadAll = function() {
            MpoListedInstitutes.query({}, function(result) {
                  $scope.instGenInfos = result;
                  console.log("=================");
                  console.log($scope.instGenInfos);
                  console.log("=================");
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

    }]).controller('LevelWiseInstitutesController',
    ['$scope','$location', 'instituteList', 'Principal', 'InstGenInfoByStatus', 'ParseLinks','InstituteByInstLevelName','$stateParams',
    function ($scope,$location, instituteList, Principal, InstGenInfoByStatus, ParseLinks, InstituteByInstLevelName, $stateParams) {
        $scope.institutes = [];
        $scope.page = 0;
        $scope.showapprove = false;
        $scope.loadAll = function() {
            InstituteByInstLevelName.query({name: $stateParams.levelName}, function(result) {
                  $scope.institutes = result;
                  console.log("=================");
                  console.log($scope.institutes);
                  console.log("=================");
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

    }])
    .controller('InstituteInfoApproveController',
    ['$scope','$rootScope', '$stateParams', 'Principal', 'instituteList', 'InstGenInfo', 'InstituteAllInfo', 'AllInstInfraInfo', 'InstituteAllInfosTemp','InstituteStatusByInstitute',
    function ($scope,$rootScope, $stateParams, Principal, instituteList, InstGenInfo, InstituteAllInfo, AllInstInfraInfo, InstituteAllInfosTemp, InstituteStatusByInstitute) {

        // Previously only general info approval was applicable, now each tab need to approve. So, When init check status of all items tab and do action

        //alert($stateParams.id);

        $scope.instituteStatus = {};

        InstGenInfo.get({id: $stateParams.id},function(result){
            if(result.institute != null){
                InstituteStatusByInstitute.get({id: result.institute.id}, function(instStatus){
                    console.log(instStatus);
                    $scope.instituteStatus = instStatus;
                });
            }

            // Initialization of TAB
            $scope.instGenInfo = result;
            $scope.generalInfoTab = false;
            $scope.stuffInfoTab = false;
            $scope.academicInfoTab = false;
            $scope.financialInfoTab = false;
            $scope.infrastructureInfoTab = false;
            $scope.courseInfoTab = false;
            $scope.curriculumInfoTab = false;
            $scope.adminInfoTab = false;
            $scope.hideGenInfoApprove = true;
            $scope.hideAdmInfoApprove = false;
            $scope.hideAcaInfoApprove = false;
            $scope.hideStuffInfoApprove = false;
            $scope.hideFinnInfoApprove = false;
            $scope.hideInfraInfoApprove = false;
            $scope.hideCurriculumInfoApprove = false;
            $scope.hideCourseInfoApprove = false;
            $scope.successMsg = $rootScope.setSuccessMessage;


            if($scope.instGenInfo.type=='Government'){
                $scope.instGenInfo.mpoEnlisted=false;
                $scope.hidempoEnlisted=true;
            }

            if($scope.instGenInfo.mpoEnlisted == true){
                $scope.mpoListed = 'Yes';
            }
            else{
                $scope.mpoListed = 'No';
            }

            if($scope.instGenInfo.status != null && $scope.instGenInfo.status> 0 ){
                AllInstInfraInfo.get({institueid: $scope.instGenInfo.institute.id},function(result){
                    console.log(result);
                    $scope.instShopInfos = result.instShopInfoList;
                    $scope.instLabInfos = result.instLabInfoList;
                    $scope.instPlayGroundInfos = result.instPlayGroundInfoList;
                });
            }


            if($scope.instGenInfo.status==null || $scope.instGenInfo.status==0  || $scope.instGenInfo.status==2){
                console.log('------vvvvvvvvvv---------');
                if($scope.instGenInfo.status==null){
                    $scope.instGenInfo.status=0;
                }
                $scope.hideGenInfoApprove=false;
            }

            console.log(result);
            //alert('Hi Institute');
            if(result.institute){
            // Previous used -> InstituteAllInfo
                InstituteAllInfosTemp.get({id: $scope.instGenInfo.institute.id},function(res){

                    console.log(res);
                    $scope.instGenInfo = res.instGenInfo;
                    $scope.instAdmInfo = res.instAdmInfo;
                    $scope.insAcademicInfo = res.insAcademicInfo;
                    $scope.instEmpCount = res.instEmpCount;
                    $scope.instFinancialInfos = res.instFinancialInfo;
                    $scope.instInfraInfo = res.instInfraInfo;



                    $scope.instGovernBodys = res.instGovernBody;
                    $scope.instCurriculums = res.instCurriculums;
                    $scope.instCources = res.instCourses;
                    $scope.instituteAllInfo =  res;
                    ;

                    // Temp data test
                    console.log('Temp Data test');
                    console.log('$scope.instGenInfo ---> ');
                    console.log($scope.instGenInfo);
                    console.log('$scope.instAdmInfo ---> ');
                    console.log($scope.instAdmInfo);
                    console.log('$scope.insAcademicInfo ---> ');
                    console.log($scope.insAcademicInfo);
                    console.log('$scope.instEmpCount ---> ');
                    console.log($scope.instEmpCount);
                    console.log('$scope.instFinancialInfos ---> ');
                    console.log($scope.instFinancialInfos);
                    console.log('$scope.instInfraInfo ---> ');
                    console.log($scope.instInfraInfo);
                    console.log('$scope.instGovernBodys ---> ');
                    console.log($scope.instGovernBodys);
                    console.log('$scope.instCurriculums ---> ');
                    console.log($scope.instCurriculums);
                    console.log('$scope.instCources ---> ');
                    console.log($scope.instCources);





                    if($scope.instituteAllInfo.instGenInfo == null){
                             $scope.generalInfoTab = true;
                        console.log($scope.generalInfoTab);
                    }else{
                        if($scope.instGenInfo.status==0){
                            $scope.hideGenInfoApprove=true;
                        }
                    }
                    console.log("adminInfo"+$scope.adminInfo);
                    if($scope.instituteAllInfo.instAdmInfo == null){

                             $scope.adminInfoTab = true;
                        console.log($scope.adminInfo);
                    }else{
                        if($scope.instAdmInfo.status==1 || $scope.instAdmInfo.status==2){
                            console.log($scope.adminInfo);
                            $scope.hideAdmInfoApprove=true;
                        }
                    }
                    console.log("academicInfo"+$scope.academicInfoTab);
                    if($scope.instituteAllInfo.insAcademicInfo == null){
                             $scope.academicInfoTab = true;
                        console.log($scope.academicInfoTab);
                    }else{
                        if($scope.insAcademicInfo.status==1 || $scope.insAcademicInfo.status==2){
                            $scope.hideAcaInfoApprove=true;
                        }
                    }

                    if( $scope.instCources.length == 0){
                             $scope.courseInfoTab = true;
                        console.log($scope.instCources);
                    }else{
                        if($scope.instCources[0].status==1 || $scope.instCources[0].status==2 ){
                            $scope.hideCourseInfoApprove=true;
                        }
                    }

                    if( $scope.instCurriculums.length == 0){
                             $scope.curriculumInfoTab = true;
                        console.log($scope.instCurriculums);
                    }else{
                        if($scope.instCurriculums[0].status==1 || $scope.instCurriculums[0].status==2 ){
                            $scope.hideCurriculumInfoApprove=true;
                        }
                    }
                    console.log("stuffInfo"+$scope.stuffInfoTab);
                    if($scope.instituteAllInfo.instEmpCount == null){
                        $scope.stuffInfoTab = true;

                    }else{
                        if($scope.instEmpCount.status==1 || $scope.instEmpCount.status==2){
                            $scope.hideStuffInfoApprove=true;
                        }
                    }
                    console.log("financialInfo"+$scope.financialInfoTab);
                    if($scope.instituteAllInfo.instFinancialInfo.length === 0){
                              $scope.financialInfoTab = true;
                        console.log("financial"+$scope.financialInfoTab);
                    }else{
                        if($scope.instFinancialInfos[0].status==1 || $scope.instFinancialInfos[0].status==2 ){
                            $scope.hideFinnInfoApprove=true;
                        }
                    }
                    console.log("financialInfo"+$scope.infrastructureInfoTab);
                    if($scope.instituteAllInfo.instInfraInfo == null){
                              $scope.infrastructureInfoTab = true;
                        console.log("infrastructure"+$scope.infrastructureInfoTab);
                    }else{
                        if($scope.instInfraInfo.status==1 || $scope.instInfraInfo.status==2){
                            $scope.hideInfraInfoApprove=true;
                        }
                    }
                });
            }
            else{
                $scope.generalInfoTab = false;
                $scope.stuffInfoTab = true;
                $scope.academicInfoTab = true;
                $scope.financialInfoTab = true;
                $scope.infrastructureInfoTab = true;
                $scope.adminInfoTab = true;
            }
        });


       /* $scope.generalInfo = function(){
        }
        $scope.adminInfo = function(){
        }
        $scope.academicInfo = function(){
        }
        $scope.infraInfo = function(){
        }
        $scope.stuffInfo = function(){
        }
        $scope.financialInfo = function(){
        }*/
    }])
