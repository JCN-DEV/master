'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instMemShip', {
                parent: 'entity',
                url: '/instMemShips',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instMemShip.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instMemShip/instMemShips.html',
                        controller: 'InstMemShipController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instMemShip');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instMemShip.detail', {
                parent: 'entity',
                url: '/instMemShip/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instMemShip.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instMemShip/instMemShip-detail.html',
                        controller: 'InstMemShipDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instMemShip');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstMemShip', function($stateParams, InstMemShip) {
                        return InstMemShip.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instMemShip.new', {
                parent: 'instMemShip',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instMemShip/instMemShip-dialog.html',
                        controller: 'InstMemShipDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    fullName: null,
                                    dob: null,
                                    gender: null,
                                    address: null,
                                    email: null,
                                    contact: null,
                                    designation: null,
                                    orgName: null,
                                    orgAdd: null,
                                    orgContact: null,
                                    date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instMemShip', null, { reload: true });
                    }, function() {
                        $state.go('instMemShip');
                    })
                }]
            })
            .state('instMemShip.edit', {
                parent: 'instMemShip',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instMemShip/instMemShip-dialog.html',
                        controller: 'InstMemShipDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstMemShip', function(InstMemShip) {
                                return InstMemShip.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instMemShip', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instMemShip.delete', {
                parent: 'instMemShip',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instMemShip/instMemShip-delete-dialog.html',
                        controller: 'InstMemShipDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstMemShip', function(InstMemShip) {
                                return InstMemShip.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instMemShip', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
