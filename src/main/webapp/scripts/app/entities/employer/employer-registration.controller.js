'use strict';
angular.module('stepApp').controller('EmployerRegistrationController',
['$scope', '$rootScope', '$state', '$stateParams', 'InstituteByUpazila','District','Upazila','CountrysByName', 'DataUtils','Division','OrganizationType' ,'OrganizationCategory', 'entity', 'Employer', 'Auth', 'User', 'Country', 'TempEmployer', 'ExistingEmployer','OrganizationCategoryActive','GovernmentInstitutes','OrganizationTypeActive',
    function ($scope, $rootScope, $state, $stateParams, InstituteByUpazila,District,Upazila,CountrysByName, DataUtils,Division,OrganizationType ,OrganizationCategory, entity, Employer, Auth, User, Country, TempEmployer, ExistingEmployer,OrganizationCategoryActive,GovernmentInstitutes,OrganizationTypeActive) {

        //$scope.countries = Country.query({size: 500});
        $scope.countries = CountrysByName.query();
        console.log("countrys :"+$scope.countries);
        $scope.organizationTypes = OrganizationTypeActive.query({size: 50});
        $scope.organizationCategorys = OrganizationCategoryActive.query({size: 50});
        $scope.employer = {};
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.didNotMatch=null;
        $scope.zipCode=null;
        $scope.notMatched=false;
        $scope.allDistrict = [];
        $scope.allUpazila = [];
        $scope.divisions = Division.query();
        $scope.institutes = [];
        GovernmentInstitutes.query({}, function(result, headers) {
            $scope.institutes =result;
        });

        console.log($scope.organizationCategorys);
        console.log($scope.organizationTypes);

             $scope.matchPass=function(pass,conPass){

                if(pass != conPass){
                    $scope.notMatched=true;
                }else{
                    $scope.notMatched=false;
                }

            };

        $scope.getzip=function (zip){
         $scope.zipCode=zip;

        };

        //$scope.validateCompanyName=function(name){
        //console.log('you typed: '+name);
        //       ExistingEmployer.get({name: name}, function (result) {
        //
        //
        //       }, function (response) {
        //           if (response.status == 404) {
        //                console.log('expmployer existing ...'+name);
        //           }
        //       });
        //
        //};//validation method


        $scope.validateZip=function(zip){
            console.log('zip code : '+zip);
        };

        var onSaveSuccess = function (result) {
            $scope.isSaving = true;

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };
        $scope.setCompanyLogo = function ($file, employer) {
            if ($file && $file.$error == 'pattern') {
                return;
            }
            if ($file) {
                if($file.size/1024 > 100){
                    alert("File size should be maximum 100KB!");
                }else{
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function() {
                            employer.companyLogo = base64Data;
                            employer.companyLogoContentType = $file.type;
                            employer.companyLogoName = $file.name;
                        });
                    };
                }

            }
        };

        $scope.save = function () {
            if ($scope.employer.user.password !== $scope.employer.user.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.isSaving = true;
                $scope.employer.user.langKey = "en";
                //$scope.employer.user.firstName = $scope.employer.user.login;
                $scope.employer.user.activated = false;
                $scope.employer.user.authorities = ["ROLE_EMPLOYER"];

                Auth.createAccount($scope.employer.user).then(function (newUser) {
                    $scope.employer.user.id = parseInt(newUser.id);
                    $scope.employer.status = "pending";
                    TempEmployer.save($scope.employer, onSaveSuccess, onSaveError);
                    $rootScope.setSuccessMessage('stepApp.employer.created');
                    $scope.success = 'OK';
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };

        if($scope.allDistrict.length==0){
            District.query({page: $scope.page, size: 65}, function(result, headers) { $scope.allDistrict= result;});
        }
        if( $scope.allUpazila.length==0){
            Upazila.query({page: $scope.page, size: 500}, function(result, headers) { $scope.allUpazila= result;});
        }
        $scope.updatedDistrict=function(select){
            if($scope.allDistrict.length==0){
                District.query({page: $scope.page, size: 65}, function(result, headers) { $scope.allDistrict= result;
                    console.log(select);
                    console.log($scope.allDistrict);

                    $scope.districts=[];
                    angular.forEach($scope.allDistrict, function(district) {
                        if(select !=undefined && select.id == district.division.id){
                            $scope.districts.push(district);
                        }
                    });
                });
            }else{
                console.log(select);
                console.log($scope.allDistrict);

                $scope.districts=[];
                $scope.allUpazila = [];
               // $scope.institutes = [];
                angular.forEach($scope.allDistrict, function(district) {
                    if(select != undefined  && select.id == district.division.id){
                        $scope.districts.push(district);
                    }
                });
            }



        };
        $scope.updatedUpazila=function(select){
            //if( $scope.allUpazila.length==0){
                Upazila.query({page: $scope.page, size: 500}, function(result, headers) { $scope.allUpazila= result;
                    console.log(select);
                    console.log($scope.allUpazila);
                    $scope.upazilas=[];
                   // $scope.institutes = [];
                    angular.forEach($scope.allUpazila, function(upazila) {
                        if(select !=undefined && select.id==upazila.district.id){
                            $scope.upazilas.push(upazila);
                        }
                    });
                });
            /*}else{
                console.log(select);
                console.log($scope.allUpazila);
                $scope.upazilas=[];
                angular.forEach($scope.allUpazila, function(upazila) {
                    if(select !=undefined && select.id==upazila.district.id){
                        $scope.upazilas.push(upazila);
                    }
                });
            }*/

        };

        $scope.updatedInstitute=function(upazilaId){

            /*InstituteByUpazila.query({id: upazilaId}, function(result, headers) {
                $scope.allUpazila= result;

                $scope.institutes =result;
                   *//* $scope.upazilas=[];
                    angular.forEach($scope.allUpazila, function(upazila) {
                        if(select !=undefined && select.id==upazila.district.id){
                            $scope.upazilas.push(upazila);
                        }
                    });*//*
                });*/
            };

        /*$scope.findInstitutes=function(country){
            if(country.name != null && country.name != 'Bangladesh'){
                GovernmentInstitutes.query({}, function(result, headers) {
                    $scope.institutes =result;
                });
            }else{
                $scope.institutes =[];
            }
        };
*/



    }]);
