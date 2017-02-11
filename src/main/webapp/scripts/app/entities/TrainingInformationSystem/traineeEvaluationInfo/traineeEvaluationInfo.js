'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingInfo.traineeEvaluationInfo', {
                parent: 'trainingInfo',
                url: '/traineeEvaluationInfos',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.traineeEvaluationInfo.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/traineeEvaluationInfo/traineeEvaluationInfos.html',
                        controller: 'TraineeEvaluationInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('traineeEvaluationInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trainingInfo.traineeEvaluationInfo.detail', {
                parent: 'trainingInfo.traineeEvaluationInfo',
                url: '/traineeEvaluationInfo/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.traineeEvaluationInfo.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/traineeEvaluationInfo/traineeEvaluationInfo-detail.html',
                        controller: 'TraineeEvaluationInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('traineeEvaluationInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TraineeEvaluationInfo', function($stateParams, TraineeEvaluationInfo) {
                        return TraineeEvaluationInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trainingInfo.traineeEvaluationInfo.new', {
                parent: 'trainingInfo.traineeEvaluationInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                       templateUrl: 'scripts/app/entities/TrainingInformationSystem/traineeEvaluationInfo/traineeEvaluationInfo-dialog.html',
                       controller: 'TraineeEvaluationInfoDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            sessionYear: null,
                            performance: null,
                            mark: null,
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
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/traineeEvaluationInfo/traineeEvaluationInfo-dialog.html',
                        controller: 'TraineeEvaluationInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    sessionYear: null,
                                    performance: null,
                                    mark: null,
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
                    }).result.then(function(result) {
                        $state.go('traineeEvaluationInfo', null, { reload: true });
                    }, function() {
                        $state.go('traineeEvaluationInfo');
                    })
                }]*/
            })
            .state('trainingInfo.traineeEvaluationInfo.edit', {
                parent: 'trainingInfo.traineeEvaluationInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.traineeEvaluationInfo.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/traineeEvaluationInfo/traineeEvaluationInfo-dialog.html',
                        controller: 'TraineeEvaluationInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('traineeEvaluationInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TraineeEvaluationInfo', function($stateParams, TraineeEvaluationInfo) {
                        return TraineeEvaluationInfo.get({id : $stateParams.id});
                    }]
                }
            });
    });
