'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsAppRetirmntAttach', {
                parent: 'pgms',
                url: '/pgmsAppRetirmntAttachs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppRetirmntAttach.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appRetirmntAttach/pgmsAppRetirmntAttachs.html',
                        controller: 'PgmsAppRetirmntAttachController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntAttach');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsAppRetirmntAttach.detail', {
                parent: 'pgms',
                url: '/pgmsAppRetirmntAttach/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppRetirmntAttach.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appRetirmntAttach/pgmsAppRetirmntAttach-detail.html',
                        controller: 'PgmsAppRetirmntAttachDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntAttach');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsAppRetirmntAttach', function($stateParams, PgmsAppRetirmntAttach) {
                        return PgmsAppRetirmntAttach.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsAppRetirmntAttach.new', {
                parent: 'pgmsAppRetirmntAttach',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/appRetirmntAttach/pgmsAppRetirmntAttach-dialog.html',
                      controller: 'PgmsAppRetirmntAttachDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntAttach');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    appRetirmntPenId: null,
                                    attachment: null,
                                    attachmentContentType: null,
                                    attachDocName: null,
                                    attachDocType: null,
                                    id: null
                              }
                      }
                }
            })
            .state('pgmsAppRetirmntAttach.edit', {
                parent: 'pgmsAppRetirmntAttach',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/appRetirmntAttach/pgmsAppRetirmntAttach-dialog.html',
                          controller: 'PgmsAppRetirmntAttachDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntAttach');
                       return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsAppRetirmntAttach', function($stateParams, PgmsAppRetirmntAttach) {
                               return PgmsAppRetirmntAttach.get({id : $stateParams.id});
                     }]
                }
            });
    });
