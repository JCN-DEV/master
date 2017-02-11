'use strict';

angular.module('stepApp')
    .controller('JPReportController',
     ['$rootScope','$scope','$sce', '$stateParams', '$filter', '$location', '$state','AvailableJobs', 'Principal', 'Job', 'JobSearch', 'Jobapplication', 'Cat', 'ParseLinks', 'GetAllJasperReportByModule','GetJasperParamByJasperReport','Institute','OrganizationType','Employer','OrganizationCategory','Division','District','Upazila','DlContCatSet',
     'DlSourceSetUp','DlContSCatSet','DlContTypeSet','HrDepartmentHeadSetup','DlBookInfo','DlFileType',
     function ($rootScope,$scope,$sce, $stateParams, $filter, $location, $state,AvailableJobs, Principal, Job, JobSearch, Jobapplication, Cat, ParseLinks, GetAllJasperReportByModule,GetJasperParamByJasperReport,Institute,OrganizationType,Employer,OrganizationCategory,Division,District,Upazila,DlContCatSet,
     DlSourceSetUp,DlContSCatSet,DlContTypeSet,HrDepartmentHeadSetup,DlBookInfo,DlFileType
     ) {
        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
         $scope.allStatus=[
             {id:1, value:'Active'},
             {id:0, value:'InActive'}
         ];
         $scope.genders=[
             {id:1, value:'Male'},
             {id:0, value:'Female'}
         ];
         //if($scope.moduleName=='job_portal'){
         //
         //}

         $scope.moduleName=$stateParams.module;
         $scope.institutes=Institute.query({size:500});
         $scope.organizations=OrganizationType.query({size: 200});
         $scope.employers=Employer.query({size: 500});
         $scope.organizationCategories=OrganizationCategory.query({size:500});
         $scope.cats=Cat.query({size:500});
         $scope.dlContCategories=DlContCatSet.query({size:500});
         console.log('............... category');
         console.log($scope.dlContCategories);
         $scope.dlContSubCategories=DlContSCatSet.query({size:500});
         $scope.contentSources=DlSourceSetUp.query({size:500});
          $scope.contentTypes=DlContTypeSet.query({size:500});
          $scope.HrDepartments=HrDepartmentHeadSetup.query({size:500});
         $scope.books=DlBookInfo.query({size:500});
         $scope.fileTypes=DlFileType.query({size:500});

         //division district upazila
         $scope.divisions = Division.query({size: 10});
         var allDistrict = District.query({page: $scope.page, size: 65}, function (result, headers) {
             return result;
         });
         var allUpazila = Upazila.query({page: $scope.page, size: 500}, function (result, headers) {
             return result;
         });
         $scope.updatedDistrict = function (select) {
             $scope.districts = [];
             angular.forEach(allDistrict, function (district) {
                 if (select == district.division.name) {
                     $scope.districts.push(district);
                 }
             });
         };
         $scope.updatedUpazila = function (select) {
             console.log('..............');
             console.log(select);
             console.log('..............');
             $scope.upazilas = [];
             angular.forEach(allUpazila, function (upazila) {
                 if (select == upazila.district.name) {
                     $scope.upazilas.push(upazila);
                 }
             });
         };
         //division district upazila

        //$scope.moduleName = "job_portal";
        $scope.jasperReports = [];

        var loadModule = function () {
            GetAllJasperReportByModule.query({module: $scope.moduleName }, function (result) {
                    $scope.jasperReports = result;
                },
                function (response) {
                }
            );
        };

         $scope.list_change=function(data){
             console.log('change_list: '+data);
             console.log('change_name '+data.cat);
         };

        loadModule();
        $scope.reportChange=function(){
            $scope.obj = $scope.jasperReport.id;
            $scope.jasperReportParameters = [];
            GetJasperParamByJasperReport.query({id: $scope.jasperReport.id}, function (result) {
                $scope.jasperReportParameters = result;
            });;
        };
 var paramDate;
        //start preview method
        $scope.reportPreview = function () {

            var parmavar = "";
            var urlString = "";
                    urlString = "http://202.4.121.77:9090/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2FDTE%2F"+$stateParams.module+"%2F";
            urlString = urlString + $scope.jasperReport.path + "&standAlone=true&j_username=user&j_password="+$rootScope.encCre+"&decorate=no";
            var parmavar = "";
            angular.forEach($scope.jasperReportParameters, function (data) {
                parmavar = parmavar + "&" + data.name + "=";
                if (data.actiontype) {
                    if (data.type == 'combo') {
                        //var array = $.map(data.actiontype, function (value, index) {
                        //    return [value];
                        //});
                        //if (array.length > 0) {
                        //    parmavar = parmavar + array[1];
                        //}

                        if (data.actiontype.toString().trim() != "")
                            parmavar = parmavar + data.actiontype;
                        parmavar = parmavar + "%25";
                    }
                    else if (data.type == 'text') {
                        if (data.actiontype.toString().trim() != "")
                            parmavar = parmavar + data.actiontype;
                        parmavar = parmavar + "%25";
                    }else if (data.type == 'date') {
                                             if (data.actiontype.toString().trim() != "")
                                                 paramDate= data.actiontype;
                                                 parmavar = parmavar+paramDate.getFullYear()+'-'+(paramDate.getMonth()+1)+'-'+paramDate.getDate()+"%25";
                     //                           // parmavar = parmavar + data.actiontype;
                                                 console.log(paramDate.getFullYear()+'-'+paramDate.getMonth()+'-'+paramDate.getDate());
                                             parmavar = parmavar + "%25";
                                         }
                    else if (data.type == 'month' || data.type == 'year') {

                        if (data.actiontype.toString().trim() != "")
                            parmavar = parmavar + data.actiontype;
                    }
                }
                else {
                    parmavar = parmavar + "%25";
                }
            });

            $scope.rptBaseurl = urlString + parmavar;
            console.log('.......URI JP');
            console.log($scope.rptBaseurl);
            $scope.url = $sce.trustAsResourceUrl($scope.rptBaseurl);
        };
        //end preview method
    }]);
