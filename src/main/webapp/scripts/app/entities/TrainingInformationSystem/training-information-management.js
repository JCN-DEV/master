'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingInfo', {
                parent: 'entity',
                url: '/training-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.employeeLoanInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/training-information-system.html',
                        controller: 'TrainingInformationSystemController'
                    },
                    'trainingInfoView@trainingInformationSystem':{
                         templateUrl: 'scripts/app/entities/TrainingInformationSystem/training-info-dashboard.html',
                         controller: 'TrainingInformationSystemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                       return $translate.refresh();
                    }]
                }
            })
            .state('trainingInfo.dashboard', {
                parent: 'trainingInfo',
                url: '/trainingInfo-dashboard',
                data: { //'ROLE_AD','ROLE_DG','ROLE_INSTITUTE','ROLE_ADMIN','ROLE_USER','ROLE_TIS_DASH'
                    authorities: [],
                    pageTitle: 'stepApp.employeeLoanInfo.home.title'
                },
                views: {
                    'trainingInfoView@trainingInformationSystem':{
                         templateUrl: 'scripts/app/entities/TrainingInformationSystem/training-info-dashboard.html',
                         controller: 'TrainingInformationSystemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {

                        return $translate.refresh();
                    }]
                }
            });
    });
