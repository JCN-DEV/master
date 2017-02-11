'use strict';

angular.module('stepApp').
controller('EmployerProfileController',
['$scope', '$stateParams', 'DataUtils','OrganizationType','OrganizationCategoryActive', 'Principal','Division','District', 'Upazila','Employer','InstituteByUpazila', 'TempEmployer', 'User', 'Country', 'TempEmployerByUserId','OrganizationTypeActive','GovernmentInstitutes',
 function ($scope, $stateParams, DataUtils,OrganizationType,OrganizationCategoryActive, Principal,Division,District, Upazila,Employer,InstituteByUpazila, TempEmployer, User, Country, TempEmployerByUserId,OrganizationTypeActive,GovernmentInstitutes) {

    $scope.employer = {};
    $scope.tempEmployer = {};
    $scope.isProfilePending = false;
    $scope.isNewProfile = false;
    //$scope.notMatched=false;
    $scope.selectUpazila = null;
    $scope.selectDistrict = null;
    $scope.selectInstitute = null;
    $scope.allDistrict = [];
    $scope.allUpazila = [];
    $scope.divisions = Division.query();
    $scope.institutes = [];
     GovernmentInstitutes.query({}, function(result, headers) {
         $scope.institutes =result;
     });
    $scope.countries = Country.query({size: 500});
     $scope.organizationTypes = OrganizationTypeActive.query({size: 50});
     $scope.organizationCategorys = OrganizationCategoryActive.query({size: 50});

    Principal.identity().then(function (account) {
        $scope.account = account;

        User.get({login: $scope.account.login}, function (result) {
            $scope.user = result;
            TempEmployerByUserId.get({id: $scope.user.id}, function (tempEmployer) {
                    $scope.isProfilePending = true;
                },
                function (response) {
                    if (response.status === 404) {
                        $scope.isProfilePending = false;
                    }
                }
            );
        });

    });

    Employer.get({id: 'my'}, function (result) {
        $scope.isNewProfile = false;
        $scope.employer = result;
        $scope.tempEmployer = result;
        if(result.upazila.id != null){
            $scope.selectUpazila = result.upazila.name;
            $scope.selectDistrict = result.upazila.district.name;
            $scope.selectInstitute = result.institute.name;

        }

    }, function (response) {
        if (response.status == 404) {
            $scope.isNewProfile = true;
            console.log($scope.isNewProfile);
        }
    });

    var onSaveSuccess = function (result) {
        //$scope.$emit('stepApp:tempEmployerUpdate', result);
        $scope.isSaving = false;
    };

    var onSaveError = function (result) {
        $scope.isSaving = false;
    };

    $scope.save = function () {

        $scope.isSaving = true;
        $scope.editEmployer = false;
        $scope.tempEmployer.user = $scope.user;

        if ($scope.tempEmployer.id != null) {
            $scope.isProfilePending = true;
            $scope.tempEmployer.id = null;
            $scope.tempEmployer.status = 'update';
            TempEmployer.save($scope.tempEmployer, onSaveSuccess, onSaveError);
        } else {
            $scope.isProfilePending = true;
            $scope.tempEmployer.id = null;
            $scope.tempEmployer.status = 'pending';
            TempEmployer.save($scope.tempEmployer, onSaveSuccess, onSaveError);
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
                $scope.institutes = [];
                $scope.selectUpazila = null;
                $scope.selectDistrict = null;
                //$scope.selectInstitute = null;
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
                $scope.institutes = [];
                $scope.selectUpazila = null;
               // $scope.selectInstitute = null;
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

           /* InstituteByUpazila.query({id: upazilaId}, function(result, headers) { $scope.allUpazila= result;
                //$scope.selectInstitute = null;
                $scope.institutes =result;
                *//* $scope.upazilas=[];
                 angular.forEach($scope.allUpazila, function(upazila) {
                 if(select !=undefined && select.id==upazila.district.id){
                 $scope.upazilas.push(upazila);
                 }
                 });*//*
            });*/
        };

    $scope.setCompanyLogo = function ($file, employer) {
                if ($file && $file.$error == 'pattern') {
                    return;
                }
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function() {
                            employer.companyLogo = base64Data;
                            employer.companyLogoContentType = $file.type;
                        });
                    };
                }
            };

    $scope.editEmployer = false;
    $scope.editEmployerProfile = function (value) {
        $scope.editEmployer = value;
    }


}]);

