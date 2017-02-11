'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pfmsDeductionFinalize', {
                parent: 'pfms',
                url: '/pfmsDeductionFinalizes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsDeductionFinalize.home.title',
                    displayName: 'Deduction Finalize'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsDeductionFinalize/pfmsDeductionFinalizes.html',
                        controller: 'PfmsDeductionFinalizeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsDeductionFinalize');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pfmsDeductionFinalize.detail', {
                parent: 'pfmsDeductionFinalize',
                url: '/pfmsDeductionFinalize/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsDeductionFinalize.detail.title',
                    displayName: 'Deduction Finalize Details'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsDeductionFinalize/pfmsDeductionFinalize-detail.html',
                        controller: 'PfmsDeductionFinalizeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsDeductionFinalize');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsDeductionFinalize', function($stateParams, PfmsDeductionFinalize) {
                        return PfmsDeductionFinalize.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pfmsDeductionFinalize.new', {
                parent: 'pfmsDeductionFinalize',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Add Deduction Finalize'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsDeductionFinalize/pfmsDeductionFinalize-dialog.html',
                        controller: 'PfmsDeductionFinalizeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsDeductionFinalize');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            finalizeYear: null,
                            finalizeMonth: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('pfmsDeductionFinalize.edit', {
                parent: 'pfmsDeductionFinalize',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Edit Deduction Finalize'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsDeductionFinalize/pfmsDeductionFinalize-dialog.html',
                        controller: 'PfmsDeductionFinalizeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsDeductionFinalize');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsDeductionFinalize', function($stateParams, PfmsDeductionFinalize) {
                        //return PfmsDeductionFinalize.get({id : $stateParams.id});
                    }]
                }
            });
    });
