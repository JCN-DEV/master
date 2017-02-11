'use strict';

angular.module('stepApp')
    .controller('PgmsElpcController',
    ['$scope', 'PgmsElpc', 'PgmsElpcSearch', 'ParseLinks',
    function ($scope, PgmsElpc, PgmsElpcSearch, ParseLinks) {
        $scope.pgmsElpcs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PgmsElpc.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pgmsElpcs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PgmsElpc.get({id: id}, function(result) {
                $scope.pgmsElpc = result;
                $('#deletePgmsElpcConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PgmsElpc.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePgmsElpcConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PgmsElpcSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsElpcs = result;
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
            $scope.pgmsElpc = {
                empCode: null,
                instCode: null,
                empName: null,
                instName: null,
                desigId: null,
                designation: null,
                dateOfBirth: null,
                joinDate: null,
                beginDateOfRetiremnt: null,
                retirementDate: null,
                lastRcvPayscale: null,
                incrsDtOfYrlyPayment: null,
                gainingLeave: null,
                leaveType: null,
                leaveTotal: null,
                appRetirementDate: null,
                mainPayment: null,
                incrMonRateLeaving: null,
                specialPayment: null,
                specialAllowance: null,
                houserentAl: null,
                treatmentAl: null,
                dearnessAl: null,
                travellingAl: null,
                laundryAl: null,
                personalAl: null,
                technicalAl: null,
                hospitalityAl: null,
                tiffinAl: null,
                advOfMakingHouse: null,
                vechileStatus: null,
                advTravAl: null,
                advSalary: null,
                houseRent: null,
                carRent: null,
                gasBill: null,
                santryWaterTax: null,
                bankAcc: null,
                accBookNo: null,
                bookPageNo: null,
                bankInterest: null,
                monlyDepRateFrSalary: null,
                expectedDeposition: null,
                accDate: null,
                appNo: null,
                appDate: null,
                appType: null,
                appComments: null,
                aprvStatus: 'Pending',
                aprvDate: null,
                aprvComment: null,
                aprvBy: null,
                notificationStatus: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateBy: null,
                updateDate: null,
                id: null
            };
        };
    }]);
