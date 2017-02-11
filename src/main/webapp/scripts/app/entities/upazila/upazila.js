'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('division.upazila', {
                parent: 'division',
                url: '/upazilas',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.upazila.home.title'
                },
                views: {
                    'generalView@division': {
                        templateUrl: 'scripts/app/entities/upazila/upazilas.html',
                        controller: 'UpazilaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('upazila');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('division.upazila.detail', {
                parent: 'division.upazila',
                url: '/upazila/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.upazila.detail.title'
                },
                views: {
                    'generalView@division': {
                        templateUrl: 'scripts/app/entities/upazila/upazila-detail.html',
                        controller: 'UpazilaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('upazila');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Upazila', function($stateParams, Upazila) {
                        return Upazila.get({id : $stateParams.id});
                    }]
                }
            })
            .state('division.upazila.new', {
                parent: 'division.upazila',
                url: '/new',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/upazila/upazila-dialog.html',
                        controller: 'UpazilaDialogController',
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
                        $state.go('division.upazila', null, { reload: true });
                    }, function() {
                        $state.go('division.upazila');
                    })
                }]
            })
            .state('division.upazila.edit', {
                parent: 'division.upazila',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/upazila/upazila-dialog.html',
                        controller: 'UpazilaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Upazila', function(Upazila) {
                                return Upazila.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('division.upazila', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('division.upazila.delete', {
                parent: 'division.upazila',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/upazila/upazila-delete-dialog.html',
                        controller: 'UpazilaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Upazila', function(Upazila) {
                                return Upazila.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('division.upazila', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
