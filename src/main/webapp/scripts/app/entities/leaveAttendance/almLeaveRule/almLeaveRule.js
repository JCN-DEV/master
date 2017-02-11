'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('almLeaveRule', {
                parent: 'alm',
                url: '/almLeaveRules',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almLeaveRule.home.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveRule/almLeaveRules.html',
                        controller: 'AlmLeaveRuleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveRule');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('almLeaveRule.detail', {
                parent: 'alm',
                url: '/almLeaveRule/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.almLeaveRule.detail.title'
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveRule/almLeaveRule-detail.html',
                        controller: 'AlmLeaveRuleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveRule');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmLeaveRule', function($stateParams, AlmLeaveRule) {
                        return AlmLeaveRule.get({id : $stateParams.id});
                    }]
                }
            })
            .state('almLeaveRule.new', {
                parent: 'almLeaveRule',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveRule/almLeaveRule-dialog.html',
                        controller: 'AlmLeaveRuleDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveRule');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            leaveRuleName: null,
                            noOfDaysEntitled: null,
                            maxConsecutiveLeaves: null,
                            minGapBetweenTwoLeaves: null,
                            noOfInstanceAllowed: null,
                            isNegBalanceAllowed: false,
                            maxNegBalance: null,
                            almGender: null,
                            applicableServiceLength: null,
                            isCertificateRequired: false,
                            requiredNoOfDays: null,
                            isEarnLeave: false,
                            earningMethod: null,
                            earningFreequency: null,
                            daysRequiredToEarn: null,
                            noOfLeavesEarned: null,
                            isLeaveWithoutPay: false,
                            isCarryForward: false,
                            isSuffixPrefix: false,
                            maxCarryForward: null,
                            maxBalanceForward: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('almLeaveRule.edit', {
                parent: 'almLeaveRule',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'leaveAttendanceView@alm': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/almLeaveRule/almLeaveRule-dialog.html',
                        controller: 'AlmLeaveRuleDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('almLeaveRule');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AlmLeaveRule', function($stateParams, AlmLeaveRule) {
                        return AlmLeaveRule.get({id : $stateParams.id});
                    }]
                }
            });
    });
