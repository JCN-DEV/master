'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsPenGrSetup', {
                parent: 'pgms',
                url: '/pgmsPenGrSetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsPenGrSetup.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/penGrSetup/pgmsPenGrSetups.html',
                        controller: 'PgmsPenGrSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsPenGrSetup.detail', {
                parent: 'pgms',
                url: '/pgmsPenGrSetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsPenGrSetup.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/penGrSetup/pgmsPenGrSetup-detail.html',
                        controller: 'PgmsPenGrSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrSetup');
                        $translatePartialLoader.addPart('pgmsPenGrRate');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsPenGrSetup', function($stateParams, PgmsPenGrSetup) {
                        return PgmsPenGrSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsPenGrSetup.new', {
                parent: 'pgmsPenGrSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/penGrSetup/pgmsPenGrSetup-dialog.html',
                      controller: 'PgmsPenGrSetupDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrSetup');
                        $translatePartialLoader.addPart('pgmsPenGrRate');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    setupType: null,
                                    effectiveDate: null,
                                    setupVersion: null,
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
            .state('pgmsPenGrSetup.edit', {
                parent: 'pgmsPenGrSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/penGrSetup/pgmsPenGrSetup-dialog.html',
                          controller: 'PgmsPenGrSetupDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenGrSetup');
                        $translatePartialLoader.addPart('pgmsPenGrRate');
                        return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsPenGrSetup', function($stateParams,PgmsPenGrSetup) {
                               return PgmsPenGrSetup.get({id : $stateParams.id});
                     }]
                }
            });
    });
