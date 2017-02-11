'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeeLoanInfo.employeeLoanTypeSetup', {
                parent: 'employeeLoanInfo',
                url: '/employeeLoanTypeSetups',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.employeeLoanTypeSetup.home.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanTypeSetup/employeeLoanTypeSetups.html',
                        controller: 'EmployeeLoanTypeSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanTypeSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeLoanInfo.employeeLoanTypeSetup.detail', {
                parent: 'employeeLoanInfo.employeeLoanTypeSetup',
                url: '/employeeLoanTypeSetup/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.employeeLoanTypeSetup.detail.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanTypeSetup/employeeLoanTypeSetup-detail.html',
                        controller: 'EmployeeLoanTypeSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanTypeSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EmployeeLoanTypeSetup', function($stateParams, EmployeeLoanTypeSetup) {
                        return EmployeeLoanTypeSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employeeLoanInfo.employeeLoanTypeSetup.new', {
                parent: 'employeeLoanInfo.employeeLoanTypeSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanTypeSetup/employeeLoanTypeSetup-dialog.html',
                        controller: 'EmployeeLoanTypeSetupDialogController',
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                           loanTypeCode: null,
                           loanTypeName: null,
                           status: null,
                           createDate: null,
                           createBy: null,
                           updateDate: null,
                           updateBy: null,
                           id: null
                        };
                    }
                }

            })
            .state('employeeLoanInfo.employeeLoanTypeSetup.edit', {
                parent: 'employeeLoanInfo.employeeLoanTypeSetup',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                 'employeeLoanView@employeeLoanInfo': {
                    templateUrl: 'scripts/app/entities/ELMS/employeeLoanTypeSetup/employeeLoanTypeSetup-dialog.html',
                    controller: 'EmployeeLoanTypeSetupDialogController',
                 }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanTypeSetup');
                        return $translate.refresh();
                }],
                entity: ['$stateParams', 'EmployeeLoanTypeSetup', function($stateParams, EmployeeLoanTypeSetup) {
                    return EmployeeLoanTypeSetup.get({id : $stateParams.id});
                }]
                }
            })

            .state('employeeLoanInfo.employeeLoanTypeSetup.delete', {
                parent: 'employeeLoanInfo.employeeLoanTypeSetup',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanTypeSetup/employeeLoanTypeSetup-delete-dialog.html',
                        controller: 'EmployeeLoanTypeSetupDeleteDialogController',
                        size: 'md',
                        resolve: {
                            entity: ['EmployeeLoanTypeSetup', function(EmployeeLoanTypeSetup) {
                                return EmployeeLoanTypeSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employeeLoanTypeSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
