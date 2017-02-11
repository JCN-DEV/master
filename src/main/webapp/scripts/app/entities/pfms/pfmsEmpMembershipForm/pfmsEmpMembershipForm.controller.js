'use strict';

angular.module('stepApp')
    .controller('PfmsEmpMembershipFormController', function ($scope, PfmsEmpMembershipForm, PfmsEmpMembershipFormSearch, ParseLinks) {
        $scope.pfmsEmpMembershipForms = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PfmsEmpMembershipForm.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pfmsEmpMembershipForms = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PfmsEmpMembershipForm.get({id: id}, function(result) {
                $scope.pfmsEmpMembershipForm = result;
                $('#deletePfmsEmpMembershipFormConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PfmsEmpMembershipForm.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePfmsEmpMembershipFormConfirmation').modal('hide');
                    $scope.clear();
                });
        };


        $scope.search = function () {
            PfmsEmpMembershipFormSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pfmsEmpMembershipForms = result;
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
            $scope.pfmsEmpMembershipForm = {
                isMinimumThreeYrs: false,
                isMandatoryContribute: false,
                isAnotherContFund: null,
                fundName: null,
                isEmpFamily: false,
                percentOfDeduct: null,
                isMoneySection: false,
                nomineeName: null,
                ageOfNominee: null,
                nomineeAddress: null,
                witnessNameOne: null,
                witnessMobileNoOne: null,
                witnessAddressOne: null,
                witnessNameTwo: null,
                witnessMobileNoTwo: null,
                witnessAddressTwo: null,
                stationName: null,
                applicationDate: null,
                remarks: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
