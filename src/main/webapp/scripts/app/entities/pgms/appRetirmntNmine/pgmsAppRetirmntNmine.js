'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsAppRetirmntNmine', {
                parent: 'pgms',
                url: '/pgmsAppRetirmntNmines',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppRetirmntNmine.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appRetirmntNmine/pgmsAppRetirmntNmines.html',
                        controller: 'PgmsAppRetirmntNmineController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntNmine');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsAppRetirmntNmine.detail', {
                parent: 'pgms',
                url: '/pgmsAppRetirmntNmine/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppRetirmntNmine.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appRetirmntNmine/pgmsAppRetirmntNmine-detail.html',
                        controller: 'PgmsAppRetirmntNmineDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntNmine');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsAppRetirmntNmine', function($stateParams, PgmsAppRetirmntNmine) {
                        return PgmsAppRetirmntNmine.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsAppRetirmntNmine.new', {
                parent: 'pgmsAppRetirmntNmine',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/appRetirmntNmine/pgmsAppRetirmntNmine-dialog.html',
                      controller: 'PgmsAppRetirmntNmineDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntNmine');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    appRetirmntPenId: null,
                                    nomineeStatus: null,
                                    nomineeName: null,
                                    gender: null,
                                    relation: null,
                                    dateOfBirth: null,
                                    presentAddress: null,
                                    nid: null,
                                    occupation: null,
                                    designation: null,
                                    maritalStatus: null,
                                    mobileNo: null,
                                    getPension: null,
                                    hrNomineeInfo: null,
                                    id: null
                              }
                       }
                }
           })
            .state('pgmsAppRetirmntNmine.edit', {
                parent: 'pgmsAppRetirmntNmine',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/appRetirmntNmine/pgmsAppRetirmntNmine-dialog.html',
                          controller: 'PgmsAppRetirmntNmineDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppRetirmntNmine');
                       return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsAppRetirmntNmine', function($stateParams, PgmsAppRetirmntNmine) {
                               return PgmsAppRetirmntNmine.get({id : $stateParams.id});
                     }]
                }
            });
    });
