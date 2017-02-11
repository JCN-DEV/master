'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mpoCommitteeHistory', {
                parent: 'entity',
                url: '/mpoCommitteeHistorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoCommitteeHistory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoCommitteeHistory/mpoCommitteeHistorys.html',
                        controller: 'MpoCommitteeHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteeHistory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoCommitteeHistory.detail', {
                parent: 'entity',
                url: '/mpoCommitteeHistory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.mpoCommitteeHistory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoCommitteeHistory/mpoCommitteeHistory-detail.html',
                        controller: 'MpoCommitteeHistoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteeHistory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoCommitteeHistory', function($stateParams, MpoCommitteeHistory) {
                        return MpoCommitteeHistory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpoCommitteeHistory.new', {
                parent: 'mpoCommitteeHistory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoCommitteeHistory/mpoCommitteeHistory-dialog.html',
                        controller: 'MpoCommitteeHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    month: null,
                                    year: null,
                                    dateCrated: null,
                                    dateModified: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mpoCommitteeHistory', null, { reload: true });
                    }, function() {
                        $state.go('mpoCommitteeHistory');
                    })
                }]
            })
            .state('mpoCommitteeHistory.edit', {
                parent: 'mpoCommitteeHistory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoCommitteeHistory/mpoCommitteeHistory-dialog.html',
                        controller: 'MpoCommitteeHistoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MpoCommitteeHistory', function(MpoCommitteeHistory) {
                                return MpoCommitteeHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoCommitteeHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('mpoCommitteeHistory.delete', {
                parent: 'mpoCommitteeHistory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoCommitteeHistory/mpoCommitteeHistory-delete-dialog.html',
                        controller: 'MpoCommitteeHistoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MpoCommitteeHistory', function(MpoCommitteeHistory) {
                                return MpoCommitteeHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoCommitteeHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
