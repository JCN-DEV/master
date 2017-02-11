'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmplTraining', {
                parent: 'entity',
                url: '/instEmplTrainings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplTraining.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplTraining/instEmplTrainings.html',
                        controller: 'InstEmplTrainingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplTraining');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmplTraining.detail', {
                parent: 'entity',
                url: '/instEmplTraining/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplTraining.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplTraining/instEmplTraining-detail.html',
                        controller: 'InstEmplTrainingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplTraining');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmplTraining', function($stateParams, InstEmplTraining) {
                        return InstEmplTraining.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmplTraining.new', {
                parent: 'instEmplTraining',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplTraining/instEmplTraining-dialog.html',
                        controller: 'InstEmplTrainingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    subjectsCoverd: null,
                                    location: null,
                                    startedDate: null,
                                    endedDate: null,
                                    result: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplTraining', null, { reload: true });
                    }, function() {
                        $state.go('instEmplTraining');
                    })
                }]
            })
            .state('instEmplTraining.edit', {
                parent: 'instEmplTraining',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplTraining/instEmplTraining-dialog.html',
                        controller: 'InstEmplTrainingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmplTraining', function(InstEmplTraining) {
                                return InstEmplTraining.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplTraining', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmplTraining.delete', {
                parent: 'instEmplTraining',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplTraining/instEmplTraining-delete-dialog.html',
                        controller: 'InstEmplTrainingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmplTraining', function(InstEmplTraining) {
                                return InstEmplTraining.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplTraining', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
