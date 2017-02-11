'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeeLoanCheckRegister', {
                parent: 'entity',
                url: '/employeeLoanCheckRegisters',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.employeeLoanCheckRegister.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employeeLoanCheckRegister/employeeLoanCheckRegisters.html',
                        controller: 'EmployeeLoanCheckRegisterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanCheckRegister');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeLoanCheckRegister.detail', {
                parent: 'entity',
                url: '/employeeLoanCheckRegister/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.employeeLoanCheckRegister.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employeeLoanCheckRegister/employeeLoanCheckRegister-detail.html',
                        controller: 'EmployeeLoanCheckRegisterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanCheckRegister');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EmployeeLoanCheckRegister', function($stateParams, EmployeeLoanCheckRegister) {
                        return EmployeeLoanCheckRegister.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employeeLoanCheckRegister.new', {
                parent: 'employeeLoanCheckRegister',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeLoanCheckRegister/employeeLoanCheckRegister-dialog.html',
                        controller: 'EmployeeLoanCheckRegisterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    applicationType: null,
                                    numberOfWithdrawal: null,
                                    checkNumber: null,
                                    tokenNumber: null,
                                    status: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('employeeLoanCheckRegister', null, { reload: true });
                    }, function() {
                        $state.go('employeeLoanCheckRegister');
                    })
                }]
            })
            .state('employeeLoanCheckRegister.edit', {
                parent: 'employeeLoanCheckRegister',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeLoanCheckRegister/employeeLoanCheckRegister-dialog.html',
                        controller: 'EmployeeLoanCheckRegisterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EmployeeLoanCheckRegister', function(EmployeeLoanCheckRegister) {
                                return EmployeeLoanCheckRegister.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employeeLoanCheckRegister', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
