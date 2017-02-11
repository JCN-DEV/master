'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingSummary', {
                parent: 'entity',
                url: '/trainingSummarys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.trainingSummary.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/trainingSummary/trainingSummarys.html',
                        controller: 'TrainingSummaryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingSummary');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trainingSummary.detail', {
                parent: 'entity',
                url: '/trainingSummary/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.trainingSummary.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/trainingSummary/trainingSummary-detail.html',
                        controller: 'TrainingSummaryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingSummary');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingSummary', function($stateParams, TrainingSummary) {
                        return TrainingSummary.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trainingSummary.new', {
                parent: 'trainingSummary',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/trainingSummary/trainingSummary-dialog.html',
                        controller: 'TrainingSummaryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    topicsCovered: null,
                                    institute: null,
                                    country: null,
                                    location: null,
                                    startedDate: null,
                                    endedDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('trainingSummary', null, { reload: true });
                    }, function() {
                        $state.go('trainingSummary');
                    })
                }]
            })
            .state('trainingSummary.edit', {
                parent: 'trainingSummary',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/trainingSummary/trainingSummary-dialog.html',
                        controller: 'TrainingSummaryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TrainingSummary', function(TrainingSummary) {
                                return TrainingSummary.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trainingSummary', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
