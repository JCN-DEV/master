'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingInfo.trainingCategorySetup', {
                parent: 'trainingInfo',
                url: '/trainingCategorySetups',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingCategorySetup.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingCategorySetup/trainingCategorySetups.html',
                        controller: 'TrainingCategorySetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingCategorySetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trainingInfo.trainingCategorySetup.detail', {
                parent: 'trainingInfo.trainingCategorySetup',
                url: '/trainingCategorySetup/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingCategorySetup.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingCategorySetup/trainingCategorySetup-detail.html',
                        controller: 'TrainingCategorySetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingCategorySetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingCategorySetup', function($stateParams, TrainingCategorySetup) {
                        return TrainingCategorySetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trainingInfo.trainingCategorySetup.new', {
                parent: 'trainingInfo.trainingCategorySetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingCategorySetup/trainingCategorySetup-dialog.html',
                        controller: 'TrainingCategorySetupDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            categoryName: null,
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
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingCategorySetup/trainingCategorySetup-dialog.html',
                        controller: 'TrainingCategorySetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    categoryName: null,
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
                        $state.go('trainingCategorySetup', null, { reload: true });
                    }, function() {
                        $state.go('trainingCategorySetup');
                    })
                }]*/
            })

            .state('trainingInfo.trainingCategorySetup.edit', {
                parent: 'trainingInfo.trainingCategorySetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingCategorySetup.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingCategorySetup/trainingCategorySetup-dialog.html',
                        controller: 'TrainingCategorySetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingCategorySetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingCategorySetup', function($stateParams, TrainingCategorySetup) {
                        return TrainingCategorySetup.get({id : $stateParams.id});
                    }]
                }
            })

            /*.state('trainingInfo.trainingCategorySetup.edit', {
                parent: 'trainingInfo.trainingCategorySetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingCategorySetup/trainingCategorySetup-dialog.html',
                        controller: 'TrainingCategorySetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TrainingCategorySetup', function(TrainingCategorySetup) {
                                return TrainingCategorySetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trainingCategorySetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/;
    });
