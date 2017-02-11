'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsPenGrRate', {
                parent: 'pgms',
                url: '/pgmsPenGrRates',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsPenGrRate.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/penGrRate/pgmsPenGrRates.html',
                        controller: 'PgmsPenGrRateController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrRate');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsPenGrRate.detail', {
                parent: 'pgms',
                url: '/pgmsPenGrRate/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsPenGrRate.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/penGrRate/pgmsPenGrRate-detail.html',
                        controller: 'PgmsPenGrRateDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrRate');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsPenGrRate', function($stateParams, PgmsPenGrRate) {
                        return PgmsPenGrRate.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsPenGrRate.new', {
                parent: 'pgmsPenGrRate',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/penGrRate/pgmsPenGrRate-dialog.html',
                      controller: 'PgmsPenGrRateDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrRate');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    penGrSetId: null,
                                    workingYear: null,
                                    rateOfPenGr: null,
                                    activeStatus: null,
                                    id: null
                              }
                       }
                }
            })
            .state('pgmsPenGrRate.edit', {
                parent: 'pgmsPenGrRate',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/penGrRate/pgmsPenGrRate-dialog.html',
                          controller: 'PgmsPenGrRateDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrRate');
                        return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsPenGrRate', function($stateParams,PgmsPenGrRate) {
                               return PgmsPenGrRate.get({id : $stateParams.id});
                     }]
                }
            });
    });
