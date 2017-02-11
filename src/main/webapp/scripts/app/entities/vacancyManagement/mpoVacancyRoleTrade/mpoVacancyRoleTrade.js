'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mpoVacancyRoleTrade', {
                parent: 'entity',
                url: '/mpoVacancyRoleTrades',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoVacancyRoleTrade.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRoleTrade/mpoVacancyRoleTrades.html',
                        controller: 'MpoVacancyRoleTradeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoVacancyRoleTrade');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoVacancyRoleTrade.detail', {
                parent: 'entity',
                url: '/mpoVacancyRoleTrade/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoVacancyRoleTrade.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRoleTrade/mpoVacancyRoleTrade-detail.html',
                        controller: 'MpoVacancyRoleTradeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoVacancyRoleTrade');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoVacancyRoleTrade', function($stateParams, MpoVacancyRoleTrade) {
                        return MpoVacancyRoleTrade.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpoVacancyRoleTrade.new', {
                parent: 'mpoVacancyRoleTrade',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRoleTrade/mpoVacancyRoleTrade-dialog.html',
                        controller: 'MpoVacancyRoleTradeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    crateDate: null,
                                    updateDate: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mpoVacancyRoleTrade', null, { reload: true });
                    }, function() {
                        $state.go('mpoVacancyRoleTrade');
                    })
                }]
            })
            .state('mpoVacancyRoleTrade.edit', {
                parent: 'mpoVacancyRoleTrade',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRoleTrade/mpoVacancyRoleTrade-dialog.html',
                        controller: 'MpoVacancyRoleTradeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MpoVacancyRoleTrade', function(MpoVacancyRoleTrade) {
                                return MpoVacancyRoleTrade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoVacancyRoleTrade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('mpoVacancyRoleTrade.delete', {
                parent: 'mpoVacancyRoleTrade',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancyManagement/mpoVacancyRoleTrade/mpoVacancyRoleTrade-delete-dialog.html',
                        controller: 'MpoVacancyRoleTradeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MpoVacancyRoleTrade', function(MpoVacancyRoleTrade) {
                                return MpoVacancyRoleTrade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoVacancyRoleTrade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
