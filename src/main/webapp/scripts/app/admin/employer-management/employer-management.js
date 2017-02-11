'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employer-management', {
                parent: 'admin',
                url: '/company-management',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_JPADMIN'],
                    pageTitle: 'employer-management.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/employer-management/employer-management.html',
                        controller: 'EmployerManagementController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer-management');
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('tempEmployerStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employer.approve', {
                parent: 'employer-management',
                url: '/{id}/approve',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_JPADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/admin/employer-management/employer-approve-dialog.html',
                        controller: 'EmployerApproveController',
                        size: 'md',
                        resolve: {
                            entity: ['TempEmployer', function (TempEmployer) {
                                return TempEmployer.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('employer-management', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            })
            .state('employer.reject', {
                parent: 'employer-management',
                url: '/{id}/reject',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_JPADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/admin/employer-management/employer-reject-dialog.html',
                        controller: 'EmployerRejectController',
                        size: 'md',
                        resolve: {
                            entity: ['TempEmployer', function (TempEmployer) {
                                return TempEmployer.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('employer-management', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            })
            .state('employer.delete', {
                parent: 'employer-management',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_JPADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/admin/employer-management/employer-delete-dialog.html',
                        controller: 'EmployerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TempEmployer', function (TempEmployer) {
                                return TempEmployer.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('employer-management', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            })
            .state('employer.deactivate', {
                parent: 'employer-management',
                url: '/{id}/deactivate',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_JPADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/admin/employer-management/employer-deactivate-dialog.html',
                        controller: 'EmployerDeactivateController',
                        size: 'md',
                        resolve: {
                            entity: ['TempEmployer', function (TempEmployer) {
                                return TempEmployer.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('employer-management', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            })
            .state('employer.activate', {
                parent: 'employer-management',
                url: '/{id}/activate',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_JPADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/admin/employer-management/employer-activate-dialog.html',
                        controller: 'EmployerActivateController',
                        size: 'md',
                        resolve: {
                            entity: ['TempEmployer', function (TempEmployer) {
                                return TempEmployer.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('employer-management', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            })
            .state('employer-management-detail', {
                parent: 'admin',
                url: '/employer-management/:login',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_JPADMIN'],
                    pageTitle: 'employer-management.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/employer-management/employer-management-detail.html',
                        controller: 'employerManagementDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employer.management');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employer-management.new', {
                parent: 'employer-management',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_JPADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/admin/employer-management/employer-management-dialog.html',
                        controller: 'employerManagementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null, login: null, firstName: null, lastName: null, email: null,
                                    activated: null, langKey: null, createdBy: null, createdDate: null,
                                    lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                                    resetKey: null, authorities: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('employer-management', null, { reload: true });
                    }, function() {
                        $state.go('employer-management');
                    })
                }]
            })
            .state('employer-management.edit', {
                parent: 'employer-management',
                url: '/{login}/edit',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_JPADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/admin/employer-management/employer-management-dialog.html',
                        controller: 'employerManagementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['employer', function(employer) {
                                return employer.get({login : $stateParams.login});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employer-management', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
