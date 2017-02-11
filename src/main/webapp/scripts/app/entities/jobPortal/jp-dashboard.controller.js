'use strict';

angular.module('stepApp')
    .controller('JpDashboardController',
    ['$scope', '$state', 'Principal','DataUtils', 'User','JpEmployee', 'Employer', 'TempEmployer','TempEmployerSearch', 'UsersSearch', 'ParseLinks','MyFirstEmploymentHistory',
    function ($scope, $state, Principal,DataUtils, User,JpEmployee, Employer, TempEmployer,TempEmployerSearch, UsersSearch, ParseLinks,MyFirstEmploymentHistory) {

        $scope.users = [];
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN", "ROLE_EMPLOYER","ROLE_JPADMIN","ROLE_JOBSEEKER"];
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.userRole = 'ROLE_JOBSEEKER';
        $scope.searchQueryOrganization = "";
        $scope.jpEmployee = {};

        Principal.identity().then(function (account) {
            $scope.account = account;
console.log("sdasd :"+$scope.account.authorities);
            if($scope.isInArray('ROLE_ADMIN', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_ADMIN';
            }
            else if($scope.isInArray('ROLE_EMPLOYER', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_EMPLOYER';
            }
            else if($scope.isInArray('ROLE_JPADMIN', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_JPADMIN';
            }
            else if($scope.isInArray('ROLE_USER', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_USER';
                MyFirstEmploymentHistory.get({},function(result) {
                    console.log('sdjlajsdlkfjalksdjflkajsdlfjlaksdjf.........');
                    if(result != null){
                        $scope.jpEmployee = result.jpEmployee;
                        $scope.jpEmployee.totalExperience = $scope.calculateAge(result.startFrom);
                        //$scope.jpEmployee.totalExperience
                        console.log($scope.jpEmployee);
                        JpEmployee.update($scope.jpEmployee);

                    }

                });


            }else if($scope.isInArray('ROLE_JOBSEEKER', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_JOBSEEKER';


                MyFirstEmploymentHistory.get({},function(result) {
                    console.log('sdjlajsdlkfjalksdjflkajsdlfjlaksdjf.........');
                    if(result != null){
                        $scope.jpEmployee = result.jpEmployee;
                        $scope.jpEmployee.totalExperience = $scope.calculateAge(result.startFrom);
                        //$scope.jpEmployee.totalExperience
                        console.log($scope.jpEmployee);
                        JpEmployee.update($scope.jpEmployee);

                    }

                });


            }
        });

        $scope.calculateAge = function(birthday) {
            console.log(birthday);
            var ageDifMs = Date.now() - new Date(birthday);
            var ageDate = new Date(ageDifMs);
            return Math.abs(ageDate.getUTCFullYear() - 1970);
        };


        $scope.isInArray = function isInArray(value, array) {
            return array.indexOf(value) > -1;
        };



        $scope.tempEmployers = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Employer.query({page: $scope.page, size: 5000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tempEmployers = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            console.log("search query :"+$scope.searchQueryOrganization);
            TempEmployerSearch.query({query: $scope.searchQueryOrganization}, function(result) {
                $scope.tempEmployers = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };
        $scope.search2 = function (s) {
            console.log("search query :"+s);
            TempEmployerSearch.query({query: s}, function(result) {
                $scope.tempEmployers = result;
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
            $scope.tempEmployer = {
                name: null,
                alternativeCompanyName: null,
                contactPersonName: null,
                personDesignation: null,
                contactNumber: null,
                companyInformation: null,
                address: null,
                city: null,
                zipCode: null,
                companyWebsite: null,
                industryType: null,
                businessDescription: null,
                companyLogo: null,
                companyLogoContentType: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        // bulk operations start
        $scope.areAllTempEmployersSelected = false;

        $scope.updateTempEmployersSelection = function (TempEmployerArray, selectionValue) {
            for (var i = 0; i < TempEmployerArray.length; i++)
            {
                TempEmployerArray[i].isSelected = selectionValue;
            }
        };


        /*$scope.import = function (){
            for (var i = 0; i < $scope.tempEmployers.length; i++){
                var tempEmployer = $scope.tempEmployers[i];
                if(tempEmployer.isSelected){
                    //TempEmployer.update(TempEmployer);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.tempEmployers.length; i++){
                var tempEmployer = $scope.tempEmployers[i];
                if(tempEmployer.isSelected){
                    //TempEmployer.update(TempEmployer);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.tempEmployers.length; i++){
                var tempEmployer = $scope.tempEmployers[i];
                if(tempEmployer.isSelected){
                    TempEmployer.delete(tempEmployer);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.tempEmployers.length; i++){
                var tempEmployer = $scope.tempEmployers[i];
                if(tempEmployer.isSelected){
                    TempEmployer.update(tempEmployer);
                }
            }
        };*/

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            TempEmployer.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tempEmployers = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);

