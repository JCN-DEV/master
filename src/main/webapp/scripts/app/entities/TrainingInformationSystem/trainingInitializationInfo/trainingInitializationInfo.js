'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingInfo.trainingInitializationInfo', {
                parent: 'trainingInfo',
                url: '/trainingInitializationInfos',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingInitializationInfo.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingInitializationInfo/trainingInitializationInfos.html',
                        controller: 'TrainingInitializationInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingInitializationInfo','trainerInformation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trainingInfo.trainingInitializationInfo.detail', {
                parent: 'trainingInfo.trainingInitializationInfo',
                url: '/trainingInitializationInfo/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingInitializationInfo.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingInitializationInfo/trainingInitializationInfo-detail.html',
                        controller: 'TrainingInitializationInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingInitializationInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingInitializationInfo', function($stateParams, TrainingInitializationInfo) {
                        return TrainingInitializationInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trainingInfo.trainingInitializationInfo.new', {
                parent: 'trainingInfo.trainingInitializationInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingInitializationInfo/trainingInitializationInfo-dialog.html',
                        controller: 'TrainingInitializationInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingInitializationInfo','trainerInformation');
                        return $translate.refresh();
                    }]
                    ,
                    entity: function () {
                        return {
                            training_code: null,
                            trainingType: null,
                            session: null,
                            venueName: null,
                            numberOfTrainee: null,
                            location: null,
                            division: null,
                            district: null,
                            venueDescription: null,
                            initializationDate: null,
                            startDate: null,
                            endDate: null,
                            duration: null,
                            hours: null,
                            publishStatus: null,
                            status: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            trainingHeadSetup: null,
                            trainingRequisitionForm: null,
                            id:null
                        };
                    }
                }
            })

            .state('trainingInfo.trainingInitializationInfo.edit', {
                parent: 'trainingInfo.trainingInitializationInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingInitializationInfo.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingInitializationInfo/trainingInitializationInfo-dialog.html',
                        controller: 'TrainingInitializationInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingInitializationInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingInitializationInfo', function($stateParams, TrainingInitializationInfo) {
                        return TrainingInitializationInfo.get({id : $stateParams.id});
                    }]
                }
            });
    });
