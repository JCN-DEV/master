'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsPenGrCalculation', {
                parent: 'pgms',
                url: '/pgmsPenGrCalculations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsPenGrCalculation.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/penGrCalculation/pgmsPenGrCalculations.html',
                        controller: 'PgmsPenGrCalculationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrCalculation');
                        $translatePartialLoader.addPart('withdrawnType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsPenGrCalculation.detail', {
                parent: 'pgms',
                url: '/pgmsPenGrCalculation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsPenGrCalculation.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/penGrCalculation/pgmsPenGrCalculation-detail.html',
                        controller: 'PgmsPenGrCalculationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrCalculation');
                        $translatePartialLoader.addPart('withdrawnType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsPenGrCalculation', function($stateParams, PgmsPenGrCalculation) {
                        return PgmsPenGrCalculation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsPenGrCalculation.new', {
                parent: 'pgmsPenGrCalculation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/penGrCalculation/pgmsPenGrCalculation-dialog.html',
                      controller: 'PgmsPenGrCalculationDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrCalculation');
                        $translatePartialLoader.addPart('withdrawnType');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    withdrawnType: null,
                                    categoryType: null,
                                    activeStatus: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                              }
                       }
                }
            })
            .state('pgmsPenGrCalculation.edit', {
                parent: 'pgmsPenGrCalculation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/penGrCalculation/pgmsPenGrCalculation-dialog.html',
                          controller: 'PgmsPenGrCalculationDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrCalculation');
                        $translatePartialLoader.addPart('withdrawnType');
                        return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsPenGrCalculation', function($stateParams,PgmsPenGrCalculation) {
                               return PgmsPenGrCalculation.get({id : $stateParams.id});
                     }]
                }
            });
    });
