'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dlBookReturn', {
                parent: 'entity',
                url: '/dlBookReturns',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlBookReturn.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlBookReturn/dlBookReturns.html',
                        controller: 'DlBookReturnController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookReturn');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dlBookReturn.detail', {
                parent: 'entity',
                url: '/dlBookReturn/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlBookReturn.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlBookReturn/dlBookReturn-detail.html',
                        controller: 'DlBookReturnDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookReturn');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlBookReturn', function($stateParams, DlBookReturn) {
                        return DlBookReturn.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dlBookReturn.new', {
                parent: 'dlBookReturn',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookReturn/dlBookReturn-dialog.html',
                        controller: 'DlBookReturnDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    issueId: null,
                                    createdDate: null,
                                    updatedDate: null,
                                    createdBy: null,
                                    updatedBy: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookReturn', null, { reload: true });
                    }, function() {
                        $state.go('dlBookReturn');
                    })
                }]
            })
            .state('dlBookReturn.edit', {
                parent: 'dlBookReturn',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookReturn/dlBookReturn-dialog.html',
                        controller: 'DlBookReturnDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlBookReturn', function(DlBookReturn) {
                                return DlBookReturn.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookReturn', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dlBookReturn.delete', {
                parent: 'dlBookReturn',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookReturn/dlBookReturn-delete-dialog.html',
                        controller: 'DlBookReturnDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlBookReturn', function(DlBookReturn) {
                                return DlBookReturn.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookReturn', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
