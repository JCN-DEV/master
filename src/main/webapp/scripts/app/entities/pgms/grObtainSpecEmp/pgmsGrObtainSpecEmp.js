'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pgmsGrObtainSpecEmp', {
                parent: 'pgms',
                url: '/pgmsGrObtainSpecEmps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsGrObtainSpecEmp.home.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/grObtainSpecEmp/pgmsGrObtainSpecEmps.html',
                        controller: 'PgmsGrObtainSpecEmpController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsGrObtainSpecEmp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pgmsGrObtainSpecEmp.detail', {
                parent: 'pgms',
                url: '/pgmsGrObtainSpecEmp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pgmsGrObtainSpecEmp.detail.title'
                },
                views: {
                    'pgmsHomeView@pgms': {
                        templateUrl: 'scripts/app/entities/pgms/grObtainSpecEmp/pgmsGrObtainSpecEmp-detail.html',
                        controller: 'PgmsGrObtainSpecEmpDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsGrObtainSpecEmp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PgmsGrObtainSpecEmp', function($stateParams, PgmsGrObtainSpecEmp) {
                        return PgmsGrObtainSpecEmp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pgmsGrObtainSpecEmp.new', {
                parent: 'pgmsGrObtainSpecEmp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                      templateUrl: 'scripts/app/entities/pgms/grObtainSpecEmp/pgmsGrObtainSpecEmp-dialog.html',
                      controller: 'PgmsGrObtainSpecEmpDialogController'
                     }
                },
                resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsGrObtainSpecEmp');
                        return $translate.refresh();
                      }],
                      entity: function () {
                              return {
                                    empName: null,
                                    empDesignation: null,
                                    empDepartment: null,
                                    empEndDate: null,
                                    empStatus: false,
                                    empWrkingYear: null,
                                    amountAsGr: null,
                                    obtainDate: null,
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
            .state('pgmsGrObtainSpecEmp.edit', {
                parent: 'pgmsGrObtainSpecEmp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                     'pgmsHomeView@pgms': {
                          templateUrl: 'scripts/app/entities/pgms/grObtainSpecEmp/pgmsGrObtainSpecEmp-dialog.html',
                          controller: 'PgmsGrObtainSpecEmpDialogController'
                     }
                },
                resolve: {
                     translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pgmsGrObtainSpecEmp');
                        return $translate.refresh();
                     }],
                     entity: ['$stateParams','PgmsGrObtainSpecEmp', function($stateParams,PgmsGrObtainSpecEmp) {
                               return PgmsGrObtainSpecEmp.get({id : $stateParams.id});
                     }]
                }
            });
    });
