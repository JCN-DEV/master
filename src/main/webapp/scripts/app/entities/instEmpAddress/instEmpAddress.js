'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmpAddress', {
                parent: 'entity',
                url: '/instEmpAddresss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmpAddress.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmpAddress/instEmpAddresss.html',
                        controller: 'InstEmpAddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpAddress');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmpAddress.detail', {
                parent: 'entity',
                url: '/instEmpAddress/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmpAddress.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmpAddress/instEmpAddress-detail.html',
                        controller: 'InstEmpAddressDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpAddress');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmpAddress', function($stateParams, InstEmpAddress) {
                        return InstEmpAddress.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmpAddress.new', {
                parent: 'instEmpAddress',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpAddress/instEmpAddress-dialog.html',
                        controller: 'InstEmpAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    address: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpAddress', null, { reload: true });
                    }, function() {
                        $state.go('instEmpAddress');
                    })
                }]
            })
            .state('instEmpAddress.edit', {
                parent: 'instEmpAddress',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpAddress/instEmpAddress-dialog.html',
                        controller: 'InstEmpAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmpAddress', function(InstEmpAddress) {
                                return InstEmpAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmpAddress.delete', {
                parent: 'instEmpAddress',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpAddress/instEmpAddress-delete-dialog.html',
                        controller: 'InstEmpAddressDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmpAddress', function(InstEmpAddress) {
                                return InstEmpAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
