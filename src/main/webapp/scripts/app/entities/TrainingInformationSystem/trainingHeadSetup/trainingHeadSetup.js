'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingInfo.trainingHeadSetup', {
                parent: 'trainingInfo',
                url: '/trainingHeadSetups',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingHeadSetup.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingHeadSetup/trainingHeadSetups.html',
                        controller: 'TrainingHeadSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingHeadSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trainingInfo.trainingHeadSetup.detail', {
                parent: 'trainingInfo.trainingHeadSetup',
                url: '/trainingHeadSetup/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingHeadSetup.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingHeadSetup/trainingHeadSetup-detail.html',
                        controller: 'TrainingHeadSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingHeadSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingHeadSetup', function($stateParams, TrainingHeadSetup) {
                        return TrainingHeadSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trainingInfo.trainingHeadSetup.new', {
                parent: 'trainingInfo.trainingHeadSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingHeadSetup/trainingHeadSetup-dialog.html',
                        controller: 'TrainingHeadSetupDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            headName: null,
                            description: null,
                            status: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingHeadSetup/trainingHeadSetup-dialog.html',
                        controller: 'TrainingHeadSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    headName: null,
                                    description: null,
                                    status: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('trainingHeadSetup', null, { reload: true });
                    }, function() {
                        $state.go('trainingHeadSetup');
                    })
                }]*/
            })

            .state('trainingInfo.trainingHeadSetup.edit', {
                parent: 'trainingInfo.trainingHeadSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingHeadSetup.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingHeadSetup/trainingHeadSetup-dialog.html',
                        controller: 'TrainingHeadSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingHeadSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingHeadSetup', function($stateParams, TrainingHeadSetup) {
                        return TrainingHeadSetup.get({id : $stateParams.id});
                    }]
                }
            })

            /*.state('trainingInfo.trainingHeadSetup.edit', {
                parent: 'trainingInfo.trainingHeadSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingHeadSetup/trainingHeadSetup-dialog.html',
                        controller: 'TrainingHeadSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TrainingHeadSetup', function(TrainingHeadSetup) {
                                return TrainingHeadSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trainingHeadSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/;
    });
