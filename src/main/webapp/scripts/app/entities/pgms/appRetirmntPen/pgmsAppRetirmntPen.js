'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsAppRetirmntPen', {
                parent: 'pgms',
                url: '/pgmsAppRetirmntPens',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppRetirmntPen.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appRetirmntPen/pgmsAppRetirmntPens.html',
                        controller: 'PgmsAppRetirmntPenController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntPen');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsRetiremnetPensionAppPending', {
                parent: 'pgms',
                url: '/pgmsRetiremnetPensionApplicationPending',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppRetirmntPen.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appRetirmntPen/pgmsRetiremnetPensionAppPending.html',
                        controller: 'pgmsAppRetirmntPensionRequestController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntPen');
                        $translatePartialLoader.addPart('pgmsAppRetirmntNmine');
                        $translatePartialLoader.addPart('pgmsAppRetirmntAttach');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('hrmHome');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsAppRetirmntPen.detail', {
                parent: 'pgms',
                url: '/pgmsAppRetirmntPen/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppRetirmntPen.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appRetirmntPen/pgmsAppRetirmntPen-detail.html',
                        controller: 'PgmsAppRetirmntPenDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntPen');
                        $translatePartialLoader.addPart('pgmsAppRetirmntNmine');
                        $translatePartialLoader.addPart('pgmsAppRetirmntAttach');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsAppRetirmntPen', function($stateParams, PgmsAppRetirmntPen) {
                        return PgmsAppRetirmntPen.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsAppRetirmntPen.new', {
                parent: 'pgmsAppRetirmntPen',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/appRetirmntPen/pgmsAppRetirmntPen-dialog.html',
                      controller: 'PgmsAppRetirmntPenDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntPen');
                        $translatePartialLoader.addPart('pgmsAppRetirmntNmine');
                        $translatePartialLoader.addPart('pgmsAppRetirmntAttach');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    withdrawnType: null,
                                    applicationType: null,
                                    rcvGrStatus: false,
                                    workDuration: null,
                                    emergencyContact: null,
                                    bankAccStatus: false,
                                    bankName: null,
                                    bankAcc: null,
                                    bankBranch: null,
                                    appDate: null,
                                    appNo: null,
                                    aprvStatus: 'Pending',
                                    aprvDate: null,
                                    aprvComment: null,
                                    aprvBy: null,
                                    activeStatus: null,
                                    createDate: null,
                                    createBy: null,
                                    updateBy: null,
                                    updateDate: null,
                                    id: null
                              }
                       }
                }
            })
            .state('pgmsAppRetirmntPen.edit', {
                parent: 'pgmsAppRetirmntPen',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/appRetirmntPen/pgmsAppRetirmntPen-dialog.html',
                          controller: 'PgmsAppRetirmntPenDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntPen');
                        $translatePartialLoader.addPart('pgmsAppRetirmntNmine');
                        $translatePartialLoader.addPart('pgmsAppRetirmntAttach');
                       return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsAppRetirmntPen', function($stateParams,PgmsAppRetirmntPen) {
                               return PgmsAppRetirmntPen.get({id : $stateParams.id});
                     }]
                }
            });
    });
