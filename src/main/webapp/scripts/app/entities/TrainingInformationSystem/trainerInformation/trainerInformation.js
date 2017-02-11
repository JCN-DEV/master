'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingInfo.trainerInformation', {
                parent: 'trainingInfo',
                url: '/trainerInformations',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.trainerInformation.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainerInformation/trainerInformations.html',
                        controller: 'TrainerInformationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainerInformation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trainingInfo.trainerInformation.detail', {
                parent: 'trainingInfo.trainerInformation',
                url: '/trainerInformation/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.trainerInformation.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainerInformation/trainerInformation-detail.html',
                        controller: 'TrainerInformationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainerInformation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainerInformation', function($stateParams, TrainerInformation) {
                        return TrainerInformation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trainingInfo.trainerInformation.new', {
                parent: 'trainingInfo.trainerInformation',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainerInformation/trainerInformation-dialog.html',
                        controller: 'TrainerInformationDialogController',
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            trainerId: null,
                            trainerName: null,
                            trainerType: null,
                            address: null,
                            designation: null,
                            department: null,
                            organization: null,
                            mobileNumber: null,
                            emailId: null,
                            specialSkills: null,
                            trainingAssignStatus: null,
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
            .state('trainingInfo.trainerInformation.edit', {
                parent: 'trainingInfo.trainerInformation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_TIS_DASH'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainerInformation/trainerInformation-dialog.html',
                        controller: 'TrainerInformationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TrainerInformation', function(TrainerInformation) {
                                return TrainerInformation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trainerInformation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
