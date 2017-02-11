'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pfmsDeduction', {
                parent: 'pfms',
                url: '/pfmsDeductions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsDeduction.home.title',
                    displayName: 'Deduction'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsDeduction/pfmsDeductions.html',
                        controller: 'PfmsDeductionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsDeduction');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pfmsDeduction.detail', {
                parent: 'pfms',
                url: '/pfmsDeduction/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsDeduction.detail.title',
                    displayName: 'Deduction Details'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsDeduction/pfmsDeduction-detail.html',
                        controller: 'PfmsDeductionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsDeduction');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsDeduction', function($stateParams, PfmsDeduction) {
                        return PfmsDeduction.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pfmsDeduction.new', {
                parent: 'pfmsDeduction',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'New Deduction'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsDeduction/pfmsDeduction-dialog.html',
                        controller: 'PfmsDeductionDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsDeduction');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            accountNo: null,
                            deductionAmount: null,
                            deductionDate: null,
                            activeStatus: false,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('pfmsDeduction.edit', {
                parent: 'pfmsDeduction',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Edit Deduction'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsDeduction/pfmsDeduction-dialog.html',
                        controller: 'PfmsDeductionDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsDeduction');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsDeduction', function($stateParams, PfmsDeduction) {
                        //return PfmsDeduction.get({id : $stateParams.id});
                    }]
                }
            });
    });
