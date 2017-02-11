'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mpoVacancyRoleDesgnations', {
                parent: 'entity',
                url: '/mpoVacancyRoleDesgnationss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoVacancyRoleDesgnations.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRoleDesgnations/mpoVacancyRoleDesgnationss.html',
                        controller: 'MpoVacancyRoleDesgnationsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoVacancyRoleDesgnations');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoVacancyRoleDesgnations.detail', {
                parent: 'entity',
                url: '/mpoVacancyRoleDesgnations/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoVacancyRoleDesgnations.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRoleDesgnations/mpoVacancyRoleDesgnations-detail.html',
                        controller: 'MpoVacancyRoleDesgnationsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoVacancyRoleDesgnations');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoVacancyRoleDesgnations', function($stateParams, MpoVacancyRoleDesgnations) {
                        return MpoVacancyRoleDesgnations.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpoVacancyRoleDesgnations.new', {
                parent: 'mpoVacancyRoleDesgnations',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRoleDesgnations/mpoVacancyRoleDesgnations-dialog.html',
                        controller: 'MpoVacancyRoleDesgnationsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    crateDate: null,
                                    updateDate: null,
                                    status: null,
                                    totalPost: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mpoVacancyRoleDesgnations', null, { reload: true });
                    }, function() {
                        $state.go('mpoVacancyRoleDesgnations');
                    })
                }]
            })
            .state('mpoVacancyRoleDesgnations.edit', {
                parent: 'mpoVacancyRoleDesgnations',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRoleDesgnations/mpoVacancyRoleDesgnations-dialog.html',
                        controller: 'MpoVacancyRoleDesgnationsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MpoVacancyRoleDesgnations', function(MpoVacancyRoleDesgnations) {
                                return MpoVacancyRoleDesgnations.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoVacancyRoleDesgnations', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('mpoVacancyRoleDesgnations.delete', {
                parent: 'mpoVacancyRoleDesgnations',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRoleDesgnations/mpoVacancyRoleDesgnations-delete-dialog.html',
                        controller: 'MpoVacancyRoleDesgnationsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MpoVacancyRoleDesgnations', function(MpoVacancyRoleDesgnations) {
                                return MpoVacancyRoleDesgnations.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoVacancyRoleDesgnations', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
