'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pfmsEmpMonthlyAdj', {
                parent: 'pfms',
                url: '/pfmsEmpMonthlyAdjs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsEmpMonthlyAdj.home.title',
                    displayName: 'Employee Monthly Adjustment'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMonthlyAdj/pfmsEmpMonthlyAdjs.html',
                        controller: 'PfmsEmpMonthlyAdjController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMonthlyAdj');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pfmsEmpMonthlyAdj.detail', {
                parent: 'pfms',
                url: '/pfmsEmpMonthlyAdj/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsEmpMonthlyAdj.detail.title',
                    displayName: 'Employee Monthly Adjustment Details'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMonthlyAdj/pfmsEmpMonthlyAdj-detail.html',
                        controller: 'PfmsEmpMonthlyAdjDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMonthlyAdj');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsEmpMonthlyAdj', function($stateParams, PfmsEmpMonthlyAdj) {
                        return PfmsEmpMonthlyAdj.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pfmsEmpMonthlyAdj.new', {
                parent: 'pfmsEmpMonthlyAdj',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Add Employee Monthly Adjustment'

                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMonthlyAdj/pfmsEmpMonthlyAdj-dialog.html',
                        controller: 'PfmsEmpMonthlyAdjDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMonthlyAdj');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            adjYear: null,
                            adjMonth: null,
                            deductedAmount: null,
                            modifiedAmount: null,
                            reason: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('pfmsEmpMonthlyAdj.edit', {
                parent: 'pfmsEmpMonthlyAdj',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Edit Employee Monthly Adjustment'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMonthlyAdj/pfmsEmpMonthlyAdj-dialog.html',
                        controller: 'PfmsEmpMonthlyAdjDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMonthlyAdj');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsEmpMonthlyAdj', function($stateParams, PfmsEmpMonthlyAdj) {
                        //return PfmsEmpMonthlyAdj.get({id : $stateParams.id});
                    }]
                }
            });
    });
