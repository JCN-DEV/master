'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pfmsEmpAdjustment', {
                parent: 'pfms',
                url: '/pfmsEmpAdjustments',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsEmpAdjustment.home.title',
                    displayName: 'Employee Adjustment'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpAdjustment/pfmsEmpAdjustments.html',
                        controller: 'PfmsEmpAdjustmentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpAdjustment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pfmsEmpAdjustment.detail', {
                parent: 'pfms',
                url: '/pfmsEmpAdjustment/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsEmpAdjustment.detail.title',
                    displayName: 'Employee Adjustment Details'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpAdjustment/pfmsEmpAdjustment-detail.html',
                        controller: 'PfmsEmpAdjustmentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpAdjustment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsEmpAdjustment', function($stateParams, PfmsEmpAdjustment) {
                        return PfmsEmpAdjustment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pfmsEmpAdjustment.new', {
                parent: 'pfmsEmpAdjustment',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Add Employee Adjustment'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpAdjustment/pfmsEmpAdjustment-dialog.html',
                        controller: 'PfmsEmpAdjustmentDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpAdjustment');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            isCurrentBalance: null,
                            ownContribute: null,
                            ownContributeInt: null,
                            preOwnContribute: null,
                            preOwnContributeInt: null,
                            reason: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('pfmsEmpAdjustment.edit', {
                parent: 'pfmsEmpAdjustment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Edit Employee Adjustment'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpAdjustment/pfmsEmpAdjustment-dialog.html',
                        controller: 'PfmsEmpAdjustmentDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpAdjustment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsEmpAdjustment', function($stateParams, PfmsEmpAdjustment) {
                        //return PfmsEmpAdjustment.get({id : $stateParams.id});
                    }]
                }
            });
    });
