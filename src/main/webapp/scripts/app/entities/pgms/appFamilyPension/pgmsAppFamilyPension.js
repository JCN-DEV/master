'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsAppFamilyPension', {
                parent: 'pgms',
                url: '/pgmsAppFamilyPensions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppFamilyPension.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appFamilyPension/pgmsAppFamilyPensions.html',
                        controller: 'PgmsAppFamilyPensionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppFamilyPension');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsFamilyAppPending', {
                parent: 'pgms',
                url: '/pgmsFamilyApplicationPending',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppFamilyPension.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appFamilyPension/pgmsFamilyAppPending.html',
                        controller: 'PgmsApplicationFamilyRequestController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppFamilyPension');
                        $translatePartialLoader.addPart('pgmsAppFamilyAttach');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('hrmHome');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsAppFamilyPension.detail', {
                parent: 'pgms',
                url: '/pgmsAppFamilyPension/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsAppFamilyPension.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/appFamilyPension/pgmsAppFamilyPension-detail.html',
                        controller: 'PgmsAppFamilyPensionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppFamilyPension');
                        $translatePartialLoader.addPart('pgmsAppFamilyAttach');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsAppFamilyPension', function($stateParams, PgmsAppFamilyPension) {
                        return PgmsAppFamilyPension.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsAppFamilyPension.new', {
                parent: 'pgmsAppFamilyPension',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/appFamilyPension/pgmsAppFamilyPension-dialog.html',
                      controller: 'PgmsAppFamilyPensionDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppFamilyPension');
                        $translatePartialLoader.addPart('pgmsAppFamilyAttach');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    empName: null,
                                    empDepartment: null,
                                    empDesignation: null,
                                    empNid: null,
                                    nomineeStatus: null,
                                    nomineName: null,
                                    nomineDob: null,
                                    nomineGender: null,
                                    nomineRelation: null,
                                    nomineOccupation: null,
                                    nomineDesignation: null,
                                    nominePreAddress: null,
                                    nomineParAddress: null,
                                    nomineNid: null,
                                    nomineContNo: null,
                                    nomineBankName: null,
                                    nomineBranchName: null,
                                    nomineAccNo: null,
                                    applyDate: null,
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
            .state('pgmsAppFamilyPension.edit', {
                parent: 'pgmsAppFamilyPension',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/appFamilyPension/pgmsAppFamilyPension-dialog.html',
                          controller: 'PgmsAppFamilyPensionDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsAppFamilyPension');
                        $translatePartialLoader.addPart('pgmsAppFamilyAttach');
                        return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsAppFamilyPension', function($stateParams,PgmsAppFamilyPension) {
                               return PgmsAppFamilyPension.get({id : $stateParams.id});
                     }]
                }
            });
    });
