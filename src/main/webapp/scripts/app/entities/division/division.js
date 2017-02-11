'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('division', {
                parent: 'entity',
                url: '/divisions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.division.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/division/general-setup.html',
                        controller: 'DivisionController'
                    },
                    'generalView@division':{
                          templateUrl: 'scripts/app/entities/division/divisions.html',
                          controller: 'DivisionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('division');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('division.detail', {
                parent: 'division',
                url: '/division/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.division.detail.title'
                },
                views: {
                    'generalView@division': {
                        templateUrl: 'scripts/app/entities/division/division-detail.html',
                        controller: 'DivisionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('division');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Division', function($stateParams, Division) {
                        return Division.get({id : $stateParams.id});
                    }]
                }
            })
            .state('division.new', {
                parent: 'division',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/division/division-dialog.html',
                        controller: 'DivisionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    bnName: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('division', null, { reload: true });
                    }, function() {
                        $state.go('division');
                    })
                }]
            })
            .state('division.edit', {
                parent: 'division',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/division/division-dialog.html',
                        controller: 'DivisionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Division', function(Division) {
                                return Division.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('division', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('division.delete', {
                parent: 'division',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/division/division-delete-dialog.html',
                        controller: 'DivisionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Division', function(Division) {
                                return Division.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('division', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
