'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsPenRules', {
                parent: 'pgms',
                url: '/pgmsPenRuless',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsPenRules.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/penRules/pgmsPenRuless.html',
                        controller: 'PgmsPenRulesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenRules');
                        $translatePartialLoader.addPart('penQuota');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsPenRules.detail', {
                parent: 'pgms',
                url: '/pgmsPenRules/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsPenRules.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/penRules/pgmsPenRules-detail.html',
                        controller: 'PgmsPenRulesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenRules');
                        $translatePartialLoader.addPart('penQuota');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsPenRules', function($stateParams, PgmsPenRules) {
                        return PgmsPenRules.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsPenRules.new', {
                parent: 'pgmsPenRules',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/penRules/pgmsPenRules-dialog.html',
                      controller: 'PgmsPenRulesDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenRules');
                        $translatePartialLoader.addPart('penQuota');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    quotaType: null,
                                    minAgeLimit: null,
                                    maxAgeLimit: null,
                                    minWorkDuration: null,
                                    disable: null,
                                    senile: null,
                                    activeStatus: true,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                              }
                       }
                }
            })
            .state('pgmsPenRules.edit', {
                parent: 'pgmsPenRules',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/penRules/pgmsPenRules-dialog.html',
                          controller: 'PgmsPenRulesDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsPenRules');
                        $translatePartialLoader.addPart('penQuota');
                        return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsPenRules', function($stateParams,PgmsPenRules) {
                               return PgmsPenRules.get({id : $stateParams.id});
                     }]
                }
            });
    });
