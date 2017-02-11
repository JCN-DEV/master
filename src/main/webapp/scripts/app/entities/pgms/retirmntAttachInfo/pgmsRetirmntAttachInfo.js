'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsRetirmntAttachInfo', {
                parent: 'pgms',
                url: '/pgmsRetirmntAttachInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsRetirmntAttachInfo.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/retirmntAttachInfo/pgmsRetirmntAttachInfos.html',
                        controller: 'PgmsRetirmntAttachInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsRetirmntAttachInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsRetirmntAttachInfo.detail', {
                parent: 'pgms',
                url: '/pgmsRetirmntAttachInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsRetirmntAttachInfo.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/retirmntAttachInfo/pgmsRetirmntAttachInfo-detail.html',
                        controller: 'PgmsRetirmntAttachInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsRetirmntAttachInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsRetirmntAttachInfo', function($stateParams, PgmsRetirmntAttachInfo) {
                        return PgmsRetirmntAttachInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsRetirmntAttachInfo.new', {
                parent: 'pgmsRetirmntAttachInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/retirmntAttachInfo/pgmsRetirmntAttachInfo-dialog.html',
                      controller: 'PgmsRetirmntAttachInfoDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsRetirmntAttachInfo');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    attachName: null,
                                    priority: null,
                                    attachType: null,
                                    activeStatus: true,
                                    createDate: null,
                                    createBy: null,
                                    updateBy: null,
                                    updateDate: null,
                                    id: null
                              }
                       }
                }
            })
            .state('pgmsRetirmntAttachInfo.edit', {
                parent: 'pgmsRetirmntAttachInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/retirmntAttachInfo/pgmsRetirmntAttachInfo-dialog.html',
                          controller: 'PgmsRetirmntAttachInfoDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsRetirmntAttachInfo');
                       return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsRetirmntAttachInfo', function($stateParams,PgmsRetirmntAttachInfo) {
                               return PgmsRetirmntAttachInfo.get({id : $stateParams.id});
                     }]
                }
            });
    });
