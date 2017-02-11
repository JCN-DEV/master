'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsAppFamilyAttach', {
                parent: 'pgms',
                url: '/pgmsAppFamilyAttachs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppFamilyAttach.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appFamilyAttach/pgmsAppFamilyAttachs.html',
                        controller: 'PgmsAppFamilyAttachController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppFamilyAttach');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsAppFamilyAttach.detail', {
                parent: 'pgms',
                url: '/pgmsAppFamilyAttach/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppFamilyAttach.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appFamilyAttach/pgmsAppFamilyAttach-detail.html',
                        controller: 'PgmsAppFamilyAttachDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppFamilyAttach');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsAppFamilyAttach', function($stateParams, PgmsAppFamilyAttach) {
                        return PgmsAppFamilyAttach.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsAppFamilyAttach.new', {
                parent: 'pgmsAppFamilyAttach',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/appFamilyAttach/pgmsAppFamilyAttach-dialog.html',
                      controller: 'PgmsAppFamilyAttachDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppFamilyAttach');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    appFamilyPenId: null,
                                    attachment: null,
                                    attachmentContentType: null,
                                    attachDocName: null,
                                    attachDocType: null,
                                    id: null
                              }
                       }
                }
            })
            .state('pgmsAppFamilyAttach.edit', {
                parent: 'pgmsAppFamilyAttach',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/appFamilyAttach/pgmsAppFamilyAttach-dialog.html',
                          controller: 'PgmsAppFamilyAttachDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppFamilyAttach');
                        return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsAppFamilyAttach', function($stateParams,PgmsAppFamilyAttach) {
                               return PgmsAppFamilyAttach.get({id : $stateParams.id});
                     }]
                }
            });
    });
