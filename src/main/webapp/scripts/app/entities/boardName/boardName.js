'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('boardName', {
                parent: 'entity',
                url: '/boardNames',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.boardName.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/boardName/boardNames.html',
                        controller: 'BoardNameController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('boardName');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('boardName.detail', {
                parent: 'entity',
                url: '/boardName/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.boardName.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/boardName/boardName-detail.html',
                        controller: 'BoardNameDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('boardName');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BoardName', function($stateParams, BoardName) {
                        return BoardName.get({id : $stateParams.id});
                    }]
                }
            })
            .state('boardName.new', {
                parent: 'boardName',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/boardName/boardName-dialog.html',
                        controller: 'BoardNameDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('boardName', null, { reload: true });
                    }, function() {
                        $state.go('boardName');
                    })
                }]
            })
            .state('boardName.edit', {
                parent: 'boardName',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/boardName/boardName-dialog.html',
                        controller: 'BoardNameDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['BoardName', function(BoardName) {
                                return BoardName.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('boardName', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('boardName.delete', {
                parent: 'boardName',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/boardName/boardName-delete-dialog.html',
                        controller: 'BoardNameDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['BoardName', function(BoardName) {
                                return BoardName.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('boardName', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
