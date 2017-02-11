'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingInfo.traineeInformation', {
                parent: 'trainingInfo',
                url: '/traineeInformations',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.traineeInformation.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/traineeInformation/traineeInformations.html',
                        controller: 'TraineeInformationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('traineeInformation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trainingInfo.traineeInformation.detail', {
                parent: 'trainingInfo.traineeInformation',
                url: '/traineeInformation/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.traineeInformation.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/traineeInformation/traineeInformation-detail.html',
                        controller: 'TraineeInformationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('traineeInformation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TraineeInformation', function($stateParams, TraineeInformation) {
                        return TraineeInformation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trainingInfo.traineeInformation.new', {
                parent: 'trainingInfo.traineeInformation',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/traineeInformation/traineeInformation-dialog.html',
                        controller: 'TraineeInformationDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            traineeId: null,
                            traineeName: null,
                            session: null,
                            gender: null,
                            organization: null,
                            address: null,
                            division: null,
                            district: null,
                            contactNumber: null,
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
            .state('trainingInfo.traineeInformation.edit', {
                parent: 'trainingInfo.traineeInformation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.traineeInformation.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/traineeInformation/traineeInformation-dialog.html',
                        controller: 'TraineeInformationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('traineeInformation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TraineeInformation', function($stateParams, TraineeInformation) {
                        return TraineeInformation.get({id : $stateParams.id});
                    }]
                }
            });
    });
