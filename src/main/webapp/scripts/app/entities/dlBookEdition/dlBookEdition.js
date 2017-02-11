'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dlBookEdition', {
                parent: 'entity',
                url: '/dlBookEditions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlBookEdition.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlBookEdition/dlBookEditions.html',
                        controller: 'DlBookEditionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookEdition');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dlBookEdition.detail', {
                parent: 'entity',
                url: '/dlBookEdition/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlBookEdition.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlBookEdition/dlBookEdition-detail.html',
                        controller: 'DlBookEditionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookEdition');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlBookEdition', function($stateParams, DlBookEdition) {
                        return DlBookEdition.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dlBookEdition.new', {
                parent: 'dlBookEdition',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookEdition/dlBookEdition-dialog.html',
                        controller: 'DlBookEditionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    edition: null,
                                    totalCopies: null,
                                    compensation: null,
                                    createDate: null,
                                    updateDate: null,
                                    createBy: null,
                                    updateBy: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookEdition', null, { reload: true });
                    }, function() {
                        $state.go('dlBookEdition');
                    })
                }]
            })
            .state('dlBookEdition.edit', {
                parent: 'dlBookEdition',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookEdition/dlBookEdition-dialog.html',
                        controller: 'DlBookEditionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlBookEdition', function(DlBookEdition) {
                                return DlBookEdition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookEdition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('dlBookEdition.delete', {
                parent: 'dlBookEdition',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookEdition/dlBookEdition-delete-dialog.html',
                        controller: 'DlBookEditionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlBookEdition', function(DlBookEdition) {
                                return DlBookEdition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookEdition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
