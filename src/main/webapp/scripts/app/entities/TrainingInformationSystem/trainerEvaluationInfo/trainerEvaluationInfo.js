'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingInfo.trainerEvaluationInfo', {
                parent: 'trainingInfo',
                url: '/trainerEvaluationInfos',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.trainerEvaluationInfo.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainerEvaluationInfo/trainerEvaluationInfos.html',
                        controller: 'TrainerEvaluationInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainerEvaluationInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trainingInfo.trainerEvaluationInfo.detail', {
                parent: 'trainingInfo.trainerEvaluationInfo',
                url: '/trainerEvaluationInfo/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.trainerEvaluationInfo.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainerEvaluationInfo/trainerEvaluationInfo-detail.html',
                        controller: 'TrainerEvaluationInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainerEvaluationInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainerEvaluationInfo', function($stateParams, TrainerEvaluationInfo) {
                        return TrainerEvaluationInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trainingInfo.trainerEvaluationInfo.new', {
                parent: 'trainingInfo.trainerEvaluationInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainerEvaluationInfo/trainerEvaluationInfo-dialog.html',
                        controller: 'TrainerEvaluationInfoDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            sessionYear: null,
                            performance: null,
                            remarks: null,
                            evaluationDate: null,
                            status: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('trainingInfo.trainerEvaluationInfo.edit', {
                parent: 'trainingInfo.trainerEvaluationInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainerEvaluationInfo/trainerEvaluationInfo-dialog.html',
                        controller: 'TrainerEvaluationInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TrainerEvaluationInfo', function(TrainerEvaluationInfo) {
                                return TrainerEvaluationInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trainerEvaluationInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
