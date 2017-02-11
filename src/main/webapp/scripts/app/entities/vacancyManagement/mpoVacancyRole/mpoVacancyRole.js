'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mpoVacancyRole', {
                parent: 'mpo',
                url: '/mpoVacancyRoles',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.mpoVacancyRole.home.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRole/mpoVacancyRoles.html',
                        controller: 'MpoVacancyRoleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoVacancyRole');
                        $translatePartialLoader.addPart('vacancyRoleType');
                        $translatePartialLoader.addPart('mpoVacancyRoleDesgnations');
                        $translatePartialLoader.addPart('mpoVacancyRoleTrade');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoVacancyRole.detail', {
                parent: 'mpoVacancyRole',
                url: '/mpoVacancyRole/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.mpoVacancyRole.detail.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRole/mpoVacancyRole-detail.html',
                        controller: 'MpoVacancyRoleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoVacancyRole');
                        $translatePartialLoader.addPart('vacancyRoleType');
                        $translatePartialLoader.addPart('mpoVacancyRoleDesgnations');
                        $translatePartialLoader.addPart('mpoVacancyRoleTrade');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoVacancyRole', function($stateParams, MpoVacancyRole) {
                        return MpoVacancyRole.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpoVacancyRole.new', {
                parent: 'mpoVacancyRole',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRole/mpoVacancyRole-dialog.html',
                        controller: 'MpoVacancyRoleDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoVacancyRole');
                        $translatePartialLoader.addPart('vacancyRoleType');
                        $translatePartialLoader.addPart('mpoVacancyRoleDesgnations');
                        $translatePartialLoader.addPart('mpoVacancyRoleTrade');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoVacancyRole', function($stateParams, MpoVacancyRole) {
                       // return MpoVacancyRole.get({id : $stateParams.id});
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRole/mpoVacancyRole-dialog.html',
                        controller: 'MpoVacancyRoleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    crateDate: null,
                                    updateDate: null,
                                    status: null,
                                    totalTrade: null,
                                    vacancyRoleType: null,
                                    totalVacancy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mpoVacancyRole', null, { reload: true });
                    }, function() {
                        $state.go('mpoVacancyRole');
                    })
                }]*/
            })
            .state('mpoVacancyRole.edit', {
                parent: 'mpoVacancyRole',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRole/mpoVacancyRole-dialog.html',
                        controller: 'MpoVacancyRoleDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoVacancyRole');
                        $translatePartialLoader.addPart('vacancyRoleType');
                        $translatePartialLoader.addPart('mpoVacancyRoleDesgnations');
                        $translatePartialLoader.addPart('mpoVacancyRoleTrade');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoVacancyRole', function($stateParams, MpoVacancyRole) {
                        // return MpoVacancyRole.get({id : $stateParams.id});
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRole/mpoVacancyRole-dialog.html',
                        controller: 'MpoVacancyRoleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MpoVacancyRole', function(MpoVacancyRole) {
                                return MpoVacancyRole.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoVacancyRole', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('mpoVacancyRole.delete', {
                parent: 'mpoVacancyRole',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRole/mpoVacancyRole-delete-dialog.html',
                        controller: 'MpoVacancyRoleDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MpoVacancyRole', function(MpoVacancyRole) {
                                return MpoVacancyRole.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoVacancyRole', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
