'use strict';

angular.module('stepApp')
    .controller('AlmLeaveRuleController',
    ['$scope', 'AlmLeaveRule', 'AlmLeaveRuleSearch', 'ParseLinks',
    function ($scope, AlmLeaveRule, AlmLeaveRuleSearch, ParseLinks) {
        $scope.almLeaveRules = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmLeaveRule.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almLeaveRules = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmLeaveRule.get({id: id}, function(result) {
                $scope.almLeaveRule = result;
                $('#deleteAlmLeaveRuleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmLeaveRule.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmLeaveRuleConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmLeaveRuleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almLeaveRules = result;
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
            $scope.almLeaveRule = {
                leaveRuleName: null,
                noOfDaysEntitled: null,
                maxConsecutiveLeaves: null,
                minGapBetweenTwoLeaves: null,
                noOfInstanceAllowed: null,
                isNegBalanceAllowed: null,
                maxNegBalance: null,
                almGender: null,
                applicableServiceLength: null,
                isCertificateRequired: null,
                requiredNoOfDays: null,
                isEarnLeave: null,
                daysRequiredToEarn: null,
                noOfLeavesEarned: null,
                isLeaveWithoutPay: null,
                isCarryForward: null,
                maxCarryForward: null,
                maxBalanceForward: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
