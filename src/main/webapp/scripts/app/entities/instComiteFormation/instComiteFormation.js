'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instComiteFormation', {
                parent: 'entity',
                url: '/instComiteFormations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instComiteFormation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instComiteFormation/instComiteFormations.html',
                        controller: 'InstComiteFormationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instComiteFormation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instComiteFormation.detail', {
                parent: 'entity',
                url: '/instComiteFormation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instComiteFormation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instComiteFormation/instComiteFormation-detail.html',
                        controller: 'InstComiteFormationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instComiteFormation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstComiteFormation', function($stateParams, InstComiteFormation) {
                        return InstComiteFormation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instComiteFormation.new', {
                parent: 'instComiteFormation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instComiteFormation/instComiteFormation-dialog.html',
                        controller: 'InstComiteFormationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    comiteName: null,
                                    comiteType: null,
                                    address: null,
                                    timeFrom: null,
                                    timeTo: null,
                                    formationDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instComiteFormation', null, { reload: true });
                    }, function() {
                        $state.go('instComiteFormation');
                    })
                }]
            })
            .state('instComiteFormation.edit', {
                parent: 'instComiteFormation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instComiteFormation/instComiteFormation-dialog.html',
                        controller: 'InstComiteFormationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstComiteFormation', function(InstComiteFormation) {
                                return InstComiteFormation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instComiteFormation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instComiteFormation.delete', {
                parent: 'instComiteFormation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instComiteFormation/instComiteFormation-delete-dialog.html',
                        controller: 'InstComiteFormationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstComiteFormation', function(InstComiteFormation) {
                                return InstComiteFormation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instComiteFormation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
